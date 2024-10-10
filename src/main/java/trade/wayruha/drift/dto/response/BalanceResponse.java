package trade.wayruha.drift.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {
  private BigDecimal balance;
  private BigDecimal unrealizedPnl;
  private BigDecimal totalCollateral;
  private BigDecimal freeCollateral;
  private BigDecimal totalInitialMargin;
  private BigDecimal totalMaintenanceMargin;
}
