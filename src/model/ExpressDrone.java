package model;

public class ExpressDrone extends Drone {
    public ExpressDrone(Position position, double battery, String nickname) {

        super(position, battery, "ExpressDrone", 45, 1, nickname);
    }

    @Override
    protected double getBaseConsumption() {
        return 3.0;
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * getBaseConsumption() * getEfficiency();
    }
}
