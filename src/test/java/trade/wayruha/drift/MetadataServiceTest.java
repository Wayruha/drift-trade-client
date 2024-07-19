package trade.wayruha.drift;

import trade.wayruha.drift.dto.response.MarketMetadata;
import trade.wayruha.drift.service.MetadataService;

import java.util.List;

public class MetadataServiceTest {

  public static void main(String[] args) {
    final MetadataService service = new MetadataService(new DriftConfig());
    final List<MarketMetadata> markets = service.getMarkets();
    assert markets != null;
    assert markets.size() > 0;
  }
}
