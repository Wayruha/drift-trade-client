package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPositionItem {
  private BigDecimal amount;
  private String type;
  private int marketIndex;
}
