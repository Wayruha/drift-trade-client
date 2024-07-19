package trade.wayruha.drift.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.ApiClient;
import trade.wayruha.drift.config.Constant;
import trade.wayruha.drift.dto.wsrequest.WSMessage;
import trade.wayruha.drift.dto.wsrequest.WSSubscription;
import trade.wayruha.drift.util.IdGenerator;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static trade.wayruha.drift.config.Constant.MAX_WS_BATCH_SUBSCRIPTIONS;

@Slf4j
public class WebSocketSubscriptionClient<T> extends WebSocketListener {
  protected static long WEB_SOCKET_RECONNECTION_DELAY_MS = 10_000;
  protected static final WSMessage pingRequest = new WSMessage("heartbeat");

  protected final DriftConfig config;
  protected final ApiClient apiClient;
  protected final ObjectMapper objectMapper;
  protected final WebSocketCallback<T> callback;
  @Getter
  protected final int id;
  protected final String logPrefix;
  protected final Set<WSSubscription> subscriptions;

  protected WebSocket webSocket;
  protected Request connectionRequest;
  protected final AtomicInteger reconnectionCounter;
  @Setter
  protected ScheduledExecutorService scheduler;
  protected ScheduledFuture<?> scheduledPingTask;
  @Getter
  protected WSState state;
  @Getter
  protected long lastReceivedTime;

