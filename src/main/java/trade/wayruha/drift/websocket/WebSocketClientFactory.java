package trade.wayruha.drift.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.ApiClient;
import trade.wayruha.drift.dto.wsrequest.WSSubscription;
import trade.wayruha.drift.dto.wsresponse.OrderBookUpdate;

import java.util.Set;

public class WebSocketClientFactory {
  private final DriftConfig config;
  private final ApiClient apiClient;
  @Setter
  private ObjectMapper objectMapper;

  public WebSocketClientFactory(DriftConfig config) {
    this.apiClient = new ApiClient(config);
    this.config = config;
    this.objectMapper = config.getObjectMapper();
  }

  //public subscriptions
  public WebSocketSubscriptionClient<OrderBookUpdate> perpOrderBookSubscription(String coin, WebSocketCallback<OrderBookUpdate> callback) {
    final WSSubscription sub = WSSubscription.perpOrderBookSubscription(coin);
    final WebSocketSubscriptionClient<OrderBookUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback);
    client.connect(Set.of(sub));
    return client;
  }
}
