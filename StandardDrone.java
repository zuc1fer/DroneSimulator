public class StandardDrone extends Drone{
    public StandardDrone(Position position , double battery ){

        super(position , battery , "StandardDrone" , 30 , 1);
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * 4.0;
    }
}
