package trade.wayruha.drift;

import lombok.SneakyThrows;
import trade.wayruha.drift.dto.MarketType;
import trade.wayruha.drift.dto.OrderType;
import trade.wayruha.drift.dto.request.MarketPositionsRequest;
import trade.wayruha.drift.dto.request.PerpMarketPositionRequest;
import trade.wayruha.drift.dto.request.PlaceOrderParams;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.BalanceResponse;
import trade.wayruha.drift.dto.response.ExtMarketPositionItem;
import trade.wayruha.drift.dto.response.MarketInfoResponse;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;
import trade.wayruha.drift.service.HttpGatewayService;
import trade.wayruha.drift.service.MetadataService;
import trade.wayruha.drift.service.TradeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MetadataServiceTest {

  private static DriftConfig driftConfig;
  private static HttpGatewayService httpGatewayService;
  private static TradeService tradeService;
  private static MetadataService metadataService;
  private static final String privateKey = "";


  @SneakyThrows
  public static void main(String[] args) {
    driftConfig = new DriftConfig("8090", "127.0.0.1", "wss://dlob.drift.trade/ws", "E:/Work/gateway-0.1.15/target/release/drift-gateway.exe", "https://api.mainnet-beta.solana.com");
    httpGatewayService = new HttpGatewayService(privateKey, driftConfig);
    httpGatewayService.startGateway();

    tradeService = new TradeService(driftConfig);
    metadataService = new MetadataService(driftConfig);
//    getMarkets();
//    getBalance();
//    placeOrder();
//    cancelAllOrders();
//    cancelOrdersByUserIds();
//    getMarketPosition();
//    getAllMarketPositions();
		getPerpExtPositionInfo();
//    getAllOrders();
    httpGatewayService.stopGateway();
  }

  private static void getMarkets(){
    final MarketInfoResponse marketsInfo = metadataService.getMarketsInfo();
    System.out.println(marketsInfo);
    assert !marketsInfo.getPerpPositionsList().isEmpty();
    assert !marketsInfo.getSpotPositionsList().isEmpty();
  }

//  private static void getMarkets() {
//    DriftConfig driftConfig = new DriftConfig();
//    final MetadataService service = new MetadataService(driftConfig);
//    final List<MarketMetadata> markets = service.getMarkets();
//    assert markets != null;
//    assert markets.size() > 0;
//  }

  private static void getBalance() {
    final BalanceResponse balance = metadataService.getBalance();
    System.out.println(balance);
  }

  private static void placeOrder() {
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOracleOrder(0, BigDecimal.valueOf(0.1), false, OrderType.LIMIT, 20, 101)));
//    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.SpotOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(150), false, OrderType.LIMIT, 101, false, 0)));
    final PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(List.of(PlaceOrderParams.PerpOrder(0, BigDecimal.valueOf(0.1), BigDecimal.valueOf(120), false, OrderType.LIMIT, 101)));
    TxConfirmationResponse tx = tradeService.placeOrder(placeOrderRequest);
    System.out.println(tx);
  }

  private static void cancelAllOrders() {
    TxConfirmationResponse tx = tradeService.cancelAllOrders();
    System.out.println(tx);
  }

  private static void cancelOrdersByUserIds() {
    final List<Integer> userIds = new ArrayList<>(List.of(101));
    final TxConfirmationResponse tx = tradeService.cancelOrdersByUserIds(userIds);
    System.out.println(tx);
  }

  private static void getAllMarketPositions() {
    MarketPositionsResponse marketPositions = metadataService.getAllMarketPositions();
    System.out.println(marketPositions);
  }

  private static void getMarketPosition() {
    MarketPositionsRequest marketPositionsRequest = new MarketPositionsRequest(0, MarketType.SPOT);
    MarketPositionsResponse marketPositions = metadataService.getMarketPosition(marketPositionsRequest);
    System.out.println(marketPositions);
  }

	private static void getPerpExtPositionInfo(){
		PerpMarketPositionRequest perpMarketPositionRequest = new PerpMarketPositionRequest(0);
		ExtMarketPositionItem positionItem = metadataService.getPerpMarketPosition(perpMarketPositionRequest);
		System.out.println(positionItem);

	}

  private static void getAllOrders() {
    final OrdersInfoResponse orders = metadataService.getAllOrders();
    System.out.println(orders);
  }
}
