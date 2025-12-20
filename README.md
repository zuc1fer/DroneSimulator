# Drone Delivery Simulator

A robust, console-based Java simulation engine modeling an autonomous delivery network. This project simulates fleet operations including order scheduling, pathfinding, battery management, and dynamic weather events.

## Overview

The simulator operates on a minute-by-minute tick system, managing a fleet of drones with varying specifications (Speed, Battery Efficiency, Payload). It handles creating orders, assigning the optimal drone based on heuristics (distance, battery, urgency), and generating detailed performance reports.

**Key Capabilities:**
*   **Heterogeneous Fleet**: Supports Standard, Express, and Heavy-lift drones.
*   **Dynamic Environment**: Randomly generates weather events (storms) that act as temporary no-fly zones.
*   **Smart Dispatching**: Assigns orders based on drone availability, battery life, and delivery urgency.
*   **Real-time Analytics**: Live console feed with ASCII-formatted hourly and final performance reports.

## Project Structure

The codebase is organized into a modular package structure:

*   `src/model`: Core data entities (`Drone`, `Order`, `Position`).
*   `src/map`: Geospatial components (`CityMap`, `DeliveryZone`, `WeatherZone`).
*   `src/service`: Business logic and orchestration (`ControlCenter`, `Simulator`).

## Getting Started

### Prerequisites
*   Java Development Kit (JDK) 8 or higher.

### Installation & Run

1.  **Navigate** to the source directory:
    ```bash
    cd src
    ```
2.  **Compile** the project (placing binaries in a `../bin` folder):
    ```bash
    javac -d ../bin model/*.java map/*.java service/*.java Main.java
    ```
3.  **Run** the simulation:
    ```bash
    java -cp ../bin Main
    ```

## Simulation Output

The simulation runs for a default of **480 minutes** (8 hours). You will see real-time logs for:
*   New Order generation.
*   Drone dispatches and returns.
*   Weather alerts.

**Example Report:**
```text
╔════════════════════════ HOURLY REPORT (02:00) ══════════════════════╗
║ Total Deliveries: 12   | Pending Orders: 5    | Active Drones: 3    ║
╠═════════════════════════════════════════════════════════════════════╣
║ Fleet Status:                                                       ║
║  - Alpha      (#01): AVAILABLE       [Bat:  85%]                    ║
║  - Beta       (#02): IN_DELIVERY     [Bat:  42%]                    ║
╚═════════════════════════════════════════════════════════════════════╝
```

## Configuration

You can tweak simulation parameters in `src/service/Simulator.java`:
*   `SIMULATION_DURATION`: Total runtime in minutes.
*   `Thread.sleep(300)`: Adjusts the visual playback speed (default: 300ms delay).

---
*Built with pure Java. No external dependencies.*