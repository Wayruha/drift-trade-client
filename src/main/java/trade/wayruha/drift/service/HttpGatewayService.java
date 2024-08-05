package trade.wayruha.drift.service;

import lombok.extern.slf4j.Slf4j;
import trade.wayruha.drift.DriftConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpGatewayService {
  private final String host;
  private final Integer port;
  private final String privateKey;

  public HttpGatewayService(String privateKey, DriftConfig config) {

    this.host = config.getGatewayHost();
    this.port = Integer.parseInt(config.getGatewayPort());
    this.privateKey = privateKey;
  }

  //todo add stopGateway();

  public void startGateway() throws IOException, InterruptedException {
    final ProcessBuilder processBuilder = new ProcessBuilder(
        client.getConfig().getGatewayPath() + "/target/release/drift-gateway.exe", //todo we can force user to provide his own path to executable
        // and then we don't need to a) include .exe file in git; b) force user to use windows system (.exe)
        "https://api.mainnet-beta.solana.com", // todo magic constant
        "--port", port.toString(),
        "--host", host
    );

    final Map<String, String> environment = processBuilder.environment();
    environment.put("DRIFT_GATEWAY_KEY", privateKey); //todo magic constant: must be declared as (at least) 'private static final String'


    Process process = processBuilder.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (process.isAlive()) {
        process.destroy();
        log.info("Process destroyed on JVM shutdown."); //todo don't use sysout
      }
    }));

    TimeUnit.SECONDS.sleep(2);
  }

  /*  public BaseMarketResponse<MarketItemInfo> getMarketsInfo() {
        if (!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getMarkets());
        return getObjectMapper().convertValue(node, MARKET_INFO_RESPONSE_TYPE);
    }*/
/*
  public String getBalance() {
    final JsonNode jsonNode = client.executeSync(metadataApi.getBalance());
    return jsonNode.get("balance").asText();

  }

  public TxConfirmationResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
    final TxConfirmationResponse response = client.executeSync(exchangeApi.postOrder(placeOrderRequest));
    return response;
  }

  public TxConfirmationResponse cancelAllOrders() {
    final TxConfirmationResponse response = client.executeSync(exchangeApi.deleteOrders());
    return response;
  }

  public MarketPositionsResponse getMarketPositions() {
    final JsonNode node = client.executeSync(metadataApi.getAllPositions());
    final MarketPositionsResponse response = getObjectMapper().convertValue(node, MarketPositionsResponse.class);

    return response;
  }

  public OrdersInfoResponse getAllOrders() {

    final JsonNode node = client.executeSync(metadataApi.getAllOrders());
    final OrdersInfoResponse response = getObjectMapper().convertValue(node, OrdersInfoResponse.class);

    return response;
  }*/

  /*
  * public String cancelOrdersByUserIds(List<Integer> userIdsList){
        if(!isRunning) throw new RuntimeException("Service has to be started first");
        final CancelOrderByUserIdRequest cancelRequest = new CancelOrderByUserIdRequest(userIdsList);
        final JsonNode node = client.executeSync(exchangeApi.deleteOrdersByUserIds(cancelRequest));
        return node.get("tx").asText();
    }


    public BaseMarketResponse<MarketPositionItem> getAllMarketPositions() {
        if (!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getAllPositions());
        return getObjectMapper().convertValue(node, MARKET_POSITIONS_RESPONSE_TYPE);
    }
  * */
}
