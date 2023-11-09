package application;

import database.TextToSpeech;

class Test {
    public static void main(String[] args) {
        TextToSpeech task = new TextToSpeech("Hải ngu như chó", "vi");
        task.setOnSucceeded(event -> {
            task.getValue();
            System.out.println("Done");
        });
        task.setOnFailed(event -> System.out.println("Failed"));
        Thread thread = new Thread(task);
        thread.start();
    }
}