import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class CircleController {
    @FXML
    private Circle circle;
    private int x;
    private int y;

    public CircleController() {
    }

    public void up() {
        circle.setCenterX(--y);
    }

    public void down() {
        circle.setCenterX(++y);
    }

    public void left() {
        circle.setCenterX(--x);
    }

    public void right() {
        circle.setCenterX(++x);
    }
}