  public WebSocketSubscriptionClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback) {
    this(apiClient, mapper, callback, Executors.newSingleThreadScheduledExecutor());
  }

  public WebSocketSubscriptionClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback,
                                     ScheduledExecutorService scheduler) {
    this.apiClient = apiClient;
    this.config = apiClient.getConfig();
    this.connectionRequest = buildRequestFromHost(this.config.getWebSocketHost());
    this.callback = callback;
    this.objectMapper = mapper;
    this.subscriptions = new HashSet<>();
    this.reconnectionCounter = new AtomicInteger(0);
    this.scheduler = scheduler;
    this.id = IdGenerator.getNextId();
    this.logPrefix = "[ws-" + this.id + "]";
  }

  protected void connect(Set<WSSubscription> subscriptions) {
    if (this.state != WSState.CONNECTED && this.state != WSState.CONNECTING) {
      log.debug("{} Connecting to channels {} ...", logPrefix, subscriptions);
      this.state = WSState.CONNECTING;
      this.webSocket = apiClient.createWebSocket(this.connectionRequest, this);
      if (this.webSocket == null) {
        log.error("Error connect {}", this.connectionRequest);
      }
    } else {
      log.warn("{} Already connected to channels {}", logPrefix, this.subscriptions);
    }
    this.scheduledPingTask = scheduler.scheduleAtFixedRate(new PingTask(), this.config.getWebSocketPingIntervalSec(), this.config.getWebSocketPingIntervalSec(), TimeUnit.SECONDS);
    reconnectionCounter.set(0); //reset reconnection indexer due to successful connection
    this.subscribe(subscriptions);
  }

  @SneakyThrows
  public void subscribe(WSSubscription subscription) {
    if (this.sendRequest(subscription)) {
      this.subscriptions.add(subscription);
    }
  }

  public void subscribe(Set<WSSubscription> subscriptions) {
    if (subscriptions.size() > MAX_WS_BATCH_SUBSCRIPTIONS)
      throw new IllegalArgumentException("Can't subscribe to more then " + MAX_WS_BATCH_SUBSCRIPTIONS + " channels in single WS connection");
    subscriptions.forEach(this::subscribe);
  }

  @SneakyThrows
  public boolean sendRequest(WSMessage request) {
    boolean result = false;
    final String requestStr = objectMapper.writeValueAsString(request);
    log.debug("{} sending {}", logPrefix, requestStr);
    if (nonNull(webSocket)) {
      result = webSocket.send(requestStr);
    }
    if (!result) {
      log.error("{} Failed to send message: {}. Closing the connection...", logPrefix, request);
      closeOnError(null);
    }
    return result;
  }

  public void close() {
    log.debug("{} Closing WS.", logPrefix);
    state = WSState.IDLE;
    if (webSocket != null) {
      webSocket.cancel();
      webSocket = null;
    }
    this.subscriptions.clear();
    if (nonNull(scheduledPingTask))
      scheduledPingTask.cancel(false);
  }

  public boolean reConnect() {
    boolean success = false;
    final Set<WSSubscription> cancelledSubscriptions = new HashSet<>(this.subscriptions);
    while (!success &&
        (config.isWebSocketReconnectAlways() || reconnectionCounter.incrementAndGet() < config.getWebSocketMaxReconnectAttempts())) {
      try {
        log.debug("{} Try to reconnect. Attempt #{}", logPrefix, reconnectionCounter.get());
        this.close();
        this.connect(cancelledSubscriptions);
        success = true;
      } catch (Exception e) {
        log.error("{} [Connection error] Error while reconnecting: {}", logPrefix, e.getMessage(), e);
        try {
          Thread.sleep(WEB_SOCKET_RECONNECTION_DELAY_MS);
        } catch (InterruptedException ex) {
          log.error("{} [Connection error] Interrupted while Thread.sleep(). {}", logPrefix, ex.getMessage());
        }
      }
    }
    log.debug("{} Successfully reconnected to WebSocket channels: {}.", logPrefix, cancelledSubscriptions);
    cancelledSubscriptions.clear();
    return success;
  }

  @SneakyThrows
  public void ping() {
    this.sendRequest(pingRequest);
    log.trace("{} Ping.", logPrefix);
  }

  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    log.debug("{} onOpen WS event: Connected to channels {}", logPrefix, this.subscriptions);
    state = WSState.CONNECTED;
    lastReceivedTime = System.currentTimeMillis();
    this.webSocket = webSocket;
    callback.onOpen(response);
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    lastReceivedTime = System.currentTimeMillis();
    try {
      final ObjectNode response = objectMapper.readValue(text, ObjectNode.class);
      final Optional<String> channel = Optional.ofNullable(response.get("channel"))
          .map(JsonNode::textValue);
      final JsonNode errorMsg = response.get("error");
      if (errorMsg != null) {
        throw new RuntimeException("WS error: " + errorMsg.asText());
      } else if (channel.filter(ch -> ch.equalsIgnoreCase("heartbeat")).isPresent()) {
        return;
      } else if (response.has("message")) {
        return;
      }
      log.debug("{} onMessage WS event: {}", logPrefix, text);

      final JsonNode dataNode = response.get("data");
      final String dataJson = dataNode.textValue();
      final T data = objectMapper.readValue(dataJson, callback.getType());
      callback.onResponse(data);
    } catch (Exception e) {
      log.error("{} WS failed. Response: {}", log, text, e);
      closeOnError(e);
    }
  }

  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    log.debug("onMessage: {} ", bytes.toString());
    super.onMessage(webSocket, bytes);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    log.debug("{} onClosed WS event: {}, code: {}", logPrefix, reason, code);
    super.onClosed(webSocket, code, reason);
    if (state == WSState.CONNECTED || state == WSState.CONNECTING) {
      state = WSState.IDLE;
    }
    log.debug("{} Closed", logPrefix);
    callback.onClosed(code, reason);
  }

  @SneakyThrows
  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    if (state == WSState.IDLE) {
      return; //this is a handled websocket closure. No failure event should be created.
    }
    log.error("{} WS failed. Response: {}. Trying to reconnect...", logPrefix, extractResponseBody(response), t);

    if (!reConnect()) {
      log.warn("{} [Connection error] Could not reconnect in {} attempts.", logPrefix, config.getWebSocketMaxReconnectAttempts());
      super.onFailure(webSocket, t, response);
      closeOnError(t);
      callback.onFailure(t, response);
    }
  }

  private void closeOnError(Throwable ex) {
    log.warn("{} [Connection error] Connection will be closed due to error: {}", logPrefix,
        ex != null ? ex.getMessage() : Constant.WEBSOCKET_INTERRUPTED_EXCEPTION);
    this.close();
    state = WSState.CLOSED_ON_ERROR;
  }

  static Request buildRequestFromHost(String host) {
    return new Request.Builder().url(host).build();
  }

  @SneakyThrows
  static String extractResponseBody(Response response) {
    if (isNull(response)) return null;
    if (isNull(response.body())) return null;
    return response.body().string();
  }

  class PingTask implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
      try {
        ping();
      } catch (Exception ex) {
        log.error("{} Ping error. Try to reconnect and send again in {} sec...", logPrefix, WEB_SOCKET_RECONNECTION_DELAY_MS / 1000);
        Thread.sleep(WEB_SOCKET_RECONNECTION_DELAY_MS);
        reConnect();
      }
    }
  }
}
