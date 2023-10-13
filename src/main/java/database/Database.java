package database;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Database {

    public void initialize() throws SQLException {}

    public void close() {}

    public abstract ArrayList<Word> getAllWords();

    public abstract ArrayList<String> getAllWordsTarget();

    public abstract String lookUpWord(final String target);

    public abstract void addWord(final String target, final String explain);

    public abstract void editWord(final String target, final String explain);
}
