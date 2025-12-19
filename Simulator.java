import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Simulator {
    
    private ControlCenter controlCenter;
    private List<Order> allOrders;
    private int currentMinute;
    private static final int SIMULATION_DURATION = 480;
    private Random random = new Random();

    private Map<Drone, Integer> deliveryCounts = new HashMap<>();
    private Map<Drone, Double> distanceTraveled = new HashMap<>();
    private Map<String, Integer> orderStatusCounts = new HashMap<>();
    private Map<Drone, Order> deliveryAssignments = new HashMap<>();

}
