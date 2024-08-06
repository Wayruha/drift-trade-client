package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketInfoItem {
  private Integer marketIndex;
  private String symbol;
  private BigDecimal priceStep; //smallest order price increment for the market
  private BigDecimal amountStep; //smallest order amount increment for the market
  private BigDecimal minOrderSize;
  private BigDecimal initialMarginRatio; //collateral required to open position
  private BigDecimal maintenanceMarginRatio; //collateral required to maintain position
}
