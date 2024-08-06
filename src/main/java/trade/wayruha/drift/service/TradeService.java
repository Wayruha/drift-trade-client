package trade.wayruha.drift.service;

import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.dto.request.CancelOrderByUserIdRequest;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;
import trade.wayruha.drift.service.endpoint.TradeEndpoints;

import java.util.List;

public class TradeService extends ServiceBase{

  private final TradeEndpoints api;

  public TradeService(DriftConfig config) {
    super(config);
    this.api = createService(TradeEndpoints.class);
  }

  public TxConfirmationResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
    return this.client.executeSync(api.postOrder(placeOrderRequest));
  }

  public TxConfirmationResponse cancelAllOrders() {
    return client.executeSync(api.deleteOrders());
  }

  public TxConfirmationResponse cancelOrdersByUserIds(List<Integer> userIdsList){
    final CancelOrderByUserIdRequest cancelRequest = new CancelOrderByUserIdRequest(userIdsList);
    return client.executeSync(api.deleteOrdersByUserIds(cancelRequest));
  }
}
