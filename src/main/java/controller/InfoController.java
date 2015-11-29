package controller;

import com.github.sarxos.webcam.Webcam;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Client;
import model.Person;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ribra on 11/28/2015.
 */
public class InfoController implements Initializable {
    public static Person person;

    @FXML
    ImageView photoView;

    @FXML
    Label nameLabel;

    @FXML
    Label birthDateLabel;

    @FXML
    Label cityLabel;

    @FXML
    Label phoneNumberLabel;

    @FXML
    Label emailLabel;

    @FXML
    Label creationDateLabel;

    @FXML
    Label universityLabel;

    @FXML
    Label placeLabel;

    @FXML
    Label reasonLabel;


    @FXML
    public void attendAction() {
        try {
            Client.getInstance().setAttended(phoneNumberLabel.getText());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setWrapText(true);
        placeLabel.setWrapText(true);
        reasonLabel.setWrapText(true);

        nameLabel.setText(person.getName());
        birthDateLabel.setText(person.getBirthDate());
        cityLabel.setText(person.getCity());
        phoneNumberLabel.setText(person.getPhoneNumber());
        emailLabel.setText(person.getEmail());
        creationDateLabel.setText(person.getCreationDate());
        universityLabel.setText(person.getUniversity());
        placeLabel.setText(person.getPlace());
        reasonLabel.setText(person.getReason());

        if (person.getImage() == null) {
            Image image = new Image("/images/no-image-available.gif");
            photoView.setImage(image);
        }
        else {
            photoView.setImage(person.getImage());
        }

        photoView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    if (PhotoController.webcam == null) {
                        PhotoController.webcam = Webcam.getDefault();
                        PhotoController.webcam.setViewSize(new Dimension(640, 480));
                        PhotoController.webcam.open(true);
                    }

                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/photo.fxml"));
                    Scene scene = new Scene(root, 600, 400);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("Take a photo");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setResizable(false);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
