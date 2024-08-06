package trade.wayruha.drift.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trade.wayruha.drift.dto.response.BalanceResponse;
import trade.wayruha.drift.dto.response.MarketInfoResponse;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;

public interface MetadataEndpoints {
  @GET("v2/balance")
  Call<BalanceResponse> getBalance();

  @GET("v2/positions")
  Call<MarketPositionsResponse> getPositionsByMarket(@Query("marketIndex") int marketIndex,
                                                     @Query("marketType") String marketType);

  @GET("v2/positions")
  Call<MarketPositionsResponse> getAllPositions();

  @GET("v2/orders")
  Call<OrdersInfoResponse> getAllOrders();

  @GET("v2/markets")
  Call<MarketInfoResponse> getMarkets();
}
