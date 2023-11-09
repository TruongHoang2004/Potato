package database;

import javafx.concurrent.Task;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech extends Task<Void> {

    private String text;
    private String language;

    public TextToSpeech(String text, String language) {
        this.text = text;
        this.language = language;
    }

    public void playGoogleTranslateSound() {
        try {
            String api =
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                            + language
                            + "&client=tw-ob&q="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
    }

    @Override
    protected Void call() throws Exception {
        playGoogleTranslateSound();
        return null;
    }
}