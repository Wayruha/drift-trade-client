package trade.wayruha.drift.dto.request;

import lombok.Value;
import trade.wayruha.drift.dto.MarketType;

@Value
public class MarketPositionsRequest {
  int marketIndex;
  MarketType marketType;
}
