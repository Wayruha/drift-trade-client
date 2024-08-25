package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOrderUpdate {
	private static final Logger log = LoggerFactory.getLogger(UserOrderUpdate.class);
	private final UserUpdatesType updatesType;
	private CreateUpdate createUpdate;
	private CancelUpdate cancelUpdate;
	private ExpireUpdate expireUpdate;
	private FillUpdate fillUpdate;
	private ModifyUpdate modifyUpdate;
	private FundingPaymentUpdate fundingPaymentUpdate;

	public UserOrderUpdate(JsonNode data, ObjectMapper objectMapper) {
		this.updatesType = defineUpdatesType(data);
		try {
			switch (Objects.requireNonNull(this.updatesType)) {
				case CANCEL:
					this.cancelUpdate = objectMapper.readValue(data.get(updatesType.getName()).toString(), CancelUpdate.class);
					return;
				case CREATE:
					this.createUpdate = objectMapper.readValue(data.get(updatesType.getName()).get("order").toString(), CreateUpdate.class);
					return;
				case EXPIRE:
					this.expireUpdate = objectMapper.readValue(data.get(updatesType.getName()).toString(), ExpireUpdate.class);
					return;
				case FILL:
					this.fillUpdate = objectMapper.readValue(data.get(updatesType.getName()).toString(), FillUpdate.class);
					return;
				case FUNDING:
					this.fundingPaymentUpdate = objectMapper.readValue(data.get(updatesType.getName()).toString(), FundingPaymentUpdate.class);
					return;
				case MODIFY:
					this.modifyUpdate = objectMapper.readValue(data.get(updatesType.getName()).toString(), ModifyUpdate.class);
					return;
			}
		}catch (Exception ex){
			log.error(ex.toString());
		}
	}

	private UserUpdatesType defineUpdatesType(JsonNode jsonNode) {
		try {
			if (jsonNode.has("orderCancel")) {
				return UserUpdatesType.CANCEL;
			} else if (jsonNode.has("orderExpire")) {
				return UserUpdatesType.EXPIRE;
			} else if (jsonNode.has("orderCreate")) {
				return UserUpdatesType.CREATE;
			} else if (jsonNode.has("fill")) {
				return UserUpdatesType.FILL;
			} else if (jsonNode.has("orderExpire")) {
				return UserUpdatesType.EXPIRE;
			} else if (jsonNode.has("orderCancelMissing")) {
				return UserUpdatesType.EXPIRE;
			} else if (jsonNode.has("fundingPayment")) {
				return UserUpdatesType.FUNDING;
			}	else{
				throw new IllegalStateException("No valid data type for this WSMessage");
			}
		}
		catch (Exception ex){
			log.error(ex.toString());
		}
		return null;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CreateUpdate {
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
	public static class ModifyUpdate {
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
