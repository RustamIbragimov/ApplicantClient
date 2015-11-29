package controller;

import com.github.sarxos.webcam.Webcam;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by ribra on 11/29/2015.
 */
public class PhotoController implements Initializable {

    @FXML
    ImageView photoView;

    public static Webcam webcam;

    private void liveImage() {
        while (true) {
            BufferedImage image = webcam.getImage();
            Image img = SwingFXUtils.toFXImage(image, null);
            photoView.setImage(img);
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> task = new Task<Void>() {
            @Override public Void call() {
                liveImage();
                return null;
            }
        };
        new Thread(task).start();
    }
}
