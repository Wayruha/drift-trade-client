package trade.wayruha.drift;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import trade.wayruha.drift.dto.wsresponse.UserOrderUpdate;
import trade.wayruha.drift.service.HttpGatewayService;
import trade.wayruha.drift.websocket.WebSocketCallback;
import trade.wayruha.drift.websocket.WebSocketPrivateClientFactory;
import trade.wayruha.drift.websocket.WebSocketSubscriptionClient;

public class PrivateWSTest {

	private static DriftConfig driftConfig;
	private static HttpGatewayService httpGatewayService;
	private static String privateKey = "";

	@SneakyThrows
	public static void main(String[] args) {
		driftConfig = new DriftConfig("8090", "127.0.0.10", "127.0.0.10:1337", "../gateway-1.0.0/target/release/drift-gateway.exe", "");
		driftConfig.setWebSocketPingIntervalSec(0);

		httpGatewayService = new HttpGatewayService(privateKey, driftConfig);
		httpGatewayService.startGateway();
		httpGatewayService.waitForGateway();

		final WebSocketPrivateClientFactory factory = new WebSocketPrivateClientFactory(driftConfig);
		final PrivateWSTest.Callback callback = new PrivateWSTest.Callback();
		final WebSocketSubscriptionClient<UserOrderUpdate> subscription = factory.privateUpdatesSubscription(callback);
		Thread.sleep(5_000);
	}

	static class Callback implements WebSocketCallback<UserOrderUpdate> {
		static final TypeReference<UserOrderUpdate> type = new TypeReference<>() {};
		@Override
		public void onResponse(UserOrderUpdate response) {
			System.out.println(response);
		}

		@Override
		public TypeReference<UserOrderUpdate> getType() {
			return type;
		}
	}
}
