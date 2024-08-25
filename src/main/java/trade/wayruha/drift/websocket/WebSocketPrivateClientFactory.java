package trade.wayruha.drift.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.ApiClient;
import trade.wayruha.drift.dto.wsrequest.WSSubscription;
import trade.wayruha.drift.dto.wsresponse.UserOrderUpdate;
import trade.wayruha.drift.service.HttpGatewayService;

import java.util.Set;

public class WebSocketPrivateClientFactory {
	private final ApiClient apiClient;
	@Setter
	private ObjectMapper objectMapper;

	public WebSocketPrivateClientFactory(DriftConfig config) {
		config.setWebSocketHost("http://" + config.getGatewayHost() + ":1337");
		this.apiClient = new ApiClient(config);
		this.objectMapper = config.getObjectMapper();
	}

	public WebSocketSubscriptionClient<UserOrderUpdate> privateUpdatesSubscription(WebSocketCallback<UserOrderUpdate> callback){
		final WSSubscription sub = WSSubscription.privateUpdatesSubscription();
		final WebSocketSubscriptionClient<UserOrderUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback);
		client.connect(Set.of(sub));
		return client;
	}
}
