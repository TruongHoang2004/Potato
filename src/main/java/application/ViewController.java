package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController {

    @FXML
    public ImageView potatoImage() {
        Image icon = new Image("icon.png");
        ImageView image = new ImageView();
        image.setImage(icon);
        return  image;
    }

    @FXML
    public Label potatoLabel;

}
