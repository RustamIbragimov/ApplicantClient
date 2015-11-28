package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Client;
import model.Person;

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
        photoView.setImage(person.getImage());
    }
}
