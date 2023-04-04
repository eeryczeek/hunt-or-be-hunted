package environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import environment.animal.Predator;
import environment.animal.Prey;
import environment.destinations.Cave;
import environment.destinations.Path;
import environment.destinations.Plant;
import environment.destinations.Pond;
import simulation.Point;
import simulation.SimulationObject;

public class World {
    private static World single_instance = null;
    public static SimulationObject selectedObject = null;

    public static char[][] MAP;
    public static int GRID_SIZE;

    private static Set<Point> points;
    public static ArrayList<Cave> caves;
    public static ArrayList<Pond> ponds;
    public static ArrayList<Plant> plants;
    public static ArrayList<Path> paths;
    public static List<Prey> preys;
    public static List<Predator> predators;

    private World(int GRID_SIZE){
        World.GRID_SIZE = GRID_SIZE;
        Random random = new Random();

        predators = new CopyOnWriteArrayList<>();
        preys = new CopyOnWriteArrayList<>();
        caves = new ArrayList<>();
        ponds = new ArrayList<>();
        plants = new ArrayList<>();
        points = new HashSet<Point>();

        caves.addAll(Cave.generateCaves(GRID_SIZE, points, 15, random));
        ponds.addAll(Pond.generatePonds(GRID_SIZE, points, 15, random));
        plants.addAll(Plant.generatePlants(GRID_SIZE, points, 15, random));

        MAP = new char[GRID_SIZE][GRID_SIZE];
    
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                MAP[i][j] = '0';
            }
        }
        for (Cave cave: caves){
            MAP[cave.getX()][cave.getY()] = 'c';
        }
        for (Pond pond: ponds){
            MAP[pond.getX()][pond.getY()] = 'w';
        }
        for (Plant plant: plants){
            MAP[plant.getX()][plant.getY()] = 'f';
        }
        World.putPaths();
    }

    public static void putPaths() {
        paths = new ArrayList<>();
        paths.addAll(Path.generatePaths());
        for (Path path: paths){
            int x = path.getX();
            int y = path.getY();
            if (MAP[x][y] == 'w'){
                MAP[x][y] = 'W';
            }
            else if (MAP[x][y] == 'c'){
                MAP[x][y] = 'C';
            }
            else if (MAP[x][y] == 'f'){
                MAP[x][y] = 'F';
            }
            else{
                MAP[x][y] = 'p';
            }
        }
    }

    public static World getInstance(int GRID_SIZE){
        if (single_instance == null){
            single_instance = new World(GRID_SIZE);
        }
        return single_instance;
    }
}