package trade.wayruha.drift.service;

import lombok.extern.slf4j.Slf4j;
import trade.wayruha.drift.DriftConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
  }

  public void stopGateway() {
    if (process != null && process.isAlive()) {
      process.destroy();
    }
  }

	public boolean waitForGateway(int timeoutSeconds) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + TimeUnit.SECONDS.toMillis(timeoutSeconds);

		while (System.currentTimeMillis() < endTime) {
			if (isGatewayResponsive()) {
				return true;
			}
			TimeUnit.MILLISECONDS.sleep(500);
		}

		return false;
	}

	private boolean isGatewayResponsive() {
		try {
			final URL url = new URL("http://" + host + ":" + port + "/leverage");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(1000);
			final int responseCode = connection.getResponseCode();
			return (responseCode == 200);
		} catch (IOException e) {
			return false;
		}
	}
}
