package database;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Tries {

    public static final ArrayList<String> searchWord = new ArrayList<>();

    public static final TrieNode root = new TrieNode();

    public static ArrayList<String> getSearchWord() {
        return searchWord;
    }

    public static void insertWordIntoTries(String word) {
        int length = word.length();

        TrieNode crawl = root;

        for (int level = 0; level < length; level++) {
            char index = word.charAt(level);

            if (crawl.children.get(index) == null) {
                crawl.children.put(index, new TrieNode());
            }

            crawl = crawl.children.get(index);
        }

        crawl.isEndOfWord = true;
    }

    public static void insertAllWordsIntoTries(ArrayList<String> words) {
        for (String word : words) {
            insertWordIntoTries(word);
        }
    }

    public static void getWordSubTries(TrieNode crawl, String word) {

    }

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();

        public boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
        }
    }
}


