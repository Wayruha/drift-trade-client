package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import trade.wayruha.drift.dto.MarketType;
import trade.wayruha.drift.dto.OrderType;
import java.math.BigDecimal;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersInfoResponseItem {

    private OrderType orderType;
    private int marketIndex;
    private MarketType marketType;
    private BigDecimal amount;
    private BigDecimal filled;
    private BigDecimal price;
    private boolean postOnly;
    private boolean reduceOnly;
    private int userOrderId;
    private int orderId;
}
