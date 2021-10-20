import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TradeRecord {
  @JsonProperty("T")
  private String type;
  @JsonProperty("S")
  private String symbol;
  @JsonProperty("i")
  private long id;
  @JsonProperty("x")
  private String exchangeCode;
  @JsonProperty("p")
  private double price;
  @JsonProperty("s")
  private int size;
  @JsonProperty("t")
  private Timestamp timestamp;
  @JsonProperty("c")
  private String[] conditions;
  @JsonProperty("z")
  private String tape;
}