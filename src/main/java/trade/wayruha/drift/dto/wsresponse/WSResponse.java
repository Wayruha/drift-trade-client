package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WSResponse<T> {
  private String channel;
  private T data;
}
