package trade.wayruha.drift.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MarketType {
  PERP("perp"), SPOT("spot");

  @JsonValue
  private final String name;

  MarketType(String name) {
    this.name = name;
  }
}
