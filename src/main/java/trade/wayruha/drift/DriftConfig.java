package trade.wayruha.drift;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import static trade.wayruha.drift.config.Constant.HTTP_CLIENT_TIMEOUT_MS;

@Data
public class DriftConfig {
  private String gatewayPort = "8090";
  private String gatewayHost = "127.0.0.2";
  private String host;
  private String webSocketHost = "wss://dlob.drift.trade/ws";
  private String gatewayExecutablePath = "../gateway-0.1.15/target/release/drift-gateway.exe";
  private String rpcNode = "https://api.mainnet-beta.solana.com";

  public DriftConfig(String gatewayPort, String gatewayHost, String webSocketHost, String gatewayExecutablePath, String rpcNode) {
    this.gatewayPort = gatewayPort;
    this.gatewayHost = gatewayHost;
    this.host  = "http://" + gatewayHost + ":" + gatewayPort;
    this.webSocketHost = webSocketHost;
    this.gatewayExecutablePath = gatewayExecutablePath;
    this.rpcNode = rpcNode;
  }

  /**
   * Host connection timeout.
   */
  private long httpConnectTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /**
   * The host reads the information timeout.
   */
  private long httpReadTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /**
   * The host writes the information timeout.
   */
  private long httpWriteTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /**
   * Retry on failed connection, default true.
   */
  private boolean retryOnConnectionFailure = true;
  /**
   * Should we log request's data?
   */
  private boolean httpLogRequestData = false;

  /**
   * WebSocket should try re-connecting on fail forever
   */
  private boolean webSocketReconnectAlways = false;
  /**
   * If not forever, then how many times?
   */
  private int webSocketMaxReconnectAttempts = 3;
  private int webSocketPingIntervalSec = 45;

  private ObjectMapper objectMapper = createObjectMapper();

  private static ObjectMapper createObjectMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    return mapper;
  }
}