package trade.wayruha.drift.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import trade.wayruha.drift.dto.request.CancelOrderByIdsRequest;
import trade.wayruha.drift.dto.request.CancelOrderByUserIdRequest;
import trade.wayruha.drift.dto.request.PlaceOrderRequest;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;

public interface TradeEndpoints {
  @POST("v1/orders")
  Call<TxConfirmationResponse> postOrder(@Body PlaceOrderRequest request);

  @DELETE("v1/orders")
  Call<TxConfirmationResponse> deleteOrders();

  @HTTP(method = "DELETE", path = "v1/orders", hasBody = true)
  Call<TxConfirmationResponse> deleteOrdersByUserIds(@Body CancelOrderByUserIdRequest request);

	@HTTP(method = "DELETE", path = "v1/orders", hasBody = true)
	Call<TxConfirmationResponse> deleteOrdersByIds(@Body CancelOrderByIdsRequest request);
}
