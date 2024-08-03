package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPositionsResponse {
  @JsonProperty("spot")
  private List<MarketPositionItem> spotPositionsList; // todo change type
  @JsonProperty("perp")
  private List<MarketPositionItem> perpPositionsList;
}
