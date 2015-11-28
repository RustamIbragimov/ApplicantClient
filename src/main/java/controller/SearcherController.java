package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Client;
import model.Person;
import model.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ribra on 11/12/2015.
 */
public class SearcherController implements Initializable {

    @FXML
    TextField searchTextField;

    @FXML
    VBox vBox;

    private static List<Person> list;


    public void addToVBox() {
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            Button button = new Button(name);

            button.setPrefWidth(400);
            button.setMaxWidth(400);
            button.setMinWidth(400);

            button.setPrefHeight(70);
            button.setMaxHeight(70);
            button.setMinHeight(70);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Button clickedButton = (Button) event.getSource();
                    String name = clickedButton.getText();
                    openInfo(name);
                }
            });

            vBox.getChildren().add(button);
        }
    }

    public void openInfo(String name) {

        Person person = findPerson(name);
        InfoController.person = person;

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/info.fxml"));
            Scene scene = new Scene(root, 600, 700);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Applicant Client");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person findPerson(String name) {
        for (Person x : list) {
            if (x.getName().equals(name)) return x;
        }
        return null;
    }


    public void vBoxInit() {
        vBox.setAlignment(Pos.BASELINE_CENTER);
    }

    public void vBoxClear() {
        vBox.getChildren().clear();
    }

    public void initialize(URL location, ResourceBundle resources) {
        vBoxInit();


        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Utils.isNumber(newValue) && newValue.length() == 4) {
                    try {
                        vBoxClear();
                        list = Client.getInstance().getByNumber(newValue);
                        addToVBox();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
