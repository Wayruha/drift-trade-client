package trade.wayruha.drift.dto.wsrequest;


import lombok.Data;

@Data
public class WSMessage {
  private final String channel;

  public WSMessage(String channel) {
    this.channel = channel;
  }
}
