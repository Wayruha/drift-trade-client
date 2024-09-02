package trade.wayruha.drift.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderSide {
  BUY("buy"),
  SELL("sell");

  @Getter
  @JsonValue
  private final String name;
}
