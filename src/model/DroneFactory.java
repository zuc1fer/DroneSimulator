package model;

public class DroneFactory {

    public static Drone createDrone(String type, Position position, String nickname) {
        if (type == null) {
            return null;
        }

        switch (type.toUpperCase()) {
            case "STANDARD":
                return new StandardDrone(position, 100.0, nickname);
            case "EXPRESS":
                return new ExpressDrone(position, 100.0, nickname);
            case "HEAVY":
                return new HeavyDrone(position, 100.0, nickname);
            default:
                throw new IllegalArgumentException("Unknown drone type: " + type);
        }
    }
}
