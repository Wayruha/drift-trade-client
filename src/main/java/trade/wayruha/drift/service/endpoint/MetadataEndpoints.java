package trade.wayruha.drift.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;

//TODO use specific classes for response, not a general JsonNode (unless necessary)
public interface MetadataEndpoints {
  @GET("v2/balance")
  Call<JsonNode> getBalance();

  @GET("v2/positions")
  Call<JsonNode> getPositionsByMarket(@Body MarketPositionsRequest request);

  @GET("v2/positions")
  Call<JsonNode> getAllPositions();

  @GET("v2/orders")
  Call<JsonNode> getAllOrders();

  @GET("v2/markets")
  Call<JsonNode> getMarkets();
}
