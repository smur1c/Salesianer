package at.salesianer.salesianer;
import at.salesianer.connection.ConnectionClass;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @FXML
    private ImageView logoImage = new ImageView();

    private Button object_button_addNewMachine = new Button();
    private Button object_button_startSimulation = new Button();

    public static Scene window_scene;
    private VBox window_layout;
    public static Stage window_stage;

    public static List<Machine> container_availableMachines = new ArrayList<>();
    private TextField container_machineName = new TextField();
    private TextField container_machineCapacity = new TextField();

    private ConnectionClass connection_connectionClass;
    private Connection connection;




    @Override
    public void start(Stage stage) {
        Image image =  new Image("C:/Users/Sebastian Muric/Desktop/Salesianer/Salesianer_Logo.png");
        logoImage.setImage(image);

        window_layout = new VBox();
        window_layout.getChildren().addAll(mergeBoxesForMainWindowLayout());

        object_button_addNewMachine.setOnAction(e ->{
            handleAddMachineButton();
        });

        object_button_startSimulation.setOnAction(e->{
            try {
                handleStartSimulationButton();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        createStage(stage);
    }

    EventHandler<ActionEvent> addMachineEvent = e -> {
        try {
            createAndAddMachineToDatabase(container_machineName.getText(), container_machineCapacity.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    };


    private void createStage(Stage stage){
        window_stage = getMainStage(stage);
        window_scene = new Scene(window_layout, 920, 500);
        window_stage.setTitle("Salesianer");
        window_stage.setScene(window_scene);
        window_stage.show();
    }

    public static Stage getMainStage(Stage stage){
        return stage;
    }

    public VBox mergeBoxesForMainWindowLayout(){
        VBox hBox = new VBox();
        hBox.getChildren().add(logoImage);
        hBox.setAlignment(Pos.TOP_LEFT);

        object_button_startSimulation.setMinHeight(30);
        object_button_addNewMachine.setMinHeight(30);

        VBox buttons = new VBox();
        buttons.getChildren().addAll(object_button_addNewMachine, object_button_startSimulation);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        object_button_addNewMachine.setText("Neue Maschine anlegen");
        object_button_startSimulation.setText("    Simulation starten    ");

        VBox returnBox = new VBox();
        returnBox.getChildren().addAll(hBox, buttons);
        returnBox.setSpacing(50);

        return returnBox;
    }

    public void handleAddMachineButton(){
        createStageForNewMachineWindow();
    }

    public void createStageForNewMachineWindow(){
        Stage stage = new Stage();
        window_scene = new Scene(createLayoutForNewMachineStage(), 920, 500);
        stage.setScene(window_scene);
        window_stage.hide();
        stage.show();
    }

    private VBox createLayoutForNewMachineStage(){
        VBox hBox = new VBox();
        hBox.getChildren().add(logoImage);
        hBox.setAlignment(Pos.TOP_LEFT);

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
        returnBox.getChildren().addAll(hBox, inputBox, label);

        return returnBox;
    }
    public void createAndAddMachineToDatabase(String name, String machineCapacity) throws SQLException {
        Label label = new Label(name);
        Label capacity = new Label(machineCapacity);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, capacity);

        addMachineToDataBase(name, machineCapacity);

        window_scene = new Scene(vbox, 100, 100);
        Stage stage = new Stage();
        stage.setScene(window_scene);
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

    public static Pane getStarterLayoutForSimulationStage(){
        Pane pane = new Pane();
        for (int i = 0; i < container_availableMachines.size(); i++) {
            pane.getChildren().add(container_availableMachines.get(i).button);
        }
        pane.getChildren().add(Machine.line);
        return pane;
    }

    public void handleStartSimulationButton() throws SQLException {
        getContentFromDateBaseAndAddToContainer();
        createStageForStartSimulationWindow();
    }


    public static void createStageForStartSimulationWindow(){
        window_stage.hide();
        Stage stage = new Stage();
        window_scene = new Scene(getStarterLayoutForSimulationStage(), 920, 500);
        stage.setScene(window_scene);
        stage.show();
    }

    public void getContentFromDateBaseAndAddToContainer() throws SQLException {

        for(Machine m : container_availableMachines){
            container_availableMachines.remove(m);
        }
        connection_connectionClass = new ConnectionClass();
        connection = connection_connectionClass.getConnection();
        String sql = "SELECT MachineName, MachineCapacity FROM SALESIANER";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Machine machine = new Machine(rs.getString("MachineName"), rs.getString("MachineCapacity"));
            machine.handleMachinePropertiesButton();
            machine.makeObjectDraggable();
            container_availableMachines.add(machine);

        }
        connection.close();
        connection = null;
    }
}