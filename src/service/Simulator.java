package service;

import model.*;
import map.*;
import java.util.ArrayList;
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

    public Simulator(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
        this.allOrders = new ArrayList<>();
        this.currentMinute = 0;

        for (Drone drone : controlCenter.getDrones()) {
            deliveryCounts.put(drone, 0);
            distanceTraveled.put(drone, 0.0);
        }

        orderStatusCounts.put("PENDING", 0);
        orderStatusCounts.put("IN_PROGRESS", 0);
        orderStatusCounts.put("DELIVERED", 0);
        orderStatusCounts.put("FAILED", 0);
    }

    public void runSimulation() {
        printSimulationHeader();

        for (currentMinute = 0; currentMinute < SIMULATION_DURATION; currentMinute++) {
            simulateMinute(currentMinute);

            if (currentMinute % 60 == 0 && currentMinute > 0) {
                logHourlyReport(currentMinute / 60);
            }

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation interrupted");
                break;
            }
        }
        generateFinalReport();
    }

    private void printSimulationHeader() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                DRONE DELIVERY SIMULATOR 2025               ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println(
                "║ Simulation Duration: " + SIMULATION_DURATION + " minutes                           ║");
        System.out.println("║ Squadron Size: " + controlCenter.getDrones().size()
                + " Drones                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n[System] Starting simulation...\n");
    }

    private void logHourlyReport(int hour) {
        System.out.println("\n╔════════════════════════ HOURLY REPORT (" + String.format("%02d:00", hour)
                + ") ══════════════════════╗");
        System.out.printf("║ Total Deliveries: %-4d | Pending Orders: %-4d | Active Drones: %-4d ║\n",
                ControlCenter.getTotalDeliveries(), controlCenter.getPendingOrders().size(),
                controlCenter.getDrones().stream().filter(d -> !d.getStatus().equals("AVAILABLE")).count());
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Squadron Status:                                                    ║");
        for (Drone d : controlCenter.getDrones()) {
            System.out.printf("║  - %-10s (#%02d): %-15s [Bat: %3.0f%%]                    ║\n",
                    d.getNickname(), d.getId(), d.getStatus(), d.getBattery());
        }
        System.out.println("╚═════════════════════════════════════════════════════════════════════╝\n");
    }

    private void generateFinalReport() {
        System.out.println("\n\n");
        System.out.println("╔══════════════════════ FINAL SIMULATION REPORT ═══════════════════════╗");
        System.out.printf("║ Total Duration: %4d mins                                            ║\n",
                SIMULATION_DURATION);
        System.out.printf("║ Total Deliveries Completed: %-5d                                    ║\n",
                ControlCenter.getTotalDeliveries());

        double totalDist = distanceTraveled.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("║ Total Distance Flown: %-8.2f km                                    ║\n", totalDist);

        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Squadron Performance:                                                ║");
        for (Drone d : controlCenter.getDrones()) {
            System.out.printf("║  %-10s (#%02d): %3d deliveries, %6.2f km flown                   ║\n",
                    d.getNickname(), d.getId(), deliveryCounts.get(d), distanceTraveled.get(d));
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
    }

    private void simulateMinute(int minute) {
        generateOrders(minute);
        processPendingOrders();
        updateDrones(minute);
        updateWeatherConditions(minute);
    }

    private void completeDelivery(Drone drone, int minute) {
        Order order = deliveryAssignments.get(drone);
        if (order != null) {
            if (drone.attemptDelivery()) {
                order.setStatus("DELIVERED");
                orderStatusCounts.put("IN_PROGRESS", orderStatusCounts.get("IN_PROGRESS") - 1);
                orderStatusCounts.put("DELIVERED", orderStatusCounts.get("DELIVERED") + 1);

                deliveryCounts.put(drone, deliveryCounts.get(drone) + 1);

                double distance = 5.0 + random.nextDouble() * 15;
                distanceTraveled.put(drone, distanceTraveled.get(drone) + distance);

                double consumption = drone.calculateConsumption(distance);
                drone.setBattery(Math.max(0, drone.getBattery() - consumption));

                System.out.printf("MIN %04d: Drone #%02d delivered Order #%03d\n",
                        minute, drone.getId(), order.getId());

                drone.setStatus("RETURN_TO_BASE");
                deliveryAssignments.remove(drone);

            } else {
                order.setStatus("FAILED");
                orderStatusCounts.put("IN_PROGRESS", orderStatusCounts.get("IN_PROGRESS") - 1);
                orderStatusCounts.put("FAILED", orderStatusCounts.get("FAILED") + 1);

                System.out.printf("MIN %04d: Drone #%02d failed Order #%03d (reliability issue)\n",
                        minute, drone.getId(), order.getId());

                drone.setStatus("AVAILABLE");
                deliveryAssignments.remove(drone);
            }
        }
    }

    private void generateOrders(int minute) {
        int hour = minute / 60;
        double orderRate = getOrderRateForHour(hour);

        if (random.nextDouble() < orderRate) {
            Order order = createOrder(minute);
            allOrders.add(order);
            controlCenter.getPendingOrders().add(order);
            orderStatusCounts.put("PENDING", orderStatusCounts.get("PENDING") + 1);

            if (random.nextDouble() < 0.1) {
                System.out.printf("MIN %04d: New Order #%03d to %s\n",
                        minute, order.getId(), formatPosition(order.getDeliverable().getDestination()));
            }
        }
    }

    private void processPendingOrders() {
        List<Order> pending = new ArrayList<>(controlCenter.getPendingOrders());

        for (Order order : pending) {
            if (order.getStatus().equals("PENDING")) {
                Drone drone = controlCenter.findDroneForOrder(order);

                if (drone != null) {
                    controlCenter.assignOrder(order);
                    deliveryAssignments.put(drone, order);
                    orderStatusCounts.put("PENDING", orderStatusCounts.get("PENDING") - 1);
                    orderStatusCounts.put("IN_PROGRESS", orderStatusCounts.get("IN_PROGRESS") + 1);

                    System.out.printf("MIN %04d: Order #%03d → Drone #%02d (%s)\n",
                            currentMinute, order.getId(), drone.getId(), drone.getClass().getSimpleName());
                }
            }
        }
    }

    private void updateDrones(int minute) {
        for (Drone drone : controlCenter.getDrones()) {
            String status = drone.getStatus();

            if (status.equals("IN_DELIVERY")) {
                if (random.nextDouble() < 0.1) {
                    completeDelivery(drone, minute);
                } else {
                    double consumption = 0.5 + random.nextDouble() * 1.0;
                    drone.setBattery(Math.max(0, drone.getBattery() - consumption));
                }
            } else if (status.equals("RETURN_TO_BASE")) {
                if (random.nextDouble() < 0.2) {
                    drone.setStatus("AVAILABLE");
                    System.out.printf("MIN %04d: Drone #%02d returned to base (Battery: %.0f%%)\n",
                            minute, drone.getId(), drone.getBattery());
                }
            } else if (status.equals("AVAILABLE") && drone.getBattery() < 100) {
                double recharge = 1.0 + random.nextDouble() * 2.0;
                drone.setBattery(Math.min(100, drone.getBattery() + recharge));
            }
        }
    }

    private void updateWeatherConditions(int minute) {
        if (random.nextDouble() < 0.05) {
            generateWeatherEvent(minute);
        }
    }

    private void generateWeatherEvent(int minute) {
        Position base = controlCenter.getBase();
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * 10.0;
        double r = base.getX() + Math.cos(angle) * distance;
        double s = base.getY() + Math.sin(angle) * distance;
        Position center = new Position(r, s);

        double radius = 2.0 + random.nextDouble() * 5.0;

        controlCenter.getMap().updateWeather(center, radius);
        System.out.printf("MIN %04d: Weather Alert! Storm near %s (Radius: %.1fkm)\n",
                minute, formatPosition(center), radius);
    }

    private double getOrderRateForHour(int hour) {
        if (hour >= 11 && hour < 13)
            return 0.7;
        if (hour >= 17 && hour < 20)
            return 0.8;
        if (hour >= 8 && hour < 19)
            return 0.4;
        return 0.1;
    }

    private Order createOrder(int minute) {
        String[] clients = { "Client_A", "Client_B", "Client_C", "Client_D" };
        Position base = controlCenter.getBase();
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = 3 + random.nextDouble() * 12;
        double x = base.getX() + Math.cos(angle) * distance;
        double y = base.getY() + Math.sin(angle) * distance;
        Position destination = new Position(x, y);

        double weight;
        double rand = random.nextDouble();
        if (rand < 0.7)
            weight = 0.1 + random.nextDouble() * 0.8;
        else if (rand < 0.95)
            weight = 1.0 + random.nextDouble() * 1.0;
        else
            weight = 2.0 + random.nextDouble() * 0.5;

        String urgency = (random.nextDouble() < 0.25) ? "EXPRESS" : "NORMAL";
        double basePrice = 100 + weight * 50;

        StandardPackage pkg = new StandardPackage(weight, destination);
        return new Order(clients[random.nextInt(clients.length)], pkg, urgency, basePrice);
    }

    private String formatPosition(Position pos) {
        return String.format("[%.1f, %.1f]", pos.getX(), pos.getY());
    }
}