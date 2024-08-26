package trade.wayruha.drift.service;

import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.dto.request.MarketPositionsRequest;
import trade.wayruha.drift.dto.request.PerpMarketPositionRequest;
import trade.wayruha.drift.dto.response.BalanceResponse;
import trade.wayruha.drift.dto.response.ExtMarketPositionItem;
import trade.wayruha.drift.dto.response.MarketInfoResponse;
import trade.wayruha.drift.dto.response.MarketPositionsResponse;
import trade.wayruha.drift.dto.response.OrdersInfoResponse;
import trade.wayruha.drift.service.endpoint.MetadataEndpoints;

public class MetadataService extends ServiceBase {
  private final MetadataEndpoints metadataApi;

  public MetadataService(DriftConfig config) {
    super(config);
    this.metadataApi = createService(MetadataEndpoints.class);
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

  public ExtMarketPositionItem getPerpMarketPosition(PerpMarketPositionRequest perpMarketPositionRequest) {
    return client.executeSync(metadataApi.getPerpFullPositionInfo(perpMarketPositionRequest.getMarketIndex()));
  }

  public MarketPositionsResponse getAllMarketPositions() {
    return client.executeSync(metadataApi.getAllPositions());
  }

  public OrdersInfoResponse getAllOrders() {
    return client.executeSync(metadataApi.getAllOrders());
  }

}
