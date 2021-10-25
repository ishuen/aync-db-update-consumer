import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradeDao {
  private static final Logger LOGGER = Logger.getLogger(TradeDao.class.getName());
  private Optional<Connection> connection;

  public TradeDao() {
    try {
      this.connection = JdbcConnection.getConnection();
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "connection failed");
    }
  }

  public Optional<Integer> save(TradeRecord tradeRecord) {
    String sql = "INSERT INTO trade_data (trade_id, symbol, exchange_code, price, quantity, update_time)"
      + "VALUES (?, ?, ?, ?, ?, ?)";
    return Objects.nonNull(tradeRecord) ? connection.flatMap(conn -> {
      Optional<Integer> generatedId = Optional.empty();
      try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, (int) tradeRecord.getId());
        statement.setString(2, tradeRecord.getSymbol());
        statement.setString(3, tradeRecord.getExchangeCode());
        statement.setFloat(4, (float) tradeRecord.getPrice());
        statement.setInt(5, tradeRecord.getSize());
        statement.setTimestamp(6, tradeRecord.getTimestamp());
        int numberOfInsertedRows = statement.executeUpdate();
        if (numberOfInsertedRows > 0) {
          try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
              return Optional.of(resultSet.getInt(1));
            }
          }
        }
      } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "ERROR", ex);
      }
      return Optional.empty();
    }) : Optional.empty();
  }
}