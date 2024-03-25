package simulation;

import java.util.Collection;
import java.util.stream.Stream;

import environment.World;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoPanelGUI extends VBox {
    private static InfoPanelGUI singleInstance = null;

    private InfoPanelGUI(int simulationWindowSize, int infoPanelWindowSize) {

        this.setPadding(new Insets(16));
        this.setSpacing(8);
        this.getChildren().addAll(new Label("Please select an object :)"));

        this.getStylesheets().add(getClass().getResource("InfoPanelGUI.css").toExternalForm());

        SimulationGUI.canvas.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                int x = (int) (e.getX() / (simulationWindowSize / World.GRID_SIZE));
                int y = (int) (e.getY() / (simulationWindowSize / World.GRID_SIZE));

                World.selectedObject = Stream.of(World.ponds, World.plants, World.preys, World.predators, World.caves)
                        .flatMap(Collection::stream)
                        .filter(object -> object.getX() == x && object.getY() == y)
                        .findFirst()
                        .orElse(null);
            });
        });
    }

    public void show() {
        if (World.selectedObject != null) {
            Platform.runLater(() -> {
                World.selectedObject.describe(this);
            });
        }
    }

    public static InfoPanelGUI getInstance(int windowSize, int simulationWindowSize) {
        if (singleInstance == null) {
            singleInstance = new InfoPanelGUI(windowSize, simulationWindowSize);
        }
        return singleInstance;
    }
}