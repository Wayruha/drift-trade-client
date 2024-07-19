package trade.wayruha.drift;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import trade.wayruha.drift.dto.wsresponse.OrderBookUpdate;
import trade.wayruha.drift.websocket.WebSocketCallback;
import trade.wayruha.drift.websocket.WebSocketClientFactory;
import trade.wayruha.drift.websocket.WebSocketSubscriptionClient;

public class WebSocketTest {
  @SneakyThrows
  public static void main(String[] args) {
    final DriftConfig config = new DriftConfig();
    final WebSocketClientFactory factory = new WebSocketClientFactory(config);
    final Callback callback = new Callback();
    final WebSocketSubscriptionClient<OrderBookUpdate> subscription = factory.perpOrderBookSubscription("SOL", callback);
    Thread.sleep(5_000);
  }

  static class Callback implements WebSocketCallback<OrderBookUpdate> {
    static final TypeReference<OrderBookUpdate> type = new TypeReference<>() {};
    @Override
    public void onResponse(OrderBookUpdate response) {
      System.out.println(response);

    }

    @Override
    public TypeReference<OrderBookUpdate> getType() {
      return type;
    }
  }
}
