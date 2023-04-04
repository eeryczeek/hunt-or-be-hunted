package environment.animal;

import java.util.concurrent.TimeUnit;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import simulation.InfoPanelGUI;
import simulation.SimulationObject;

public abstract class Animal extends SimulationObject {

    private String species_name;
    private int health;
    private int strength;
    private int speed;

    public Animal(String NAME, int X, int Y, String SPECIES_NAME, int HEALTH, int STRENGTH, int SPEED){
        super(NAME, X, Y);
        this.species_name = SPECIES_NAME;
        this.health = HEALTH;
        this.strength = STRENGTH;
        this.speed = SPEED;
    }

    public void wait(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void display(GraphicsContext graphics, String color){
        if (this.equals(World.selectedObject)){
            graphics.setFill(Color.web("efeff0"));
            graphics.fillOval(this.getX(), this.getY(), 1, 1);
        } else {
            graphics.setFill(Color.web(color));
            graphics.fillOval(this.getX(), this.getY(), 1, 1);
        }
    }

    public void describe(InfoPanelGUI info_panel_window){
        if (this.getHealth() <= 0) {
            info_panel_window.getChildren().clear();
            info_panel_window.getChildren().addAll(new Label("your animal died of ligma"));
            info_panel_window.getChildren().addAll(new Label("please select another object:)"));
            World.selectedObject = null;
        } else {
            info_panel_window.getChildren().clear();
            info_panel_window.getChildren().add(new Label("Name: " + this.getName()));
            info_panel_window.getChildren().add(new Label("X: " + this.getX()));
            info_panel_window.getChildren().add(new Label("Y: " + this.getY()));
            info_panel_window.getChildren().add(new Label("Health: " + this.getHealth()));
            info_panel_window.getChildren().add(new Label("Speed: " + this.getSpeed()));
            info_panel_window.getChildren().add(new Label("Strength: " + this.getStrength()));
        }
    }

    public void wander(){
        
    }

    public String getSpecies_name() {
        return species_name;
    }

    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
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
