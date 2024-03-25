package environment.destinations;

import javafx.scene.control.Label;
import simulation.InfoPanelGUI;

/**
 * Avstract Peplenishable class
 */
public abstract class Replenishable extends EnvironmentStuff {

    private int replenish_speed;

    public Replenishable(String NAME, int X, int Y, int CAPACITY, int REPLENISH_SPEED) {
        super(NAME, X, Y, CAPACITY);
        this.replenish_speed = REPLENISH_SPEED;
    }

    @Override
    public void describe(InfoPanelGUI info_panel_window) {
        info_panel_window.getChildren().clear();
        info_panel_window.getChildren().addAll(
                new Label("Name: " + this.getName()),
                new Label("X: " + this.getX()),
                new Label("Y: " + this.getY()),
                new Label("Current animals: " + this.getNumber_of_animals()),
                new Label("Maximum animals: " + this.getCapacity()),
                new Label("Replenish speed: " + this.getReplenish_speed()));
    }

    public int getReplenish_speed() {
        return replenish_speed;
    }

    public void setReplenish_speed(int replenish_speed) {
        this.replenish_speed = replenish_speed;
    }
}
