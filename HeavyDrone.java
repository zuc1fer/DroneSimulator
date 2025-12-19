public class HeavyDrone extends Drone{
    public HeavyDrone(Position position , double battery, String nickname ){

        super(position , battery , "HeavyDrone" , 20 , 3, nickname);
    }

    @Override
    protected double getBaseConsumption() {
        return 5.0;
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * getBaseConsumption() * getEfficiency();
    }

    @Override
    public double getSpeed() {
        if (getBattery() < 20) {
            return 16.0;
        }
        return super.getSpeed();
    }
}
