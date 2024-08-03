package trade.wayruha.drift.service;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.service.endpoint.ExchangeEndpoints;
import trade.wayruha.drift.service.endpoint.MetadataEndpoints;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpGatewayService extends ServiceBase {
    private final String host;
    private final Integer port;
    private final  String privateKey;
    private boolean isRunning;

    private final MetadataEndpoints metadataApi;
    private final ExchangeEndpoints exchangeApi;

    public HttpGatewayService(String host, Integer port, String privateKey, DriftConfig config) {
        super(config);
        this.exchangeApi = createService(ExchangeEndpoints.class);
        this.metadataApi = createService(MetadataEndpoints.class);

        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
        isRunning = false;
    }


    @SneakyThrows
    public void startGateway() {
        if(isRunning) return;

        final ProcessBuilder processBuilder = new ProcessBuilder(
                client.getConfig().getGatewayPath() + "/target/release/drift-gateway.exe",
                "https://api.mainnet-beta.solana.com",
                "--port", port.toString(),
                "--host", host
        );

        final Map<String, String> environment = processBuilder.environment();
        environment.put("DRIFT_GATEWAY_KEY", privateKey);


        Process process = processBuilder.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (process.isAlive()) {
                process.destroy();
                System.out.println("Process destroyed on JVM shutdown.");
            }
        }));

        TimeUnit.SECONDS.sleep(2);
        isRunning = true;
    }

    public String getBalance(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");
        final JsonNode jsonNode = client.executeSync(metadataApi.getBalance());
        return jsonNode.get("balance").asText();

    }

    public String placeOrder(PlaceOrderRequest placeOrderRequest){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(exchangeApi.postOrder(placeOrderRequest));
        final String response = node.get("tx").asText();

        return response;

    }

    public String cancelAllOrders(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(exchangeApi.deleteOrders());
        final String response = node.get("tx").asText();

        return response;
    }

    public MarketPositionsResponse getMarketPositions(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getAllPositions());
        final MarketPositionsResponse response = getObjectMapper().convertValue(node, MarketPositionsResponse.class);

        return response;
    }

    public OrdersInfoResponse getAllOrders(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getAllOrders());
        final OrdersInfoResponse response = getObjectMapper().convertValue(node, OrdersInfoResponse.class);

        return response;
    }
}
