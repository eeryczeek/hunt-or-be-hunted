module OOP {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports environment;
    exports environment.animal;
    exports environment.destinations;
    exports simulation;
}