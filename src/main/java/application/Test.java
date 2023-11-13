package application;

import application.controller.DictionaryController;
import database.TextToSpeech;
import database.Tries;

import java.sql.SQLException;
import java.util.List;

import static database.Tries.words;

class Test {
    public static void main(String[] args) {
        try {
            System.out.println("Initialize application...");
            DictionaryController.databaseDictionary.initialize();
            Tries.initialize(DictionaryController.databaseDictionary.getAllWords());
            words.addAll(DictionaryController.databaseDictionary.getAllWords());
            System.out.println("Initialize application successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> list = Tries.getSearchWord();
        for (int i = 0; i < 100; ++i) {
            System.out.println(list.get(i));
        }
    }
}