package database;

import java.util.*;

public class Tries {
    public static final ArrayList<Word> words = new ArrayList<>();
    public static final TrieNode root = new TrieNode();

    public static ArrayList<String> getSearchWord() {
        ArrayList<String> res = new ArrayList<>();
        for (Word word : words) {
            res.add(word.getWordTarget());
        }
        return res;
    }

    public static void initialize(ArrayList<Word> words) {
        words.addAll(words);
        for (Word word : words) {
            insertWordIntoTries(word.getWordTarget());
        }
        words.sort(Comparator.comparing(Word::getWordTarget));
    }

    public static void addWord(Word word) {
        words.add(word);
        insertWordIntoTries(word.getWordTarget());
        words.sort(Comparator.comparing(Word::getWordTarget));
    }

    public static void editWord(Word word) {
        int left = 0;
        int right = words.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (words.get(mid).getWordTarget().equals(word.getWordTarget())) {
                words.set(mid, word);
            } else if (words.get(mid).getWordTarget().compareTo(word.getWordTarget()) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }

    public static void deleteWord(String target) {
        int left = 0;
        int right = words.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (words.get(mid).getWordTarget().equals(target)) {
                words.remove(mid);
                deleteWordFromTries(target);
                return;
            } else if (words.get(mid).getWordTarget().compareTo(target) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
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
        word = word.toLowerCase();
        ArrayList<String> proposeWord = new ArrayList<>();
        TrieNode crawl = root;

        int length = word.length();
        for (int i = 0; i < length; ++i) {
            if (crawl.children.containsKey(word.charAt(i))) {
                crawl = crawl.children.get(word.charAt(i));
            } else {
                return new ArrayList<>();
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

    public static void deleteWordFromTries(String target) {
        TrieNode crawl = root;
        int length = target.length();
        for (int i = 0; i < length - 1; ++i) {
            if (crawl.children.containsKey(target.charAt(i))) {
                crawl = crawl.children.get(target.charAt(i));
            } else {
                return;
            }
        }
        if (crawl.children.get(target.charAt(length - 1)).children.isEmpty()) {
            crawl.children.remove(target.charAt(length - 1));
        } else{
            crawl.children.get(target.charAt(length - 1)).isEndOfWord = false;
        }
    }

    public static String wordDefinitionSearch(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        //Binary search
        int left = 0;
        int right = words.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (words.get(mid).getWordTarget().equals(word)) {
                return words.get(mid).getWordExplain();
            } else if (words.get(mid).getWordTarget().compareTo(word) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        for (Word word1 : words) {
            if (word1.getWordTarget().equals(word)) {
                return word1.getWordExplain();
            }
        }

        return "000";
    }

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();

        public boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
        }
    }

    public static Word getRamdomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}


