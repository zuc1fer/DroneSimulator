import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<DeliveryZone> deliveryZones = new ArrayList<>();
        deliveryZones.add(new DeliveryZone(new Position(10, 10), 5.0, 10));
        deliveryZones.add(new DeliveryZone(new Position(-20, 10), 8.0, 5));

        List<NoFlyZone> noFlyZones = new ArrayList<>();
        noFlyZones.add(new NoFlyZone(new Position(5, 5), 2.0));

        CityMap map = new CityMap(deliveryZones, noFlyZones);
        ControlCenter controlCenter = new ControlCenter(map);

        controlCenter.addDrone(new StandardDrone(1, new Position(0, 0), "Alpha"));
        controlCenter.addDrone(new ExpressDrone(2, new Position(0, 0), "Beta"));
        controlCenter.addDrone(new HeavyDrone(3, new Position(0, 0), "Gamma"));
        controlCenter.addDrone(new StandardDrone(4, new Position(0, 0), "Delta"));
        controlCenter.addDrone(new ExpressDrone(5, new Position(0, 0), "Epsilon"));

        Simulator simulator = new Simulator(controlCenter);
        simulator.runSimulation();
    }
}
