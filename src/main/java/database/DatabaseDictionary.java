package database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseDictionary extends Dictionary {
    private static final String HOST_NAME = "localhost";
    private static final String PASS_WORD = "truonghaioop2023";
    private static final String DB_NAME = "word";
    private static final String USER_NAME = "Potato";
    private static final String PORT = "3306";
    private static final String MYSQL_URL =
            "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DB_NAME;
    private static Connection connection = null;

    /**
     * Connect to MYSQL database.
     */
    private void connectToDatabase() throws SQLException {
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASS_WORD);
        System.out .println("Connected to database successfully!");
    }

    public void initialize() throws SQLException {
        connectToDatabase();
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing connection!");
            e.printStackTrace();
        }
    }

    public void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing PreparedStatement!");
            e.printStackTrace();
        }
    }

    public void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing ResultSet!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing connection!");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Word> getAllWords() {
        return null;
    }

    @Override
    public ArrayList<String> getAllWordsTarget() {
        return null;
    }

    @Override
    public String lookUpWord(final String target) {
        final String SQL_QUERY = "SELECT definition FROM dictionary WHERE target = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        return rs.getString("definition");
                    } else {
                        return "";
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void addWord(String target, String explain) {

    }

    @Override
    public void editWord(String target, String explain) {

    }
}
