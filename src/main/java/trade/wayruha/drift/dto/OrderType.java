package trade.wayruha.drift.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderType {
    LIMIT("limit"), MARKET("market");

    @JsonValue
    private final String name;

    OrderType(String name) { this.name = name; }
}
