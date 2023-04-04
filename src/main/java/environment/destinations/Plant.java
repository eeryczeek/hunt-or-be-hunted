package environment.destinations;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulation.Point;

public class Plant extends Replenishable{
    
    public Plant(String NAME, int X, int Y, int CAPACITY, int REPLENISH_SPEED){
        super(NAME, X, Y, CAPACITY, REPLENISH_SPEED);
    }

    public void display(GraphicsContext graphics){
        if (this.equals(World.selectedObject)){
            graphics.setFill(Color.web("efeff0"));
            graphics.fillRect(this.getX(), this.getY(), 1, 1);
        } else {
            graphics.setFill(Color.web("1b998b"));
            graphics.fillRect(this.getX(), this.getY(), 1, 1);
        }
    }

    public static ArrayList<Plant> generatePlants(int GRID_SIZE, Set<Point> points, int number, Random random_generator){
        ArrayList<Plant> plants = new ArrayList<>();
        Point point;
        do {
            int x = random_generator.nextInt(GRID_SIZE-1)/2*2+1;
            int y = random_generator.nextInt(GRID_SIZE-1)/2*2+1;
            point = new Point(x, y);
            boolean contains = false;
            for (Point eee: points){
                if (point.getX() == eee.getX() && point.getY() == eee.getY()){
                    contains = true;
                    break;
                }
            }
            if (! contains){
                points.add(point);
                plants.add(new Plant("plant", x, y, 2, random_generator.nextInt(10)));
            }
        } while (plants.size() < number);
        return plants;
    }
}
