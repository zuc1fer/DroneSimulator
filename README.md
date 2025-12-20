# Drone Delivery Simulator

A Java-based simulation for managing a drone delivery fleet. It runs a tick-based system (minute by minute) to handle orders, dispatch drones, tracking battery usage and distance logic.

## How it Works

The sim runs for a set duration (default 480 mins). Every minute, it checks for:
- **New Orders**: Randomly generated based on time-of-day traffic.
- **Drone Logic**: Assigning the best available drone (Heavy, Express, or Standard) to an order.
- **Environment**: Random storms that create temporary no-fly zones.

## Structure

Code is split into logic packages:
- `src/model`: Data classes (Drone, Order, Position).
- `src/map`: Zone definitions and map logic.
- `src/service`: The main `Simulator` loop and `ControlCenter` dispatch logic.

## Usage

Compiling and running from the command line:

```bash
cd src
# Compile to ../bin
javac -d ../bin model/*.java map/*.java service/*.java Main.java

# Run
java -cp ../bin Main
```

## Logs

Output is printed to the console with a 300ms delay per step for "real-time" visualization. It shows live events and hourly ASCII summaries:

```text
╔════════════════════════ HOURLY REPORT (01:00) ══════════════════════╗
║ Total Deliveries: 2    | Pending Orders: 4    | Active Drones: 0    ║
╠═════════════════════════════════════════════════════════════════════╣
║ Fleet Status:                                                       ║
║  - Alpha      (#01): AVAILABLE       [Bat:  66%]                    ║
║  - Beta       (#02): AVAILABLE       [Bat: 100%]                    ║
║  - Gamma      (#03): AVAILABLE       [Bat: 100%]                    ║
║  - Delta      (#04): AVAILABLE       [Bat: 100%]                    ║
║  - Epsilon    (#05): AVAILABLE       [Bat: 100%]                    ║
╚═════════════════════════════════════════════════════════════════════╝
```

Adjust `SIMULATION_DURATION` in `Simulator.java` to change the runtime.
