package application;

import database.DatabaseDictionary;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        DatabaseDictionary db = new DatabaseDictionary();
        try {
            db.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = db.getAllWordsTarget();
        for (String test : result) {
            System.out.println(test);
        }
    }
}
