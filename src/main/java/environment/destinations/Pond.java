package environment.destinations;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulation.Point;

public class Pond extends Replenishable {

    public Pond(String NAME, int X, int Y, int CAPACITY, int REPLENISH_SPEED) {
        super(NAME, X, Y, CAPACITY, REPLENISH_SPEED);
    }

    public void display(GraphicsContext graphics) {
        graphics.setFill(this.equals(World.selectedObject) ? Color.web("efeff0") : Color.web("3454d1"));
        graphics.fillRect(this.getX(), this.getY(), 1, 1);
    }

    public static ArrayList<Pond> generatePonds(int GRID_SIZE, Set<Point> points, int number, Random random_generator) {
        ArrayList<Pond> ponds = new ArrayList<>();
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
                ponds.add(new Pond("pond", x, y, 2, random_generator.nextInt(10)));
            }
        } while (ponds.size() < number);
        return ponds;
    }
}
