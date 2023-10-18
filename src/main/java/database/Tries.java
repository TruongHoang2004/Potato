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

    public static ArrayList<String> getWordSub(String word) {
        ArrayList<String> proposeWord = new ArrayList<>();
        TrieNode crawl = root;

        int length = word.length();
        for (int i = 0; i < length; ++i) {
            if (crawl.children.containsKey(word.charAt(i))) {
                crawl = crawl.children.get(word.charAt(i));
            } else {
                return new ArrayList<String>();
            }
        }

        assert crawl != null;
        getSub(proposeWord, crawl, word);
        return proposeWord;
    }

    private static void getSub(ArrayList<String> proposeWord, TrieNode crawl, String word) {
        if (crawl.isEndOfWord) {
            proposeWord.add(word);
        }

        for (Character entry : crawl.children.keySet()) {
            getSub(proposeWord, crawl.children.get(entry), word + entry);
        }
    }

    public static void insertAllWordsIntoTries(ArrayList<String> words) {
        for (String word : words) {
            insertWordIntoTries(word);
        }
    }

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();

        public boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
        }
    }
}


