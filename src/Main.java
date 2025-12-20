import model.*;
import map.*;
import service.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<DeliveryZone> deliveryZones = new ArrayList<>();
        deliveryZones.add(new DeliveryZone(new Position(10, 10), 5.0));
        deliveryZones.add(new DeliveryZone(new Position(-20, 10), 8.0));

        List<NoFlyZone> noFlyZones = new ArrayList<>();
        noFlyZones.add(new NoFlyZone(new Position(5, 5), 2.0));

        TheMap map = new TheMap(deliveryZones, noFlyZones);
        ControlCenter controlCenter = new ControlCenter(map);

        Position base = new Position(0, 0);
        controlCenter.addDrone(DroneFactory.createDrone("STANDARD", base, "Alpha"));
        controlCenter.addDrone(DroneFactory.createDrone("EXPRESS", base, "Beta"));
        controlCenter.addDrone(DroneFactory.createDrone("HEAVY", base, "Gamma"));
        controlCenter.addDrone(DroneFactory.createDrone("STANDARD", base, "Delta"));
        controlCenter.addDrone(DroneFactory.createDrone("EXPRESS", base, "Epsilon"));
        controlCenter.addDrone(DroneFactory.createDrone("HEAVY", base, "Zeta"));

        Simulator simulator = new Simulator(controlCenter);
        simulator.runSimulation();
    }
}