package trade.wayruha.drift.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import trade.wayruha.drift.DriftConfig;
import trade.wayruha.drift.config.ApiClient;

import static trade.wayruha.drift.config.Constant.DEFAULT_RECEIVING_WINDOW;

public abstract class ServiceBase {
  protected int receivingWindow = DEFAULT_RECEIVING_WINDOW;
  protected final ApiClient client;

  public ServiceBase(ApiClient client) {
    this.client = client;
  }

  public ServiceBase(DriftConfig config) {
    this(new ApiClient(config));
  }

  protected <T> T createService(Class<T> apiClass) {
    return client.createService(apiClass);
  }

  protected ObjectMapper getObjectMapper(){
    return client.getConfig().getObjectMapper();
  }

  protected long now() {
    return System.currentTimeMillis();
  }
}
