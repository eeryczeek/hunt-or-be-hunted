package environment.animal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;
import environment.World;
import environment.destinations.Cave;
import environment.destinations.Plant;
import environment.destinations.Pond;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import simulation.InfoPanelGUI;

public class Prey extends Animal implements Runnable{

    private int hunger;
    private int thirst;
    private LinkedList<int[]> path = null;
    private boolean reroute = false;
    private boolean hiding = false;
    private String color = "ff6978";

    public Prey(String NAME, int X, int Y){
        super(NAME, X, Y, "prey", randomInt(70, 100), randomInt(0, 7), randomInt(0, 7));
        this.hunger = 0;
        this.thirst = 0;
        World.preys.add(this);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void display(GraphicsContext graphics){
        if (this.getHealth() <= 0) {
            World.preys.remove(this);
        } else {
            super.display(graphics, color);
        }
    }

    public void describe(InfoPanelGUI info_panel_window){
        super.describe(info_panel_window);
        if (World.selectedObject != null){
            info_panel_window.getChildren().add(new Label("Hunger: " + this.getHunger()));
            info_panel_window.getChildren().add(new Label("Thirst: " + this.getThirst()));
        }
    }

    protected static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public void travel(){
        while (path != null && !path.isEmpty()){
            int[] new_coordinates = path.pollLast();
            this.wait(1000 - this.getSpeed() * 100);
            this.setHunger(getHunger()+1);
            this.setThirst(getThirst()+1);
            this.setX(new_coordinates[0]);
            this.setY(new_coordinates[1]);
        }
    }

    public void handle_thirst(){
        path = generatePaths(this.getX(), this.getY(), 'W');
        travel();
        if (World.MAP[this.getX()][this.getY()] == 'W'){
            Pond this_pond = null;
            for (Pond pond: World.ponds){
                if (pond.getX() == this.getX() && pond.getY() == this.getY()){
                    this_pond = pond;
                    break;
                }
            }
            while (!this_pond.enter()){wait(100);}
            while (this.thirst > 20){
                drink(this_pond);
            }
            this_pond.leave();
        }
    }

    public void drink(Pond pond){
        this.setThirst(getThirst()-1);
        this.wait(1000 - pond.getReplenish_speed() * 100);
    }

    public void handle_hunger(){
        path = generatePaths(this.getX(), this.getY(), 'F');
        travel();
        if (World.MAP[this.getX()][this.getY()] == 'F'){
            Plant this_plant = null;
            for (Plant plant: World.plants){
                if (plant.getX() == this.getX() && plant.getY() == this.getY()){
                    this_plant = plant;
                    break;
                }
            }
            while (!this_plant.enter()){wait(100);}
            while (this.hunger > 20){
                eat(this_plant);
            }
            this_plant.leave();
        }
    }

    public void eat(Plant plant){
        this.setHunger(getHunger()-1);
        this.wait(1000 - plant.getReplenish_speed() * 100);
    }

    public void handle_loneliness(){
        path = generatePaths(this.getX(), this.getY(), 'C');
        travel();
        if (World.MAP[this.getX()][this.getY()] == 'C'){
            Cave this_cave = null;
            for (Cave cave: World.caves){
                if (cave.getX() == this.getX() && cave.getY() == this.getY()){
                    this_cave = cave;
                    break;
                }
            }
            while(!this_cave.enter()){wait(100);}
            hiding = true;
            rest();
            reproduce(this_cave);
            hiding = false;
            this_cave.leave();
        }
    }

    public void rest(){
        while (this.getHealth() < 100){
            this.setHunger(getHunger()+1);
            this.setThirst(getThirst()+1);
            this.setHealth(this.getHealth()+1);
            this.wait(100);
            if (this.getHunger() > 60 || this.getThirst() > 60){
                break;
            }
        }
        this.wait(1000);
    }

    public void reproduce(Cave cave){
        if (this.getHealth() == 100 && this.getHunger() < 70 && this.getThirst() < 70){
            Random random = new Random();
            double probability = random.nextDouble();
            if (probability < cave.getReproduction_probability()){
                new Prey("prey", this.getX(), this.getY());
            }
        }
    }

    public int[] random_direction(int X, int Y){
        Random random = new Random();
        while (true){
            int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            int[] random_dir = Arrays.asList(dirs).get(random.nextInt(4));

            int nextX = X + random_dir[0];
            int nextY = Y + random_dir[1];
            if (nextX >= 0 && nextX < World.MAP.length && nextY >= 0 && nextY < World.MAP.length) {
                if (World.MAP[nextX][nextY] == 'p' || World.MAP[nextX][nextY] == 'C' || World.MAP[nextX][nextY] == 'F' || World.MAP[nextX][nextY] == 'W'){
                    return random_dir;
                }
            }
        }
    }

    @Override
    public void wander(){
        int wandering_iterations = randomInt(5, 15);
        for (int i = 0; i < wandering_iterations; i++){
            if (this.thirst > 40 || this.hunger > 40){
                break;
            }
            int[] direction = random_direction(this.getX(), this.getY());
            this.setX(getX()+direction[0]);
            this.setY(getY()+direction[1]);
            this.setThirst(getThirst()+1);
            this.setHunger(getHunger()+1);
            wait(1000 - getSpeed() * 100);
        }
    }

    @Override
    public void run() {
        wander();
        while (this.getHealth() > 0){
            if (this.thirst >= 100 || this.hunger >= 100){
                if (this.thirst >= 100 && this.hunger >= 100){
                    this.setHealth(this.getHealth() - 4);
                }
                this.setHealth(this.getHealth() - 1);
            }
            if (this.thirst > 40){
                handle_thirst();
            }
            else if (this.hunger > 40){
                handle_hunger();
            }
            else if (!hiding){
                handle_loneliness();
                wander();
            }
            this.setThirst(getThirst()+1);
            this.setHunger(getHunger()+1);
        }
    }

    public static LinkedList<int[]> generatePaths(int X, int Y, char destination){
        LinkedList<int[]> path = new LinkedList<>();

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {X, Y});

        boolean[][] visited = new boolean[World.MAP.length][World.MAP.length];
        visited[X][Y] = true;

        TreeMap<int[], int[]> map_exploration = new TreeMap<>(Arrays::compare);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (World.MAP[x][y] == destination) {
                int[] currentCoord = current;
                while (currentCoord != null){
                    path.add(currentCoord);
                    currentCoord = map_exploration.get(currentCoord);
                }
                return path;
            }

            int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            for (int[] dir : dirs) {
                int nextX = x + dir[0];
                int nextY = y + dir[1];
                if (nextX >= 0 && nextX < World.MAP.length && nextY >= 0 && nextY < World.MAP.length && !visited[nextX][nextY]) {
                    if (World.MAP[nextX][nextY] == 'p' || World.MAP[nextX][nextY] == 'C' || World.MAP[nextX][nextY] == 'F' || World.MAP[nextX][nextY] == 'W'){
                        queue.add(new int[] {nextX, nextY});
                        visited[nextX][nextY] = true;
                        map_exploration.put(new int[] {nextX, nextY}, current);
                    }
                }
            }
        }
        return null;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public LinkedList<int[]> getPath() {
        return path;
    }

    public void setPath(LinkedList<int[]> path) {
        this.path = path;
    }

    public boolean isReroute() {
        return reroute;
    }

    public void setReroute(boolean reroute) {
        this.reroute = reroute;
    }

    public boolean isHiding() {
        return hiding;
    }

    public void setHiding(boolean hiding) {
        this.hiding = hiding;
    }
}
