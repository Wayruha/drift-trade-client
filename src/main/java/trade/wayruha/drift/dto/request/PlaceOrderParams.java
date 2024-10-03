package trade.wayruha.drift.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import trade.wayruha.drift.dto.MarketType;
import trade.wayruha.drift.dto.OrderType;

import java.math.BigDecimal;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceOrderParams {
  int marketIndex;
  MarketType marketType;
  BigDecimal amount;
  BigDecimal price;
  boolean postOnly;
  OrderType orderType;
  Integer userOrderId;
  Boolean reduceOnly;
  Integer oraclePriceOffset;
  @JsonProperty("maxTs")
  Integer maxExpirationTimestamp;

  private PlaceOrderParams(int marketIndex, MarketType marketType, BigDecimal amount, BigDecimal price, boolean postOnly, OrderType orderType, Integer userOrderId, Boolean reduceOnly, Integer oraclePriceOffset, Integer maxExpirationTimestamp) {
    this.marketIndex = marketIndex;
    this.marketType = marketType;
    this.amount = amount;
    this.price = price;
    this.postOnly = postOnly;
    this.orderType = orderType;
    this.userOrderId = userOrderId;
    this.reduceOnly = reduceOnly;
    this.oraclePriceOffset = oraclePriceOffset;
    this.maxExpirationTimestamp = maxExpirationTimestamp;
  }

  public static PlaceOrderParams SpotOrder(int marketIndex, BigDecimal amount, BigDecimal price, boolean postOnly, OrderType orderType, Integer userOrderId, boolean reduceOnly, Integer maxExpirationTimestamp) {
    return new PlaceOrderParams(marketIndex, MarketType.SPOT, amount, price, postOnly, orderType, userOrderId, reduceOnly, null, maxExpirationTimestamp);
  }

  public static PlaceOrderParams PerpOracleOrder(int marketIndex, BigDecimal amount, boolean postOnly, OrderType orderType, int oraclePriceOffset, Integer userOrderId, Boolean isReduceOnly) {
    return new PlaceOrderParams(marketIndex, MarketType.PERP, amount, null, postOnly, orderType, userOrderId, isReduceOnly, oraclePriceOffset, null);
  }

  public static PlaceOrderParams PerpOrder(int marketIndex, BigDecimal amount, BigDecimal price, boolean postOnly, OrderType orderType, Integer userOrderId, Boolean isReduceOnly) {
    return new PlaceOrderParams(marketIndex, MarketType.PERP, amount, price, postOnly, orderType, userOrderId, isReduceOnly, null, null);
  }
}
