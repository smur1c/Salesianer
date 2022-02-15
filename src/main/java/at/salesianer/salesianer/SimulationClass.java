package at.salesianer.salesianer;

import at.salesianer.connection.ConnectionClass;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimulationClass {
    public static Pane layout;
    public static List<Machine> container_availableMachines = new ArrayList<>();

    public static Stage mStage;
    public static Scene mScene;

    public SimulationClass(){
        layout = new Pane();
        try{
            handleStartSimulationButton();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Pane getStarterLayoutForSimulationStage(){
        for (Machine container_availableMachine : container_availableMachines) {
            if (!layout.getChildren().contains(container_availableMachine.button)) {
                layout.getChildren().add(container_availableMachine.button);
            }
        }
        layout.getChildren().add(Machine.line);
        return layout;
    }

    public void handleStartSimulationButton() throws SQLException {
        getContentFromDateBaseAndAddToContainer();
        createStageForStartSimulationWindow();
    }

    public static void createStageForStartSimulationWindow(){
        mStage = new Stage();
        mScene = new Scene(getStarterLayoutForSimulationStage(), 920, 500);
        mStage.setScene(mScene);
        mStage.show();
    }

    public void getContentFromDateBaseAndAddToContainer() throws SQLException {
        for(Machine m : container_availableMachines){
            container_availableMachines.remove(m);
        }
        ConnectionClass connection_connectionClass = new ConnectionClass();
        Connection connection = connection_connectionClass.getConnection();
        String sql = "SELECT MachineName, MachineCapacity FROM SALESIANER";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Machine machine = new Machine(rs.getString("MachineName"));
            machine.handleMachinePropertiesButton();
            machine.makeObjectDraggable();
            container_availableMachines.add(machine);

        }
        connection.close();
    }
}
