package trade.wayruha.drift.dto.request;

import lombok.AllArgsConstructor;
import lombok.Value;
import trade.wayruha.drift.dto.MarketType;

@Value
@AllArgsConstructor
public class MarketPositionsRequest {
    int marketIndex;
    MarketType marketType;
}
