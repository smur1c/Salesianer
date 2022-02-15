package at.salesianer.salesianer;

import at.salesianer.connection.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMachineClass {
    public static Stage mStage;
    public static Scene mScene;

    private TextField container_machineName;
    private TextField container_machineCapacity;

    private ImageView logoImage = new ImageView();

    private ConnectionClass connection_connectionClass;
    private Connection connection;


    public AddMachineClass(){
        container_machineName = new TextField();
        container_machineCapacity = new TextField();
        createStageAndScene();
    }

    private void createStageAndScene(){
        mStage = new Stage();
        mScene = new Scene(createLayout(), 920, 500);
        mStage.setScene(mScene);
        mStage.show();
    }

    private VBox createLayout(){
        VBox vBox = new VBox();
        Image image =  new Image("C:/Users/Sebastian Muric/Documents/Salesianer/Salesianer_Logo.png");
        logoImage.setImage(image);
        vBox.getChildren().add(logoImage);
        vBox.setAlignment(Pos.TOP_LEFT);

        container_machineName.setPromptText("Maschinenenname");
        container_machineName.setMinWidth(10);
        TextField machineCapacity = new TextField();
        machineCapacity.setPromptText("Maschinenkapazität");

        VBox inputBox = new VBox();
        inputBox.getChildren().addAll(container_machineName, container_machineCapacity);
        inputBox.setSpacing(10);
        Label label = new Label();

        container_machineName.setOnAction(addMachineEvent);
        container_machineCapacity.setOnAction(addMachineEvent);

        VBox returnBox = new VBox();
        returnBox.getChildren().addAll(vBox, inputBox, label);

        return returnBox;
    }

    EventHandler<ActionEvent> addMachineEvent = e -> {
        try {
            createAndAddMachineToDatabase(container_machineName.getText(), container_machineCapacity.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    };

    public void createAndAddMachineToDatabase(String name, String machineCapacity) throws SQLException {
        Label label = new Label(name);
        Label capacity = new Label(machineCapacity);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, capacity);

        addMachineToDataBase(name, machineCapacity);

        mScene = new Scene(vbox, 100, 100);
        Stage stage = new Stage();
        stage.setScene(mScene);
        stage.show();
    }

    public void addMachineToDataBase(String name, String machineCapacity) throws SQLException {
        connection_connectionClass = new ConnectionClass();
        connection = connection_connectionClass.getConnection();
        String sqlName = "INSERT INTO SALESIANER (MachineName, MachineCapacity) VALUES('"+name+"', '"+machineCapacity+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlName);
        connection.close();
    }
}
