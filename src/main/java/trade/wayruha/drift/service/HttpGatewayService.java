package trade.wayruha.drift.service;

import lombok.extern.slf4j.Slf4j;
import trade.wayruha.drift.DriftConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpGatewayService {
  private final String host;
  private final Integer port;
  private final String privateKey;
  private final String gatewayPath;
  private final String rpcNode;
  private Process process;

  private static final String DRIFT_GATEWAY_KEY = "DRIFT_GATEWAY_KEY";

  public HttpGatewayService(String privateKey, DriftConfig config) {
    this.host = config.getGatewayHost();
    this.port = Integer.parseInt(config.getGatewayPort());
    this.privateKey = privateKey;
    this.gatewayPath = config.getGatewayExecutablePath();
    this.rpcNode = config.getRpcNode();
  }

  public void startGateway() throws IOException, InterruptedException {
    final ProcessBuilder processBuilder = new ProcessBuilder(
        gatewayPath,
        rpcNode,
        "--port", port.toString(),
        "--host", host
    );

    final Map<String, String> environment = processBuilder.environment();
    environment.put(DRIFT_GATEWAY_KEY, privateKey);

    process = processBuilder.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (process.isAlive()) {
        process.destroy();
        log.info("Process destroyed on JVM shutdown.");
      }
    }));

    TimeUnit.SECONDS.sleep(2);
  }

  public void stopGateway() throws InterruptedException {
    if (process != null && process.isAlive()) {
      process.destroy();
    }
  }
}
