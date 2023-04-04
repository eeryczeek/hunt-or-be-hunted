package simulation;

import environment.World;
import environment.destinations.Plant;
import environment.destinations.Cave;
import environment.destinations.Pond;
import environment.animal.Predator;
import environment.animal.Prey;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InfoPanelGUI extends VBox {
    private static InfoPanelGUI single_instance = null;

    private Stage infoStage;

    private InfoPanelGUI(int INFO_PANEL_WINDOW_SIZE, int SIMULATION_WINDOW_SIZE) {
        infoStage = new Stage();
        infoStage.setTitle("Info Panel");
        infoStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        this.setPadding(new Insets(10));
        this.setSpacing(4);
        this.getChildren().addAll(new Label("Please select an object :)"));

        infoStage.setScene(new Scene(this, INFO_PANEL_WINDOW_SIZE, INFO_PANEL_WINDOW_SIZE));
        infoStage.getScene().getStylesheets().add(getClass().getResource("InfoPanelGUI.css").toExternalForm());
        infoStage.setOnCloseRequest(e -> System.exit(0));
        infoStage.setX(1080);
        infoStage.setY(60);

        SimulationGUI.canvas.setOnMouseClicked(e -> {
            Platform.runLater(()->{
                int x = (int) (e.getX() / (SIMULATION_WINDOW_SIZE / World.GRID_SIZE));
                int y = (int) (e.getY() / (SIMULATION_WINDOW_SIZE / World.GRID_SIZE));

                for (Pond pond : World.ponds) {
                    if (pond.getX() == x && pond.getY() == y) {
                        World.selectedObject = pond;
                        break;
                    }
                }
                for (Plant plant : World.plants) {
                    if (plant.getX() == x && plant.getY() == y) {
                        World.selectedObject = plant;
                        break;
                    }
                }
                for (Prey prey : World.preys) {
                    if (prey.getX() == x && prey.getY() == y) {
                        World.selectedObject = prey;
                        break;
                    }
                }
                for (Predator predator : World.predators) {
                    if (predator.getX() == x && predator.getY() == y) {
                        World.selectedObject = predator;
                        break;
                    }
                }
                for (Cave cave : World.caves) {
                    if (cave.getX() == x && cave.getY() == y) {
                        World.selectedObject = cave;
                        break;
                    }
                }
            });
        });
        infoStage.show();
    }

    public void show(){
        if (World.selectedObject != null) {
            Platform.runLater(() -> {
                World.selectedObject.describe(this);
            });
        }
    }

    public static InfoPanelGUI getInstance(int WINDOW_SIZE, int SIMULATION_WINDOW_SIZE){
        if (single_instance == null){
            single_instance = new InfoPanelGUI(WINDOW_SIZE, SIMULATION_WINDOW_SIZE);
        }
        return single_instance;
    }
}