package simulation;

import environment.World;
import environment.animal.Predator;
import environment.animal.Prey;
import environment.destinations.Cave;
import environment.destinations.Path;
import environment.destinations.Plant;
import environment.destinations.Pond;
import javafx.application.Platform;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SimulationGUI extends VBox {
    private static SimulationGUI single_instance = null;

    private Stage simulationStage;
    public static Canvas canvas;
    private Affine affine;

    private SimulationGUI(int WINDOW_SIZE, int GRID_SIZE) {
        simulationStage = new Stage();
        simulationStage.setTitle("Simulation");
        simulationStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        affine = new Affine();
        affine.appendScale((double) WINDOW_SIZE / GRID_SIZE, (double) WINDOW_SIZE / GRID_SIZE);

        canvas = new Canvas(WINDOW_SIZE, WINDOW_SIZE);
        this.getChildren().addAll(canvas);

        Scene scene = new Scene(this, WINDOW_SIZE, WINDOW_SIZE);
        simulationStage.setScene(scene);
        simulationStage.setY(60);
    }

    public void draw() {
        Platform.runLater(() -> {
            GraphicsContext graphics = SimulationGUI.canvas.getGraphicsContext2D();
            graphics.setTransform(this.affine);

            graphics.setFill(Color.web("9fc490"));
            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (Path path : World.paths) {
                path.display(graphics);
            }
            for (Cave cave : World.caves) {
                cave.display(graphics);
            }
            for (Pond pond : World.ponds) {
                pond.display(graphics);
            }
            for (Plant plant : World.plants) {
                plant.display(graphics);
            }
            for (Predator predator : World.predators) {
                predator.display(graphics);
            }
            for (Prey prey : World.preys) {
                prey.display(graphics);
            }
        });
    }

    public static SimulationGUI getInstance(int WINDOW_SIZE, int GRID_SIZE) {
        if (single_instance == null) {
            single_instance = new SimulationGUI(WINDOW_SIZE, GRID_SIZE);
        }
        return single_instance;
    }

    public static SimulationGUI getSingle_instance() {
        return single_instance;
    }

    public static void setSingle_instance(SimulationGUI single_instance) {
        SimulationGUI.single_instance = single_instance;
    }

    public Stage getSimulationStage() {
        return simulationStage;
    }

    public void setSimulationStage(Stage simulationStage) {
        this.simulationStage = simulationStage;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        SimulationGUI.canvas = canvas;
    }

    public Affine getAffine() {
        return affine;
    }

    public void setAffine(Affine affine) {
        this.affine = affine;
    }
}
