package database;

import javafx.concurrent.Task;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech extends Task<Void> {

    private final String text;
    private final String language;

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
            try {
                throw new Exception("Failed");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    protected Void call() throws Exception {
        try {
            playGoogleTranslateSound();
        } catch (Exception e) {
            throw new Exception("Task failed", e);
        }
        return null;
    }
}