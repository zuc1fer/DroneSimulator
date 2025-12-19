public class ExpressDrone extends Drone{
    public ExpressDrone(Position position , double battery ){

        super(position , battery , "ExpressDrone" , 45 , 1);
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * 3.0;
    }
}
