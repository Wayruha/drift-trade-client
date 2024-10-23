package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trade.wayruha.drift.dto.MarketType;
import trade.wayruha.drift.dto.OrderSide;
import trade.wayruha.drift.dto.OrderType;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOrderUpdate {
  private static final Logger log = LoggerFactory.getLogger(UserOrderUpdate.class);
  @JsonProperty("orderCreate")
  private CreateUpdate createUpdate;
  @JsonProperty("orderCancel")
  private CancelUpdate cancelUpdate;
  @JsonProperty("orderExpire")
  private ExpireUpdate expireUpdate;
  @JsonProperty("fill")
  private FillUpdate fillUpdate;
  @JsonProperty("orderCancelMissing")
  private CancelMissingUpdate cancelMissingUpdate;
  @JsonProperty("fundingPayment")
  private FundingPaymentUpdate fundingPaymentUpdate;

  public UserUpdatesType getUpdatesType() {
    if (cancelUpdate != null) {
      return UserUpdatesType.CANCEL;
    } else if (expireUpdate != null) {
      return UserUpdatesType.EXPIRE;
    } else if (createUpdate != null) {
      return UserUpdatesType.CREATE;
    } else if (fillUpdate != null) {
      return UserUpdatesType.FILL;
    } else if (cancelMissingUpdate != null) {
      return UserUpdatesType.CANCEL_MISSING;
    } else if (fundingPaymentUpdate != null) {
      return UserUpdatesType.FUNDING;
    } else {
      throw new IllegalStateException("No valid data type for this WSMessage");
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CreateUpdate {
    private Order order;
    private Long ts;
    private String signature;
    private Long txIdx;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Order {
      private Long slot;
      private BigDecimal price;
      private BigDecimal amount;
      private BigDecimal filled;
      private BigDecimal triggerPrice;
      private BigDecimal auctionStartPrice;
      private BigDecimal auctionEndPrice;
      private Long maxTs;
      private BigDecimal oraclePriceOffset;
      private Long orderId;
      private Integer marketIndex;
      private OrderType orderType;
      private MarketType marketType;
      private Long userOrderId;
      private OrderSide direction;
      private Boolean reduceOnly;
      private Boolean postOnly;
      private Long auctionDuration;
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CancelUpdate {
    private Long orderId;
    private Long ts;
    private String signature;
    private Long txIdx;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ExpireUpdate {
    private Long orderId;
    private String fee;
    private Long ts;
    private String signature;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FillUpdate {
    private OrderSide side;
    private String fee;
    private BigDecimal amount;
    private BigDecimal price;
    private String oraclePrice;
    private Long orderId;
    private Integer marketIndex;
    private MarketType marketType;
    private Long ts;
    private Long txIdx;
    private String signature;
    private String maker;
    private Long makerOrderId;
    private String makerFee;
    private String taker;
    private Long takerOrderId;
    private String takerFee;

		public Long findActualOrderId(String clientPublicKey) {
			if (maker.equals(clientPublicKey)) {
				return makerOrderId;
			} else if (taker.equals(clientPublicKey)) {
				return takerOrderId;
			}
			return ObjectUtils.firstNonNull(orderId, makerOrderId, takerOrderId);
		}
	}

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CancelMissingUpdate {
    private Long userOrderId;
    private Long orderId;
    private Long ts;
    private String signature;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FundingPaymentUpdate {
    private String amount;
    private Integer marketIndex;
    private Long ts;
    private String signature;
    private Long txIdx;
  }
}
