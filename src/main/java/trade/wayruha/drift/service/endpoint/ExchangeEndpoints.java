package trade.wayruha.drift.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

public interface ExchangeEndpoints {
    @POST("v2/orders")
    Call<JsonNode> postOrder(@Body Object request);

    @DELETE("v2/orders")
    Call<JsonNode> deleteOrders();
}
