package trade.wayruha.drift.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import trade.wayruha.drift.DriftConfig;

public class RetrofitBuilder {

  public static Retrofit buildRetrofit(DriftConfig configuration, OkHttpClient httpClient) {
    final ObjectMapper objectMapper = configuration.getObjectMapper();

    Retrofit.Builder builder = new Retrofit.Builder();
    builder.client(httpClient);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    builder.baseUrl(configuration.getHost());
    return builder.build();
  }
}
