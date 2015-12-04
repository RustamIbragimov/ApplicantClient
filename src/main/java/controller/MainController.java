package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.Main;
import model.Client;
import model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ribra on 11/11/2015.
 */
public class MainController implements Initializable {

    @FXML
    private TextField portTextField;

    @FXML
    private TextField ipAddressTextField;

    private boolean isCorrectPort;


    @FXML
    public void signInAction() throws Exception{
        if (isCorrectPort) {
            String port = portTextField.getText();
            String ipAddress = ipAddressTextField.getText();
            Client.getInstance().build(Integer.valueOf(port), ipAddress);

            createSearcher();
        }
    }

    public void createSearcher() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/searcher.fxml"));
            Scene scene = new Scene(root, 400, 600);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Applicant Client");
            stage.setResizable(false);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    try {
                        Client.getInstance().exit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Main.getStage().close();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isCorrectPort(String port) {
        int length = port.length();
        if (length == 0) return true;
        if (length >= 5) return false;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(port.charAt(i))) return false;
        }
        if (Integer.valueOf(port) > 65535) return false;
        return true;
    }

    public void initialize(URL location, ResourceBundle resources) {
        portTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                isCorrectPort = (isCorrectPort(newValue)) ? true : false;
            }
        });

    }
}
