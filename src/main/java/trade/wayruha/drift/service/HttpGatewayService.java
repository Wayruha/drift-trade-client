package trade.wayruha.drift.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.dto.request.CancelOrderByUserIdRequest;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.MarketItemInfo;
import trade.wayruha.drift.dto.response.MarketPositionItem;
import trade.wayruha.drift.dto.response.BaseMarketResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.service.endpoint.ExchangeEndpoints;
import trade.wayruha.drift.service.endpoint.MetadataEndpoints;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpGatewayService extends ServiceBase {
    private static final TypeReference<BaseMarketResponse<MarketPositionItem>> MARKET_POSITIONS_RESPONSE_TYPE = new TypeReference<>() {};
    private static final TypeReference<BaseMarketResponse<MarketItemInfo>> MARKET_INFO_RESPONSE_TYPE = new TypeReference<>() {};

    private final String host;
    private final Integer port;
    private final  String privateKey;
    private boolean isRunning;

    private final MetadataEndpoints metadataApi;
    private final ExchangeEndpoints exchangeApi;

    public HttpGatewayService(String privateKey, DriftConfig config) {
        super(config);
        this.exchangeApi = createService(ExchangeEndpoints.class);
        this.metadataApi = createService(MetadataEndpoints.class);

        this.host = config.getGatewayHost();
        this.port = Integer.parseInt(config.getGatewayPort());
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
            }
        }));

        TimeUnit.SECONDS.sleep(2);
        isRunning = true;
    }

    public BaseMarketResponse<MarketItemInfo> getMarketsInfo() {
        if (!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getMarkets());
        return getObjectMapper().convertValue(node, MARKET_INFO_RESPONSE_TYPE);
    }

    public String getBalance(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");
        final JsonNode jsonNode = client.executeSync(metadataApi.getBalance());
        return jsonNode.get("balance").asText();

    }

    public String placeOrder(PlaceOrderRequest placeOrderRequest){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(exchangeApi.postOrder(placeOrderRequest));
        return node.get("tx").asText();

    }

    public String cancelAllOrders(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(exchangeApi.deleteOrders());
        return node.get("tx").asText();
    }

    public String cancelOrdersByUserIds(List<Integer> userIdsList){
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

    public OrdersInfoResponse getAllOrders(){
        if(!isRunning) throw new RuntimeException("Service has to be started first");

        final JsonNode node = client.executeSync(metadataApi.getAllOrders());
        return getObjectMapper().convertValue(node, OrdersInfoResponse.class);
    }
}
