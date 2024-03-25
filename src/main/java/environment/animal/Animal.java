package environment.animal;

import java.util.concurrent.TimeUnit;
import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import simulation.InfoPanelGUI;
import simulation.SimulationObject;

public abstract class Animal extends SimulationObject {

    private String speciesName;
    private int health;
    private int strength;
    private int speed;

    public Animal(String name, int x, int y, String speciesName, int health, int strength, int speed) {
        super(name, x, y);
        this.speciesName = speciesName;
        this.health = health;
        this.strength = strength;
        this.speed = speed;
    }

    public void pause(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void display(GraphicsContext graphics, String color) {
        graphics.setFill(this.equals(World.selectedObject) ? Color.web("efeff0") : Color.web(color));
        graphics.fillOval(this.getX(), this.getY(), 1, 1);
    }

    public void describe(InfoPanelGUI infoPanelWindow) {
        infoPanelWindow.getChildren().clear();

        if (this.getHealth() <= 0) {
            infoPanelWindow.getChildren().addAll(
                    new Label("Your animal died of ligma"),
                    new Label("Please select another object:)"));
            World.selectedObject = null;
        } else {
            infoPanelWindow.getChildren().addAll(
                    new Label("Name: " + this.getName()),
                    new Label("X: " + this.getX()),
                    new Label("Y: " + this.getY()),
                    new Label("Health: " + this.getHealth()),
                    new Label("Speed: " + this.getSpeed()),
                    new Label("Strength: " + this.getStrength()));
        }
    }

    public void wander() {
        // Implementation goes here
    }

    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}