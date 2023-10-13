package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController extends MenuController implements Initializable {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private TextArea fontTextArea = new TextArea();

    @FXML
    private WebView webView = new WebView();

    private WebEngine webEngine;

    @FXML
    private TextArea recommendedArea = new TextArea();

    @FXML
    private AnchorPane resultAnchorPane = new AnchorPane();

    public void search() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadPage();
    }

    public void loadPage() {
        webEngine.loadContent("<I><Q>@application /,æpli''keiʃn/<br />*  danh từ<br />- sự gắn vào, sự áp vào, sự ghép vào, sự đính vào, sự đắp vào, sự tra vào ((cũng) appliance)<br />=the application of a plaster to a wound+ sự đắp thuốc vào vết thương<br />- vật gắn, vật áp, vật ghép, vật đính, vật đắp, vật tra<br />- sự dùng, sự áp dụng, sự ứng dụng<br />=medicine for external application+ thuốc dùng ngoài da<br />- sự chuyên cần, sự chuyên tâm<br />=a man of close application+ một người rất chuyên cần<br />- lời xin, lời thỉnh cầu; đơn xin<br />=application for a job+ đơn xin việc làm<br />=to make an application to someone for something+ gửi đơn cho ai để xin việc gì<br />=to put in an application+ đệ đơn xin, gửi đơn xin<br /><br />@application<br />- (Tech) ứng dụng; chương trình ứng dụng<br /><br />@application<br />- phép trải, sự ứng dụng</Q></I>", "text/html");
    }
}
