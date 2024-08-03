package trade.wayruha.drift;

import lombok.SneakyThrows;
import trade.wayruha.drift.dto.OrderType;
import trade.wayruha.drift.dto.request.PlaceOrderParams;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.MarketMetadata;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;
import trade.wayruha.drift.service.HttpGatewayService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MetadataServiceTest {

  private static DriftConfig driftConfig;
  private static HttpGatewayService httpGatewayService;
  private static final String privateKey = "";


  @SneakyThrows
  public static void main(String[] args) {
    driftConfig = new DriftConfig();
    httpGatewayService = new HttpGatewayService(privateKey, driftConfig);
    httpGatewayService.startGateway();
//    getMarkets();
//    getBalance();
//    placeOrder();
//    cancelAllOrders();
//    cancelOrdersByUserIds();
//    getAllMarketPositions();
//    getAllOrders();
  }

  //todo
  /*
    private static void getMarkets(){
    final BaseMarketResponse<MarketItemInfo> marketsInfo = httpGatewayService.getMarketsInfo();
    assert !marketsInfo.getPerpPositionsList().isEmpty();
    assert !marketsInfo.getSpotPositionsList().isEmpty();
  }
  */
  private static void getMarkets() {
    final MetadataService service = new MetadataService(new DriftConfig());
    final List<MarketMetadata> markets = service.getMarkets();
    assert markets != null;
    assert markets.size() > 0;
  }

  private static void getBalance() {
    final String balance = httpGatewayService.getBalance();
    System.out.println(balance);
  }

  private static void placeOrder() {
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOracleOrder(0, BigDecimal.valueOf(0.1), false, OrderType.LIMIT, 20, 101)));
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.SpotOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(150), false, OrderType.LIMIT, 101, false, 0)));
    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(150), false, OrderType.LIMIT, 101)));
    TxConfirmationResponse tx = httpGatewayService.placeOrder(placeOrderRequest);
    System.out.println("tx: " + tx);
  }

  private static void cancelAllOrders() {
    TxConfirmationResponse tx = httpGatewayService.cancelAllOrders();
    System.out.println("tx: " + tx);
  }

  private static void cancelOrdersByUserIds() {
    final List<Integer> userIds = new ArrayList<>(List.of(101));
    final String tx = httpGatewayService.cancelOrdersByUserIds(userIds);
    System.out.println("tx: " + tx);
  }

  private static void getAllMarketPositions() {
    MarketPositionsResponse marketPositions = httpGatewayService.getMarketPositions();
    System.out.println(marketPositions);
  }

  private static void getAllOrders() {
    final OrdersInfoResponse orders = httpGatewayService.getAllOrders();
    System.out.println(orders);
  }
}
