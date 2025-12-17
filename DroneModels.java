public class StandardDrone extends Drone{
    public StandardDrone(Position position , double battery ){

        super(position , battery , "StandardDrone" , 30 , 1);
    }
}

public class ExpressDrone extends Drone{
    public ExpressDrone(Position position , double battery ){

        super(position , battery , "ExpressDrone" , 45 , 1);
    }
}

public class HeavyDrone extends Drone{
    public HeavyDrone(Position position , double battery ){

        super(position , battery , "HravyDrone" , 20 , 3);
    }
}
