package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtMarketPositionItem extends MarketPositionItem{
	private BigDecimal averageEntry;
	private BigDecimal liquidationPrice;
	private BigDecimal unrealizedPnl;
	private BigDecimal unsettledPnl;
	private BigDecimal oraclePrice;

	@Override
	public String toString() {
		return super.toString() +
				", averageEntry=" + averageEntry +
				", liquidationPrice=" + liquidationPrice +
				", unrealizedPnl=" + unrealizedPnl +
				", unsettledPnl=" + unsettledPnl +
				", oraclePrice=" + oraclePrice;
	}
}
