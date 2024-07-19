package trade.wayruha.drift.exception;

public class DriftException extends RuntimeException {
  public DriftException(String message) {
    super(message);
  }

  public DriftException(String message, Throwable cause) {
    super(message, cause);
  }
}
