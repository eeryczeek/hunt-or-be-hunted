package environment.destinations;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Queue;
import java.util.List;
import java.util.Set;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Path extends EnvironmentStuff {

    public Path(String NAME, int X, int Y, int CAPACITY) {
        super(NAME, X, Y, CAPACITY);
    }

    public void display(GraphicsContext graphics){
        if (this.equals(World.selectedObject)){
            graphics.setFill(Color.web("efeff0"));
            graphics.fillRect(this.getX(), this.getY(), 1, 1);
        } else {
            graphics.setFill(Color.web("e5c687"));
            graphics.fillRect(this.getX(), this.getY(), 1, 1);
        }
    }
    
    public static ArrayList<Path> generatePaths() {
        ArrayList<Path> PATHS = new ArrayList<>();
        Set<int[]> points = new HashSet<int[]>();
        
        for (Cave cave: World.caves) {
            List<Character> destinations = new ArrayList<>(Arrays.asList( 'c', 'c', 'w', 'w', 'w', 'f', 'f', 'f'));
            int caveX = cave.getX();
            int caveY = cave.getY();

            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[] {caveX, caveY});

            boolean[][] visited = new boolean[World.MAP.length][World.MAP.length];
            visited[caveX][caveY] = true;

            TreeMap<int[], int[]> map_exploration = new TreeMap<>(Arrays::compare);

            while (!queue.isEmpty() && !Arrays.asList(destinations).isEmpty()) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];

                if (destinations.contains(World.MAP[x][y])) {
                    for (int i = 0; i < destinations.size(); i++) {
                        if (destinations.get(i) == World.MAP[x][y]) {
                            destinations.remove(i);
                            break;
                        }
                    }

                    int[] currentCoord = current;
                    while (currentCoord != null){
                        boolean contains = false;

                        for (int[] point: points){
                            if (currentCoord[0] == point[0] && currentCoord[1] == point[1]) {
                                contains = true;
                                break;
                            }
                        }
                        if (!contains){
                            points.add(currentCoord);
                            PATHS.add(new Path("path", currentCoord[0], currentCoord[1], 2));
                        }
                        currentCoord = map_exploration.get(currentCoord);
                    }
                }

                int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                for (int[] dir : dirs) {
                    int nextX = x + dir[0];
                    int nextY = y + dir[1];
                    if (nextX >= 0 && nextX < World.MAP.length && nextY >= 0 && nextY < World.MAP.length && !visited[nextX][nextY]) {
                        queue.add(new int[] {nextX, nextY});
                        visited[nextX][nextY] = true;
                        map_exploration.put(new int[] {nextX, nextY}, current);
                    }
                }
            }
        }
        return PATHS;
    }
}
