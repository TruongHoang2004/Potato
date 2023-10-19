package database;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

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
    private static void connectToDatabase() throws SQLException {
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
        ArrayList<Word> allWords = new ArrayList<>();

        final String SQL_QUERY = "SELECT * FROM dictionary";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
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

    @Override
    public ArrayList<String> getAllWordsTarget() {
        ArrayList<String> allTargetWord = new ArrayList<>();

        final String SQL_QUERY = "SELECT target FROM dictionary";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    ArrayList<String> targets = new ArrayList<>();
                    while (rs.next()) {
                        targets.add(rs.getString(1));
                    }
                    return targets;
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
        final String SQL_QUERY = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
           ps.setString(1, target);
           ps.setString(2, explain);
           ps.execute();
           showEditResultDialog(target + " have been add to dictionary", "Add result");
           close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editWord(String target, String explain) {
        final String SQL_QUERY = "UPDATE dictionary SET definition = ? WHERE target = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, explain);
            ps.setString(2, target);
            ps.execute();
            showEditResultDialog("Mean of " + target + " have been change", "Edit result");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWord(String target) {
        final  String SQL_QUERY = "DELETE FROM dictionary WHERE target = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            ps.execute();
            close(ps);
            showEditResultDialog(target + " have been delete from dictionary", "Delete result");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEditResultDialog(String content, String tittle) {
        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(okButtonType);
        dialog.setTitle(tittle);
        dialog.setContentText(content);
        dialog.getDialogPane().lookupButton(okButtonType).setDisable(false);
        dialog.show();
    }
}
