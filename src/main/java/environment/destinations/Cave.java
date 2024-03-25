package environment.destinations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import simulation.InfoPanelGUI;
import simulation.Point;

public class Cave extends EnvironmentStuff {

    private double reproduction_probability;

    public Cave(String NAME, int X, int Y, int CAPACITY) {
        super(NAME, X, Y, CAPACITY);
        Random random = new Random();
        this.reproduction_probability = random.nextDouble();
    }

    public void display(GraphicsContext graphics) {
        graphics.setFill(this.equals(World.selectedObject) ? Color.web("efeff0") : Color.web("6d4c3d"));
        graphics.fillRect(this.getX(), this.getY(), 1, 1);
    }

    @Override
    public void describe(InfoPanelGUI info_panel_window) {
        info_panel_window.getChildren().clear();
        info_panel_window.getChildren().add(new Label("Name: " + this.getName()));
        info_panel_window.getChildren().add(new Label("X: " + this.getX()));
        info_panel_window.getChildren().add(new Label("Y: " + this.getY()));
        info_panel_window.getChildren().add(new Label("Current animals: " + this.getNumber_of_animals()));
        info_panel_window.getChildren().add(new Label("Maximum animals: " + this.getCapacity()));
        DecimalFormat decor = new DecimalFormat("0.00");
        info_panel_window.getChildren()
                .add(new Label("Reproduction chance: " + decor.format(this.getReproduction_probability())));
    }

    public static ArrayList<Cave> generateCaves(int GRID_SIZE, Set<Point> points, int number, Random random_generator) {
        ArrayList<Cave> caves = new ArrayList<>();
        Point point;
        do {
            int x = random_generator.nextInt(GRID_SIZE - 1) / 2 * 2 + 1;
            int y = random_generator.nextInt(GRID_SIZE - 1) / 2 * 2 + 1;
            point = new Point(x, y);
            boolean contains = false;
            for (Point eee : points) {
                if (point.getX() == eee.getX() && point.getY() == eee.getY()) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                points.add(point);
                caves.add(new Cave("cave", x, y, 2));
            }
        } while (caves.size() < number);
        return caves;
    }

    public double getReproduction_probability() {
        return reproduction_probability;
    }

    public void setReproduction_probability(double reproduction_probability) {
        this.reproduction_probability = reproduction_probability;
    }
}
