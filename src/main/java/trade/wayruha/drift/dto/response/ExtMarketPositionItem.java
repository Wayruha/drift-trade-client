package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtMarketPositionItem extends MarketPositionItem {
  private BigDecimal averageEntry;
  private BigDecimal liquidationPrice;
  private BigDecimal unrealizedPnl;
  private BigDecimal unsettledPnl;
  private BigDecimal oraclePrice;
}
