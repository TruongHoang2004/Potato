package application.potato;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class CircleController {
    @FXML
    private Circle circle;
    private int x;
    private int y;

    public void up() {
        circle.setCenterY(y -= 5);
    }

    public void down() {
        circle.setCenterY(y += 5);
    }

    public void left() {
        circle.setCenterX(x -= 5);
    }

    public void right() {
        circle.setCenterX(x += 5);
    }
}
