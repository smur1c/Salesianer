package at.salesianer.salesianer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelloController {

    @FXML
    private ImageView logoImage = new ImageView();

    public void handleButton(){
        Image image =  new Image("C:/Users/Sebastian Muric/Desktop/Salesianer/Salesianer_Logo.png");
        logoImage.setImage(image);
    }
}

