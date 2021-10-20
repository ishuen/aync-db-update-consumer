import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {
  private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class.getName());
  private static Optional<Connection> connection = Optional.empty();

  public static Optional<Connection> getConnection() throws Exception {
    if (!connection.isPresent()) {
      String configPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "db.config";
      Properties keyProps = new Properties();
      keyProps.load(new FileInputStream(configPath));
      String url = keyProps.getProperty("url");
      String user = keyProps.getProperty("user");
      String password = keyProps.getProperty("password");

      try {
        connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
      } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      }
    }
    return connection;
  }
}
