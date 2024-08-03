package trade.wayruha.drift;

import lombok.SneakyThrows;
import trade.wayruha.drift.dto.OrderType;
import trade.wayruha.drift.dto.request.PlaceOrderParams;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.MarketMetadata;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.service.HttpGatewayService;
import trade.wayruha.drift.service.MetadataService;

import java.math.BigDecimal;
import java.util.List;

public class MetadataServiceTest {

  private static DriftConfig driftConfig;
  private static HttpGatewayService httpGatewayService;
  private static final String privateKey = "";


  @SneakyThrows
  public static void main(String[] args) {
    driftConfig = new DriftConfig();
    httpGatewayService = new HttpGatewayService(driftConfig.getGatewayHost(), Integer.parseInt(driftConfig.getGatewayPort()), privateKey, driftConfig);
    httpGatewayService.startGateway();
//    getMarkets();
//    getBalance();
//    placeOrder();
    cancelAllOrders();
//    getAllMarketPositions();
//    getAllOrders();
  }


  private static void getMarkets(){
    final MetadataService service = new MetadataService(new DriftConfig());
    final List<MarketMetadata> markets = service.getMarkets();
    assert markets != null;
    assert markets.size() > 0;
  }

  private static void getBalance(){
    String balance = httpGatewayService.getBalance();
    System.out.println(balance);
  }

  private static void placeOrder(){
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOracleOrder(0, BigDecimal.valueOf(0.1), false, OrderType.LIMIT, 20, 101)));
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.SpotOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(150), false, OrderType.LIMIT, 101, false, 0)));
    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(150), false, OrderType.LIMIT,101)));
    String tx = httpGatewayService.placeOrder(placeOrderRequest);
    System.out.println("tx: " + tx);
  }

  private static void cancelAllOrders(){
    String tx = httpGatewayService.cancelAllOrders();
    System.out.println("tx: " + tx);
  }

  private static void getAllMarketPositions(){
    MarketPositionsResponse marketPositions = httpGatewayService.getMarketPositions();
    System.out.println(marketPositions);
  }

  private static void getAllOrders(){
    OrdersInfoResponse orders = httpGatewayService.getAllOrders();
    System.out.println(orders);
  }
}
