package trade.wayruha.drift.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MetadataEndpoints {

    @GET("v2/markets")
    Call<JsonNode> getMarkets();

    @GET("v2/balance")
    Call<JsonNode> getBalance();

    @GET("v2/positions")
    Call<JsonNode> getAllPositions();

    @GET("v2/orders")
    Call<JsonNode> getAllOrders();
}
