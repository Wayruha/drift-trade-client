package trade.wayruha.drift.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import trade.wayruha.drift.dto.MarketType;

import java.math.BigDecimal;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketMetadata {
  private Map<String, Object> marketType;
  private int marketIndex;
  private String symbol;
  private BigDecimal baseVolume;
  private BigDecimal quoteVolume;
  private BigDecimal baseVolume30D;
  private BigDecimal quoteVolume30D;
  private BigDecimal price24hAgo;
  private BigDecimal pricePercentChange;
  private BigDecimal priceHigh;
  private BigDecimal priceLow;
  private BigDecimal marketCap;
  private int dailyVolumeIncreaseZScore;

  public MarketType getMarketType() {
    if (marketType == null || marketType.size() == 0) return null;
    return marketType.containsKey(MarketType.PERP.name()) ? MarketType.PERP : MarketType.SPOT;
  }

  public String getBaseAsset() {
    if (symbol == null || symbol.length() == 0) return null;
    return symbol.replace("-PERP", "").toUpperCase();
  }
}