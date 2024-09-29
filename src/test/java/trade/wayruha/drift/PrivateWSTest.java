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
		driftConfig = new DriftConfig("8095", "127.0.0.1", "127.0.0.1:8091", "8096", "E:/Work/gateway-1.0.0/target/release/drift-gateway.exe", "https://go.getblock.io/fab4f72a499f464381808cdf952364d0");
//		driftConfig.setWebSocketPingIntervalSec(0);

//		httpGatewayService = new HttpGatewayService(privateKey, driftConfig);
//		httpGatewayService.startGateway();
//		httpGatewayService.setTimeoutSeconds(30);
//		httpGatewayService.waitForGateway(1000);

		final WebSocketPrivateClientFactory factory = new WebSocketPrivateClientFactory(driftConfig);
		final PrivateWSTest.Callback callback = new PrivateWSTest.Callback();
		final WebSocketSubscriptionClient<UserOrderUpdate> subscription = factory.privateUpdatesSubscription(callback);
		Thread.sleep(60_000);
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
