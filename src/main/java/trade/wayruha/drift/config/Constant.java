package trade.wayruha.drift.config;

public class Constant {
  /**
   * The default timeout is 30 seconds.
   */
  public static final long HTTP_CLIENT_TIMEOUT_MS = 30 * 1000;

  /**
   * max allowed discrepancy in nonce (i.e., timestamp, millis)
   */
  public static final int DEFAULT_RECEIVING_WINDOW = 60_000;
  public static final String WEBSOCKET_INTERRUPTED_EXCEPTION = "The server terminated the connection for an unknown reason";
  public static final String API_CLIENT_ERROR_MESSAGE_PARSE_EXCEPTION = "Can't parse error message";

  public static final int MAX_WS_BATCH_SUBSCRIPTIONS = 1000;

  // precisions
  public static final int BASE_PRECISION = 9;
  public static final int QUOTE_PRECISION = 6;
  public static final int PRICE_PRECISION = 6;
  public static final int FUNDING_RATE_PRECISION = 9;
  //  public static final int SPOT_MARKET_CONFIG_PRECISION = 6; // Derived from token mint's decimals (USDC is 6, SOL is 9)
  public static final int SPOT_MARKET_BALANCE_PRECISION = 9;
  public static final int MARGIN_PRECISION = 4;
  public static final int SPOT_WEIGHT_PRECISION = 4;
}
