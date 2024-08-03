package trade.wayruha.drift.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PlaceOrderRequest {
  @JsonProperty("orders")
  List<PlaceOrderParams> placeOrderParamsList;
}
