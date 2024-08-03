package trade.wayruha.drift.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import trade.wayruha.drift.dto.response.TxConfirmationResponse;

public interface TradeEndpoints {
  @POST("v2/orders")
  Call<TxConfirmationResponse> postOrder(@Body Object request); //todo should be an actual request class, not a general Object, as well as response

  @DELETE("v2/orders")
  Call<TxConfirmationResponse> deleteOrders();
}
