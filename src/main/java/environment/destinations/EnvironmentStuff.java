package environment.destinations;

import simulation.SimulationObject;

public abstract class EnvironmentStuff extends SimulationObject{

    private int capacity;
    private int number_of_animals;
    
    public EnvironmentStuff(String NAME, int X, int Y, int CAPACITY){
        super(NAME, X, Y);
        this.capacity = CAPACITY;
    }

    public synchronized boolean enter(){
        if (number_of_animals >= capacity){
            return false;
        }
        number_of_animals++;
        return true;
    }

    public synchronized void leave(){
        if (number_of_animals > 0){
            number_of_animals--;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumber_of_animals() {
        return number_of_animals;
    }

    public void setNumber_of_animals(int number_of_animals) {
        this.number_of_animals = number_of_animals;
    }
}
