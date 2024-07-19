package trade.wayruha.drift.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.HttpClientBuilder;
import trade.wayruha.drift.dto.response.MarketMetadata;
import trade.wayruha.drift.exception.DriftException;

import java.io.IOException;
import java.util.List;

public class MetadataService extends ServiceBase {
  private static final String MARKETS_METADATA = "https://mainnet-beta.api.drift.trade/markets24h";
  private static final TypeReference<List<MarketMetadata>> METADATA_TYPE = new TypeReference<>() {
  };

  private final OkHttpClient client;

  public MetadataService(DriftConfig config) {
    super(config);
    final HttpClientBuilder clientBuilder = new HttpClientBuilder(config);
    this.client = clientBuilder.buildClient();
  }

  public List<MarketMetadata> getMarkets() {
    Request request = new Request.Builder()
        .url(MARKETS_METADATA)
        .build();

    try (Response response = this.client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new DriftException("Unexpected code " + response);

      // Parse the response
      ObjectMapper mapper = new ObjectMapper();
      final String json = response.body().string();
      final ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);

      return mapper.convertValue(jsonNode.get("data"), METADATA_TYPE);
    } catch (IOException e) {
      throw new DriftException("GET MARKETS_METADATA failed.", e);
    }
  }
}
