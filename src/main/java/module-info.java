module at.salesianer.salesianer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens at.salesianer.salesianer to javafx.fxml;
    exports at.salesianer.salesianer;
}