import java.sql.*;
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

  public Optional<Integer> save(TradeEntity tradeEntity) {
    String sql = "INSERT INTO trade_data (trade_id, symbol, exchange_code, price, quantity, update_time)"
      + "VALUES (?, ?, ?, ?, ?, ?)";
    return Objects.nonNull(tradeEntity) ? connection.flatMap(conn -> {
      Optional<Integer> generatedId = Optional.empty();
      try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, (int) tradeEntity.getId());
        statement.setString(2, tradeEntity.getSymbol());
        statement.setString(3, tradeEntity.getExchangeCode());
        statement.setFloat(4, (float) tradeEntity.getPrice());
        statement.setInt(5, tradeEntity.getQuantity());
        statement.setTimestamp(6, tradeEntity.getUpdateTime());
        int numberOfInsertedRows = statement.executeUpdate();
        if (numberOfInsertedRows > 0) {
          try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
              generatedId = Optional.of(resultSet.getInt(1));
            }
          }
        }
      } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "ERROR", ex);
      }
      return generatedId;
    }) : null;
  }
}