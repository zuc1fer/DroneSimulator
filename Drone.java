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
    private int deliveryCount = 0;
    private List<Position> positionHistory;
    private double efficiency;
    private double reliability;
    private double speedFactor;
    private String nickname;

    public Drone(Position nposition , double nbattery , String nmodel , double nspeed , double ncapacity, String nickname){

        this.id = nextId++;
        this.nickname = nickname;
        this.position = nposition;
        this.battery = nbattery;
        this.model = nmodel;
        this.speed = nspeed;
        this.capacity = ncapacity;
        this.status = "AVAILABLE";
        this.totalDistance = 0;
        this.positionHistory = new ArrayList<>();
        this.positionHistory.add(nposition);
        this.efficiency = 0.9 + (Math.random() * 0.3);
        this.reliability = 0.9 + (Math.random() * 0.1);
        this.speedFactor = 0.95 + (Math.random() * 0.15);
    }

    public Drone(Position nposition, double nbattery, String nmodel, double nspeed, double ncapacity) {
        this(nposition, nbattery, nmodel, nspeed, ncapacity, nmodel);
    }

    public int getId() {return id;}

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public double getBattery() { return battery; }
    public void setBattery(double battery) { this.battery = battery; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getSpeed() {
        double baseSpeed = 0;
        if (this instanceof StandardDrone) baseSpeed = 30.0;
        else if (this instanceof ExpressDrone) baseSpeed = 45.0;
        else if (this instanceof HeavyDrone) baseSpeed = 20.0;

        return baseSpeed * speedFactor;
    }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getCapacity() { return capacity; }
    public void setCapacity(double capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public int getDeliveryCount() {return deliveryCount;}
    public void incrementDeliveryCount() {deliveryCount++;}

    public List<Position> getPositionHistory() { return positionHistory; }
    public void setPositionHistory(List<Position> positionHistory) { this.positionHistory = positionHistory; }

    public double getEfficiency() { return efficiency; }
    public double getReliability() { return reliability; }
    public double getSpeedFactor() { return speedFactor; }
    public String getNickname() { return nickname; }

    public abstract double calculateConsumption(double distance);

    protected abstract double getBaseConsumption();

    public boolean canFlyTo(Position destination) {
        double TripDistance = this.position.distanceTo(destination) * 2;
        return calculateConsumption(TripDistance) <= battery;
    }

    public void flyTo(Position destination) {
        double distance = this.position.distanceTo(destination);
        double consumption = calculateConsumption(distance);
        this.battery -= consumption;
        this.totalDistance += distance;
        this.position = destination;
        this.positionHistory.add(destination);
    }

    public void recharge(double percentage) {
        this.battery = Math.min(100, this.battery + percentage);
    }

    public boolean attemptDelivery() {
        return Math.random() < reliability;
    }

    public String toString() {
        return String.format("Drone[ID=%d, Model=%s, Position=%s, Battery=%.1f%%, Status=%s]",
                id, model, position, battery, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Drone drone = (Drone) obj;
        return id == drone.id;
    }
}