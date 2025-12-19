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

}
