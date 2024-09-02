package trade.wayruha.drift.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import trade.wayruha.drift.DriftConfig;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpGatewayService {
  private static final int DEFAULT_GATEWAY_TIMEOUT = 10;
  private final String host;
  private final Integer port;
  private final String privateKey;
  private final String gatewayPath;
  private final String rpcNode;
  private final URL gatewayHealthcheckUrl;
  @Setter
  private int timeoutSeconds;
  private ProcessResource processResource;

  private static final String DRIFT_GATEWAY_KEY = "DRIFT_GATEWAY_KEY";

  public HttpGatewayService(String privateKey, DriftConfig config) {
    this.host = config.getGatewayHost();
    this.port = Integer.parseInt(config.getGatewayPort());
    this.privateKey = privateKey;
    this.gatewayPath = config.getGatewayExecutablePath();
    this.rpcNode = config.getRpcNode();
    this.gatewayHealthcheckUrl = createGatewayHealthUrl();
    this.timeoutSeconds = DEFAULT_GATEWAY_TIMEOUT;
  }

  public ProcessResource startGateway() throws IOException {
    final ProcessBuilder processBuilder = new ProcessBuilder(
        gatewayPath,
        rpcNode,
        "--port", port.toString(),
        "--host", host
    );

    final Map<String, String> environment = processBuilder.environment();
    environment.put(DRIFT_GATEWAY_KEY, privateKey);

    final Process process = processBuilder.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (process.isAlive()) {
        process.destroy();
        log.info("Process destroyed on JVM shutdown.");
      }
    }));
    this.processResource = new ProcessResource(process);
    return processResource;
  }

  public boolean waitForGateway() throws InterruptedException {
    long startTime = System.currentTimeMillis();
    long endTime = startTime + TimeUnit.SECONDS.toMillis(timeoutSeconds);

    while (System.currentTimeMillis() < endTime) {
      if (isGatewayResponsive()) {
        return true;
      }
      TimeUnit.MILLISECONDS.sleep(500);
    }

    throw new RuntimeException("Failed to start gateway");
  }

  private boolean isGatewayResponsive() {
    try {
      final HttpURLConnection connection = (HttpURLConnection) gatewayHealthcheckUrl.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(1000);
      connection.setReadTimeout(1000);
      final int responseCode = connection.getResponseCode();
      return (responseCode == 200);
    } catch (IOException e) {
      return false;
    }
  }

  @SneakyThrows
  private URL createGatewayHealthUrl() {
    return new URL("http://" + host + ":" + port + "/v2/leverage");
  }

  @RequiredArgsConstructor
  public static class ProcessResource implements Closeable {
    private final Process process;

    @Override
    public void close() {
      if (process != null) {
        process.destroy();
        log.info("HttpGateway closed successfully...");
      }
    }
  }
}
