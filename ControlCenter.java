import java.util.ArrayList;
import java.util.List;

public class ControlCenter {

    private List<Drone> DronesList;
    private List<Order> pendingOrders;
    private Position base;
    private CityMap map;
    private List<Order> processedOrders;

    private static int totalDeliveries = 0;
    private static double totalDistance = 0;

    private static double standardDroneEnergy = 0;
    private static double expressDroneEnergy = 0;
    private static double heavyDroneEnergy = 0;

    public ControlCenter(List<Drone> DronesList, Position base, CityMap map) {
        this.DronesList = DronesList;
        this.base = base;
        this.map = map;
        this.pendingOrders = new ArrayList<>();
        this.processedOrders = new ArrayList<>();
    }

    public Drone findDroneForOrder(Order order) {
        Drone bestDrone = null;
        double bestScore = -1;

        Position dest = order.getDeliverable().getDestination();
        double weight = order.getDeliverable().getWeight();

        for (Drone drone : DronesList) {
            if (!drone.getStatus().equals("AVAILABLE"))
                continue;
            if (drone.getCapacity() < weight)
                continue;
            if (!map.isAllowed(dest))
                continue;
            if (!drone.canFlyTo(dest))
                continue;

            if (order.getUrgency().equals("EXPRESS") && !(drone instanceof ExpressDrone)) {
                continue;
            }

            double score = calculateDroneScore(drone, order, dest);

            if (score > bestScore) {
                bestScore = score;
                bestDrone = drone;
            }
        }

        return bestDrone;
    }

    private double calculateDroneScore(Drone drone, Order order, Position destination) {
        double score = 0;

        score += drone.getBattery() * 0.5;

        double distance = drone.getPosition().distanceTo(destination);
        double distanceScore = Math.max(0, 40 - (distance * 2));
        score += distanceScore;

        double capacityDiff = Math.abs(drone.getCapacity() - order.getDeliverable().getWeight());
        double capacityScore = Math.max(0, 20 - (capacityDiff * 10));
        score += capacityScore;

        if (order.getUrgency().equals("EXPRESS") && drone instanceof ExpressDrone) {
            score += 30;
        } else if (order.getDeliverable().getWeight() > 2.0 && drone instanceof HeavyDrone) {
            score += 25;
        } else if (order.getDeliverable().getWeight() <= 1.0 && drone instanceof StandardDrone) {
            score += 15;
        }

        if (drone.getBattery() > 80) {
            score += 10;
        }

        score += (drone.getEfficiency() - 1.0) * -20;

        return score;
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
        drone.incrementDeliveryCount();
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

    public static int getTotalDeliveries() {return totalDeliveries;}
    public static double getTotalDistance() {return totalDistance;}
    public static double getStandardDroneEnergy() {return standardDroneEnergy;}
    public static double getExpressDroneEnergy() {return expressDroneEnergy;}
    public static double getHeavyDroneEnergy() {return heavyDroneEnergy;}

    public Drone getMostActiveDroneByDistance() {

        if (DronesList.isEmpty())
            return null;

        Drone mostActive = null;
        double maxDistance = 0;

        for (Drone drone : DronesList) {
            if (drone.getTotalDistance() > maxDistance) {
                maxDistance = drone.getTotalDistance();
                mostActive = drone;
            }
        }
        return mostActive;
    }

    public Drone getMostActiveDroneByDeliveries() {

        if (DronesList.isEmpty())
            return null;

        Drone mostActive = null;
        int maxDeliveries = 0;

        for (Drone drone : DronesList) {
            if (drone.getDeliveryCount() > maxDeliveries) {
                maxDeliveries = drone.getDeliveryCount();
                mostActive = drone;
            }
        }
        return mostActive;
    }

    public List<Drone> getDrones() {return DronesList;}
    public List<Order> getPendingOrders() {return pendingOrders;}
    public Position getBase() {return base;}
    public CityMap getMap() {return map;}
}
