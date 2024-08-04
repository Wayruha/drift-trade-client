package trade.wayruha.drift.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
@Getter
@AllArgsConstructor
public class CancelOrderByUserIdRequest {
    List<Integer> userIds;
}
