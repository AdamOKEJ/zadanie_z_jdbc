import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbRepairConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/serwis";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS zgloszenia ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "sprzet VARCHAR(255) NOT NULL, "
                   + "koszt_naprawy DOUBLE NOT NULL"
                   + ")";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
