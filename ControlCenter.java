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

    public boolean assignOrder(Order order) {
        Drone drone = findDroneForOrder(order);
        
        if (drone != null) {
            double deliveryCost = calculateDeliveryCost(order, drone);
            order.setCost(deliveryCost);
            
            drone.setStatus("IN_DELIVERY");
            
            order.setStatus("IN_PROGRESS");
            
            if (pendingOrders.contains(order)) {
                pendingOrders.remove(order);
            }
            processedOrders.add(order);
            
            totalDeliveries++;
            
            double distance = drone.getPosition().distanceTo(order.getDeliverable().getDestination()) * 2;
            double consumption = drone.calculateConsumption(distance);
            
            if (drone instanceof StandardDrone) {
                standardDroneEnergy += consumption;
            } else if (drone instanceof ExpressDrone) {
                expressDroneEnergy += consumption;
            } else if (drone instanceof HeavyDrone) {
                heavyDroneEnergy += consumption;
            }
            
            return true;
        } else {
            if (!pendingOrders.contains(order)) {
                pendingOrders.add(order);
            }
            return false;
        }
    }

    public double calculateDeliveryCost(Order order, Drone drone) {
        Position dest = order.getDeliverable().getDestination();
        double distance = drone.getPosition().distanceTo(dest) * 2;
        double consumption = drone.calculateConsumption(distance);
        double operationCost = (distance * 0.1) + (consumption * 0.02) + 20;
        double initialPrice = order.getCost();
        double insurance = Math.max(initialPrice * 0.02, 10);
        if (order.getUrgency().equals("EXPRESS")) {
            insurance += 20;
        }
    
        return operationCost + insurance;
    }

    public void completeDelivery(Order order, Drone drone) {
        order.setStatus("DELIVERED");
        drone.setStatus("RETURN_TO_BASE");
        drone.flyTo(base);
        drone.setStatus("AVAILABLE");
        totalDistance += drone.getTotalDistance();
    }

    public void failDelivery(Order order, Drone drone) {
        order.setStatus("FAILED");
        drone.setStatus("RETURN_TO_BASE");
        
        if (drone.canFlyTo(base)) {
            drone.flyTo(base);
            drone.setStatus("AVAILABLE");
        } else {
            drone.setStatus("STRANDED");
        }
        
        pendingOrders.add(order);
    }
}
