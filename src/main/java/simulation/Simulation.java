package simulation;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.TimerTask;
import java.util.Timer;

import environment.World;

public class Simulation extends Application{
    private int GRID_SIZE = 61;
    private int SIMULATION_WINDOW_SIZE = 615;
    private int CONTROL_PANEL_WINDOW_SIZE = 220;
    private int INFO_PANEL_WINDOW_SIZE = 220;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        World.getInstance(GRID_SIZE);
        SimulationGUI simulation_window = SimulationGUI.getInstance(SIMULATION_WINDOW_SIZE, GRID_SIZE);
        InfoPanelGUI info_panel_window = InfoPanelGUI.getInstance(INFO_PANEL_WINDOW_SIZE, SIMULATION_WINDOW_SIZE);
        ControlPanelGUI control_panel_window = ControlPanelGUI.getInstance(CONTROL_PANEL_WINDOW_SIZE);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update(simulation_window, info_panel_window, control_panel_window);
            }
        }, 0, 50);
    }

    private void update(SimulationGUI simulation_window, InfoPanelGUI info_panel_window, ControlPanelGUI control_panel_window) {
        simulation_window.draw();
        info_panel_window.show();
    }
}