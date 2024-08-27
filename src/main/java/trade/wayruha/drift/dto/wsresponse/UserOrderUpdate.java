package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Order order;
		Long ts;
		String signature;
		Long txIdx;

		@Data
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Order{
			Long slot;
			BigDecimal price;
			BigDecimal amount;
			BigDecimal filled;
			BigDecimal triggerPrice;
			BigDecimal auctionStartPrice;
			BigDecimal auctionEndPrice;
			Long maxTs;
			BigDecimal oraclePriceOffset;
			Long orderId;
			Integer marketIndex;
			String OrderType;
			String marketType;
			Long userOrderId;
			String direction;
			Boolean reduceOnly;
			Boolean postOnly;
			Long auctionDuration;
		}
	}

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CancelUpdate {
    Long orderId;
    Long ts;
    String signature;
    Long txIdx;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ExpireUpdate {
    Long orderId;
    String fee;
    Long ts;
    String signature;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FillUpdate {
    String side;
    String fee;
    BigDecimal amount;
    BigDecimal price;
    String oraclePrice;
    Long orderId;
    Integer marketIndex;
    String marketType;
    Long ts;
    Long txIdx;
    String signature;
    String maker;
    Long makerOrderId;
    String makerFee;
    String taker;
    Long takerOrderId;
    String takerFee;
  }

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CancelMissingUpdate {
		Long userOrderId;
		Long orderId;
		Long ts;
		String signature;
	}

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FundingPaymentUpdate {
    String amount;
    Integer marketIndex;
    Long ts;
    String signature;
    Long txIdx;
  }
}
