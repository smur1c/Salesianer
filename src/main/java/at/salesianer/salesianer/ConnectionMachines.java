package at.salesianer.salesianer;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.sql.SQLException;

public class ConnectionMachines extends Machine {

    public Button connectButton;
    public static Button currentPressedButton;
    public ConnectionMachines(String name, String capacity) throws SQLException {
        super(name, capacity);
        connectButton = new Button();
        connectButton.setText(name);
        connectButton.setOnMouseClicked(e -> {
            currentPressedButton = getCurrentPressedButton();
            drawLine();
        });
    }

    public Button getCurrentPressedButton(){
        return this.connectButton;
    }

}
