package simulation;

public abstract class SimulationObject {

    private String name;
    private int X;
    private int Y;

    public SimulationObject(String NAME, int X, int Y){
        this.name = NAME;
        this.X = X;
        this.Y = Y;
    }

    public void describe(InfoPanelGUI info_panel_window){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return this.X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return this.Y;
    }

    public void setY(int y) {
        this.Y = y;
    }
}