package environment.animal;

import java.util.Arrays;
import java.util.Random;

import environment.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import simulation.InfoPanelGUI;

public class Predator extends Animal implements Runnable{

    private boolean relaxing;
    private String color = "985f99";

    public Predator(String NAME, int X, int Y){
        super(NAME, X, Y, "predator", 100, randomInt(3, 10), randomInt(3, 10));
        this.relaxing = true;
        World.predators.add(this);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void display(GraphicsContext graphics){
        if (this.getHealth() <= 0) {
            World.predators.remove(this);
        } else {
            super.display(graphics, this.color);
        }
    }

    public void describe(InfoPanelGUI info_panel_window){
        super.describe(info_panel_window);
        if (World.selectedObject != null){
            info_panel_window.getChildren().add(new Label("Relaxing: " + this.isRelaxing()));
        }
    }

    protected static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public int[] random_direction(int X, int Y){
        Random random = new Random();
        while (true){
            int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            int[] random_dir = Arrays.asList(dirs).get(random.nextInt(4));

            int nextX = X + random_dir[0];
            int nextY = Y + random_dir[1];
            if (nextX >= 0 && nextX < World.MAP.length && nextY >= 0 && nextY < World.MAP.length) {
                if (World.MAP[nextX][nextY] != 'C' && World.MAP[nextX][nextY] != 'F' && World.MAP[nextX][nextY] != 'W'){
                    return random_dir;
                }
            }
        }
    }

    public void travel(int X, int Y){
        if (X > this.getX()){
            this.setX(getX() + 1);
            wait(1000 - getSpeed()*100);
        } else if (X < this.getX()){
            this.setX(getX() - 1);
            wait(1000 - getSpeed()*100);
        } else if (Y > this.getY()){
            this.setY(getY() + 1);
            wait(1000 - getSpeed()*100);
        } else if (Y < this.getY()){
            this.setY(getY() - 1);
            wait(1000 - getSpeed()*100);
        }
    }

    @Override
    public void wander(){
        int relaxing_iterations = randomInt(1, 5);
        for (int i = 0; i < relaxing_iterations; i++){
            int[] direction = random_direction(this.getX(), this.getY());
            this.setX(getX()+direction[0]);
            this.setY(getY()+direction[1]);
            wait(2000 - getSpeed());
        }
    }

    public Prey find_prey(){
        Prey target = null;
        int distance;
        int min_distance = Integer.MAX_VALUE;
        for (Prey prey: World.preys){
            if (!prey.isHiding()){
                distance = (prey.getX()-this.getX())*(prey.getX()-this.getX()) + (prey.getY()-this.getY())*(prey.getY()-this.getY());
                if (distance < min_distance){
                    target = prey;
                    min_distance = distance;
                }
            }
        }
        return target;
    }

    public void attack(Prey prey){
        if (!prey.isHiding()){
            int delta = Math.max(this.getStrength() - prey.getStrength(), 0);
            prey.setHealth(prey.getHealth() - delta*5);
        }
        wait(1000 - getSpeed()*100);
    }

    public void hunt(){
        Prey prey = find_prey();
        int boredness = 0;
        if (prey != null){
            while (prey.getHealth() > 0 && boredness < 10){
                while ((prey.getX()-this.getX())*(prey.getX()-this.getX()) + (prey.getY()-this.getY())*(prey.getY()-this.getY()) > 1){
                    if (!prey.isHiding()){
                        travel(prey.getX(), prey.getY());
                    } else {
                        boredness = 10;
                        break;
                    }
                }
                attack(prey);
                boredness++;
            }
        }
    }

    @Override
    public void run() {
        while(this.getHealth() > 0){
            if (relaxing){
                wander();
            } else {
                hunt();
            }
            relaxing = !relaxing;        
        }
    }

    public boolean isRelaxing() {
        return relaxing;
    }

    public void setRelaxing(boolean relaxing) {
        this.relaxing = relaxing;
    }
}