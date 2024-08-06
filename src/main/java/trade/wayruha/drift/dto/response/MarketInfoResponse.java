package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketInfoResponse {
  @JsonProperty("spot")
  private List<MarketInfoItem> spotPositionsList;
  @JsonProperty("perp")
  private List<MarketInfoItem> perpPositionsList;
}
