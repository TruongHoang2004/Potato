package database;

public class Word {
    private final String wordTarget;
    private String wordExplain;

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    @Override
    public String toString() {
        return "Word{" +
               "wordTarget='" + wordTarget + '\'' +
               ", wordExplain='" + wordExplain + '\'' +
               '}';
    }
}
