package database;

public class Question {

    private String question;
    private String[] answer;
    private int correctAnswer;
    private int chosenAnswer = -1;

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

    public int getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(int chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public boolean isCorrect() {
        return chosenAnswer == correctAnswer;
    }
}
