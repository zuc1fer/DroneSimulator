import java.util.List;
import java.util.ArrayList;

public abstract class Drone{

    private static int nextId = 1;
    private int id;
    private Position position;
    private double battery;
    private String model;
    private double speed;
    private double capacity;
    private String status;
    private double totalDistance;
    private List<Position> positionHistory;

    public Drone(Position nposition , double nbattery , String nmodel , double nspeed , double ncapacity , String nstatus , double ntotalDistance , List<Position> npositionHistory){

        this.id = nextId++;
        this.position = nposition;
        this.battery = nbattery;
        this.model = nmodel;
        this.speed = nspeed;
        this.capacity = ncapacity;
        this.status = nstatus;
        this.totalDistance = ntotalDistance;
        this.positionHistory = npositionHistory;
        
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public double getBattery() { return battery; }
    public void setBattery(double battery) { this.battery = battery; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getCapacity() { return capacity; }
    public void setCapacity(double capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public List<Position> getPositionHistory() { return positionHistory; }
    public void setPositionHistory(List<Position> positionHistory) { this.positionHistory = positionHistory; }
     
}