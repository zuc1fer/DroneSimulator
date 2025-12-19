public class HeavyDrone extends Drone{
    public HeavyDrone(Position position , double battery ){

        super(position , battery , "HeavyDrone" , 20 , 3);
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * 5.0;
    }

    @Override
    public double getSpeed() {
        if (getBattery() < 20) {
            return 16.0;
        }
        return super.getSpeed();
    }
}
