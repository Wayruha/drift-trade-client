package trade.wayruha.drift.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class CancelOrderByIdsRequest {
	List<Integer> ids;
}
