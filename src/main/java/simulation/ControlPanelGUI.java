package simulation;

import java.util.Random;

import environment.World;
import environment.animal.Animal;
import environment.animal.Predator;
import environment.animal.Prey;
import environment.destinations.Path;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlPanelGUI extends VBox{
    private static  ControlPanelGUI single_instance = null;

    private Stage controlStage;
    Button addPreyButton;
    Button addPredatorButton;
    Button killAnimalButton;
    Button rerouteButton;

    Random random;

    private ControlPanelGUI(int CONTROL_PANEL_WINDOW_SIZE){
        controlStage = new Stage();
        controlStage.setTitle("Control Panel");
        controlStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        addPreyButton = new Button("Add Prey");
        addPredatorButton = new Button("Add Predator");
        killAnimalButton = new Button("Kill an Animal");
        rerouteButton = new Button("Reroute");

        random = new Random();
        addPreyButton.setOnAction(e -> {
            Path random_path = World.paths.get(random.nextInt(World.paths.size()));
            new Prey("prey", random_path.getX(), random_path.getY());
        });

        addPredatorButton.setOnAction(e -> {
            new Predator("predator", random.nextInt(World.GRID_SIZE), random.nextInt(World.GRID_SIZE));
        });

        killAnimalButton.setOnAction(e -> {
            if (World.selectedObject != null && World.selectedObject instanceof Animal){
                Animal animal = (Animal) World.selectedObject;
                animal.setHealth(0);
            }
        });

        rerouteButton.setOnAction(e -> {
            if (World.selectedObject != null && World.selectedObject instanceof Animal) {
                Animal animal = (Animal) World.selectedObject;
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        animal.wander();
                        return null;
                    }
                };

                new Thread(task).start();
            }
        });

        rerouteButton.setOnAction(e -> {
            if (World.selectedObject != null && World.selectedObject instanceof Animal) {
                Animal animal = (Animal) World.selectedObject;
                animal.wander();
            }
        });
        
        this.getChildren().addAll(addPreyButton, addPredatorButton, killAnimalButton, rerouteButton);
        controlStage.setScene(new Scene(this, CONTROL_PANEL_WINDOW_SIZE, CONTROL_PANEL_WINDOW_SIZE));
        controlStage.getScene().getStylesheets().add(getClass().getResource("ControlPanelGUI.css").toExternalForm());
        controlStage.setOnCloseRequest(e -> System.exit(0));
        controlStage.setX(1080);
        controlStage.setY(320);
        controlStage.show();
    }

    public static ControlPanelGUI getInstance(int CONTROL_PANEL_WINDOW_SIZE){
        if (single_instance == null){
            single_instance = new ControlPanelGUI(CONTROL_PANEL_WINDOW_SIZE);
        }
        return single_instance;
    }
}
