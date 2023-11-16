package database;

import java.sql.*;
import java.util.ArrayList;

public class GameDatabase implements DatabaseInformation {

    private static Connection connection = null;

    /**
     * Connect to MYSQL database.
     */
    private static void connectToDatabase() throws SQLException {
        System.out.println("Connecting to vocabulary database...");
        connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASS_WORD);
        System.out.println("Connected to database successfully!");
    }

    public static void initialize() throws SQLException {
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

    public ArrayList<Word> getWordsByTopic(String topic) {

        final String SQL_QUERY = "SELECT word, definition FROM vocabulary WHERE topic = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, topic);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    ArrayList<Word> words = new ArrayList<>();
                    while (rs.next()) {
                        words.add(new Word(rs.getString(1), rs.getString(2)));
                    }
                    return words;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public ArrayList<String> getAllTopics() {
        final String SQL_QUERY = "SELECT DISTINCT topic FROM vocabulary";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    ArrayList<String> topics = new ArrayList<>();
                    while (rs.next()) {
                        topics.add(rs.getString(1));
                    }
                    return topics;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
