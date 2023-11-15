package database;

public class Question {

    private String question;
    private String[] answer;
    private int correctAnswer;

    public Question(String question, String[] answer, int correctAnswer) {
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswer() {
        return answer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
