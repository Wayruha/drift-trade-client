package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import trade.wayruha.drift.dto.MarketType;

import java.math.BigInteger;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookUpdate {
  private List<Order> bids;
  private List<Order> asks;
  private String marketName;
  @JsonAlias("marketType")
  private MarketType marketType;
  private int marketIndex;
  private long slot;
  private long oracle;
  private OracleData oracleData;
  private long marketSlot;
  @JsonAlias("ts")
  private long timestamp;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class OracleData {
    private BigInteger price;
    private long slot;
    private BigInteger confidence;
    private boolean hasSufficientNumberOfDataPoints;
    private BigInteger twap;
    private BigInteger twapConfidence;
  }
}