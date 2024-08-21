package trade.wayruha.drift.service;

import com.fasterxml.jackson.core.type.TypeReference;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.HttpClientBuilder;
import trade.wayruha.drift.dto.request.MarketPositionsRequest;
import trade.wayruha.drift.dto.request.PerpMarketPositionRequest;
import trade.wayruha.drift.dto.response.BalanceResponse;
import trade.wayruha.drift.dto.response.ExtMarketPositionItem;
import trade.wayruha.drift.dto.response.MarketInfoResponse;
import trade.wayruha.drift.dto.response.MarketMetadata;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.service.endpoint.MetadataEndpoints;

import java.util.List;

public class MetadataService extends ServiceBase {
  private static final String MARKETS_METADATA = "https://mainnet-beta.api.drift.trade/markets24h";
  private static final TypeReference<List<MarketMetadata>> METADATA_TYPE = new TypeReference<>() {
  };

//  private final OkHttpClient client;
  private final MetadataEndpoints metadataApi;

  public MetadataService(DriftConfig config) {
    super(config);
    final HttpClientBuilder clientBuilder = new HttpClientBuilder(config);
    this.metadataApi = createService(MetadataEndpoints.class);

//    this.client = clientBuilder.buildClient();
  }

  public MarketInfoResponse getMarketsInfo() {
    return client.executeSync(metadataApi.getMarkets());
  }

  public BalanceResponse getBalance() {
    return client.executeSync(metadataApi.getBalance());
  }

  public MarketPositionsResponse getMarketPosition(MarketPositionsRequest marketPositionsRequest) {
    return client.executeSync(metadataApi.getPositionsByMarket(marketPositionsRequest.getMarketIndex(), marketPositionsRequest.getMarketType().getName()));
  }

	public ExtMarketPositionItem getPerpMarketPosition(PerpMarketPositionRequest perpMarketPositionRequest){
		return client.executeSync(metadataApi.getPerpFullPositionInfo(perpMarketPositionRequest.getMarketIndex()));
	}

  public MarketPositionsResponse getAllMarketPositions() {
    return client.executeSync(metadataApi.getAllPositions());
  }

  public OrdersInfoResponse getAllOrders() {
    return client.executeSync(metadataApi.getAllOrders());
  }

}
