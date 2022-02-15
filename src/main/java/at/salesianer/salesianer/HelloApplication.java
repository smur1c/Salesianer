package at.salesianer.salesianer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @FXML
    private final ImageView logoImage = new ImageView();

    private final Button object_button_addNewMachine = new Button();
    private final Button object_button_startSimulation = new Button();

    public static Scene window_scene;
    private VBox window_layout;
    public static Stage window_stage;


    @Override
    public void start(Stage stage) {
        window_layout = new VBox();
        window_layout.getChildren().addAll(mergeBoxesForMainWindowLayout());

        handleButtons();

        createStage(stage);
    }

    private void handleButtons(){
        object_button_addNewMachine.setOnAction(e -> new AddMachineClass());

        object_button_startSimulation.setOnAction(e-> new SimulationClass());
    }

    private void createStage(Stage stage){
        window_stage = stage;
        window_scene = new Scene(window_layout, 920, 500);
        window_stage.setTitle("Salesianer");
        window_stage.setScene(window_scene);
        window_stage.show();
    }

    public VBox mergeBoxesForMainWindowLayout(){
        Image image =  new Image("C:/Users/Sebastian Muric/Documents/Salesianer/Salesianer_Logo.png");
        logoImage.setImage(image);

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


}