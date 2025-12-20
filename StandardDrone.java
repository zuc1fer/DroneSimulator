public class StandardDrone extends Drone{
    public StandardDrone(Position position , double battery, String nickname ){

        super(position , battery , "StandardDrone" , 30 , 1 , nickname);
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
