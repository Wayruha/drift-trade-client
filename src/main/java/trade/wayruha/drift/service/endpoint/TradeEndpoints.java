package trade.wayruha.drift.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import trade.wayruha.drift.dto.request.CancelOrderByUserIdRequest;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;

public interface TradeEndpoints {
  @POST("v2/orders")
  Call<TxConfirmationResponse> postOrder(@Body PlaceOrderRequest request);

  @DELETE("v2/orders")
  Call<TxConfirmationResponse> deleteOrders();

  @HTTP(method = "DELETE", path = "v2/orders", hasBody = true)
  Call<TxConfirmationResponse> deleteOrdersByUserIds(@Body CancelOrderByUserIdRequest request);
}
