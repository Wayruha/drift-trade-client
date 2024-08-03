package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPositionsResponse {
    @JsonProperty("spot")
    private List<MarketPositionItem> spotPositionsList;
    @JsonProperty("perp")
    private List<MarketPositionItem> perpPositionsList;
}
