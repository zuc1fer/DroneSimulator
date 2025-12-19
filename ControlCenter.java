import java.util.ArrayList;
import java.util.List;

public class ControlCenter {
    
    private List<Drone> fleet;
    private List<Order> pendingOrders;
    private Position base;
    private Map map;
    private List<Order> processedOrders;
    
    private static int totalDeliveries = 0;
    private static double totalDistance = 0;
    
    private static double standardDroneEnergy = 0;
    private static double expressDroneEnergy = 0;
    private static double heavyDroneEnergy = 0;

    public ControlCenter(List<Drone> fleet, Position base, Map map) {
        this.fleet = fleet;
        this.base = base;
        this.map = map;
        this.pendingOrders = new ArrayList<>();
        this.processedOrders = new ArrayList<>();
    }

    public Drone findDroneForOrder(Order order) {
        Position dest = order.getDeliverable().getDestination();
        double weight = order.getDeliverable().getWeight();
        
        for (Drone drone : fleet) {
            boolean capacityOk = drone.getCapacity() >= weight;
            boolean allowed = map.isAllowed(dest);
            boolean batteryOk = drone.canFlyTo(dest);
            boolean isAvailable = drone.getStatus().equals("AVAILABLE");
            
            boolean expressOk = true;
            if (order.getUrgency().equals("EXPRESS")) {
                expressOk = drone instanceof ExpressDrone;
            }
            
            if (capacityOk && allowed && batteryOk && isAvailable && expressOk) {
                return drone;
            }
        }
        return null;
    }
}
