package trade.wayruha.drift.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {
  private BigDecimal balance;
}
