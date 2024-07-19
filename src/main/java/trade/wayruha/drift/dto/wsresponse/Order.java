package trade.wayruha.drift.dto.wsresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
  private BigInteger price;
  private BigInteger size;
}