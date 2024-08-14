package simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.TimerTask;
import java.util.Timer;

import environment.World;

public class Simulation extends Application {
    private static final int GRID_SIZE = 61;
    private static final int SIMULATION_WINDOW_SIZE = 600;
    private static final int CONTROL_AND_INFO_PANEL_WINDOW_SIZE = SIMULATION_WINDOW_SIZE / 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        HBox simulation_and_others = new HBox();
        VBox controls_and_info = new VBox();

        World.getInstance(GRID_SIZE);
        SimulationGUI simulation_window = SimulationGUI.getInstance(SIMULATION_WINDOW_SIZE, GRID_SIZE);
        InfoPanelGUI info_panel_window = InfoPanelGUI.getInstance(
                SIMULATION_WINDOW_SIZE,
                CONTROL_AND_INFO_PANEL_WINDOW_SIZE);
        ControlPanelGUI control_panel_window = ControlPanelGUI.getInstance(CONTROL_AND_INFO_PANEL_WINDOW_SIZE);

        simulation_and_others.getChildren().addAll(simulation_window, controls_and_info);
        controls_and_info.getChildren().addAll(control_panel_window, info_panel_window);

        Scene scene = new Scene(root, SIMULATION_WINDOW_SIZE + CONTROL_AND_INFO_PANEL_WINDOW_SIZE,
                SIMULATION_WINDOW_SIZE);
        root.getChildren().add(simulation_and_others);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Color Divided Scene");
        primaryStage.show();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update(simulation_window, info_panel_window, control_panel_window);
            }
        }, 0, 50);
    }

    private void update(SimulationGUI simulation_window, InfoPanelGUI info_panel_window,
            ControlPanelGUI control_panel_window) {
        simulation_window.draw();
        info_panel_window.show();
    }
}