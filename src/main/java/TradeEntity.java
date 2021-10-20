import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Getter
@Setter
@Accessors(chain = true)
public class TradeEntity {
  private String symbol;
  private long id;
  private String exchangeCode;
  private double price;
  private int quantity;
  private Timestamp updateTime;
}
