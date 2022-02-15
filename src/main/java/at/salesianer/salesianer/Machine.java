package at.salesianer.salesianer;

import at.salesianer.connection.ConnectionClass;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Machine{
    public static List<ConnectionMachines> machineList = new ArrayList<>();
    public static List<Button> buttonList = new ArrayList<>();
    public static Button clicked = new Button();
    private String mCapacity;
    private String mName;
    public static Line line = new Line();
    Button button = new Button();

    public Machine(String name, String capacity){
        mName = name;
        mCapacity = capacity;
    }

    public void handleMachinePropertiesButton(){
        button.setText(mName);
        button.setOnMouseClicked(eventForButton);
        buttonList.add(button);
    }

    public void makeObjectDraggable(){
        button.setOnMouseDragged(e -> {
            button.setLayoutX(e.getSceneX() - 20);
            button.setLayoutY(e.getSceneY() - 10);
        });
    }


    public EventHandler<MouseEvent> eventForButton = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent e) {
            if (e.getClickCount() == 2 && machineList.size() < 1) {
                try {
                    getAndAddDataBaseContent();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if(e.getClickCount() == 2){
                createStageForAvailableConnectorMachine();
            }
        }
    };

    public void getAndAddDataBaseContent() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String sql = "SELECT MachineName, MachineCapacity FROM SALESIANER";
        Statement statement;
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            machineList.add(new ConnectionMachines(rs.getString("MachineName"), rs.getString("MachineCapacity")));
        }
        connection.close();
    }

    public void createStageForAvailableConnectorMachine() {
        VBox vBox = new VBox();
        clicked = this.button;
        for (int i = 0; i < machineList.size(); i++) {
            if(!this.button.getText().equals(machineList.get(i).connectButton.getText())){
                vBox.getChildren().add(machineList.get(i).connectButton);
            }
        }
        vBox.setSpacing(10);
        HelloApplication.window_scene = new Scene(vBox, 100, 100);
        HelloApplication.window_stage = new Stage();
        HelloApplication.window_stage.setScene(HelloApplication.window_scene);
        HelloApplication.window_stage.show();
    }

    public void drawLine() {
        if (getButton() != null) {
            line = new Line(clicked.getLayoutX() + 20, clicked.getLayoutY() + 10, getButton().getLayoutX() + 20, getButton().getLayoutY() + 10);
            HelloApplication.window_stage.hide();
            HelloApplication.createStageForStartSimulationWindow();
        }
    }

    public Button getButton(){
        for (int i = 0; i < machineList.size(); i++) {
            if(ConnectionMachines.currentPressedButton.getText().equals(HelloApplication.container_availableMachines.get(i).button.getText())){
                return HelloApplication.container_availableMachines.get(i).button;
            }
        }
        return null;
    }

}
