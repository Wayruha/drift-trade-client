package trade.wayruha.drift.dto.wsrequest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WSSubscription extends WSMessage {
  protected final String type;
  protected final String marketType;
  protected final String market;

  public WSSubscription(String type, String marketType, String channel, String market) {
    super(channel);
    this.type = type;
    this.marketType = marketType;
    this.market = market;
  }

  public static WSSubscription perpOrderBookSubscription(String coin) {
    return new WSSubscription("subscribe", "perp", "orderbook", coin.toUpperCase() + "-PERP");
  }

  public static WSSubscription unsubscribePerpOrderBook(String coin) {
    return new WSSubscription("unsubscribe", "perp", "orderbook", coin.toUpperCase() + "-PERP");
  }

  public static WSSubscription spotOrderBookSubscription(String coin) {
    return new WSSubscription("subscribe", "perp", "orderbook", coin.toUpperCase());
  }

  public static WSSubscription unsubscribeSpotOrderBook(String coin) {
    return new WSSubscription("unsubscribe", "perp", "orderbook", coin.toUpperCase());
  }
}
