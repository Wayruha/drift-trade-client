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
  protected final String method;
  protected final Integer subAccountId;

  public WSSubscription(String type, String marketType, String channel, String market, String method, Integer subAccountId) {
    super(channel);
    this.type = type;
    this.marketType = marketType;
    this.market = market;
    this.method = method;
    this.subAccountId = subAccountId;
  }

  public static WSSubscription perpOrderBookSubscription(String coin) {
    return new WSSubscription("subscribe", "perp", "orderbook", coin.toUpperCase() + "-PERP", null, null);
  }

  public static WSSubscription unsubscribePerpOrderBook(String coin) {
    return new WSSubscription("unsubscribe", "perp", "orderbook", coin.toUpperCase() + "-PERP", null, null);
  }

  public static WSSubscription spotOrderBookSubscription(String coin) {
    return new WSSubscription("subscribe", "perp", "orderbook", coin.toUpperCase(), null, null);
  }

  public static WSSubscription unsubscribeSpotOrderBook(String coin) {
    return new WSSubscription("unsubscribe", "perp", "orderbook", coin.toUpperCase(), null, null);
  }

  public static WSSubscription privateUpdatesSubscription(Integer subAccountId) {
    int subAccount = subAccountId != null ? subAccountId : 0;
    return new WSSubscription(null, null, null, null, "subscribe", subAccount);
  }

  public static WSSubscription unsubscribePrivateUpdates(Integer subAccountId) {
    int subAccount = subAccountId != null ? subAccountId : 0;
    return new WSSubscription(null, null, null, null, "unsubscribe", subAccount);
  }
}
