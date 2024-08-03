package trade.wayruha.drift.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import trade.wayruha.drift.dto.request.MarketPositionsRequest;

public interface MetadataEndpoints {
  /*@POST("info")
  Call<JsonNode> getPerpetualsMetadata(@Body PerpetualsMetadataRequest request);*/

    @GET("v2/balance")
    Call<JsonNode> getBalance();

    @GET("v2/positions")
    Call<JsonNode> getPositionsByMarket(@Body MarketPositionsRequest request);

    @GET("v2/positions")
    Call<JsonNode> getAllPositions();

    @GET("v2/orders")
    Call<JsonNode> getAllOrders();
}
