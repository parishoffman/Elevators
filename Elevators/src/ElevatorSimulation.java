import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The ElevatorSimulation class represents a simulation of elevators and floors,
 * tracking passengers and calculating statistics on passenger wait times
 */
public class ElevatorSimulation {
        private List<Elevator> elevators;
        private List<Floor> floors;
        private List<Integer> times;
        private int tick;
        private float ratio;
        private Map<Passenger, Integer> passengerWaitTimes;

    /**
     * Constructs an ElevatorSimulation with the specified number of elevators, floors, tick, and ratio
     * @param numElevators The number of elevators
     * @param numFloors THe number of floors
     * @param tick Total number of ticks for the simulation
     * @param ratio The ratio of generating passengers in each tick
     */
        public ElevatorSimulation(int numElevators, int numFloors, int tick, float ratio) {
            this.elevators = new ArrayList<>();
            this.floors = new ArrayList<>();
            this.ratio = ratio;
            this.tick = tick;

            for (int i = 0; i < numElevators; i++) {
                elevators.add(new Elevator(numFloors));
            }

            for (int i = 1; i <= numFloors; i++) {
                floors.add(new Floor(i));
            }
        }

    /**
     * Runs the elevator simulation, generating passengers, moving elevators,
     * and calculating statistics
     */
    public void runSimulation() {
            passengerWaitTimes = new HashMap<>();
            for (int i = 0; i < tick; i++) {
                Random random = new Random();
                float randomFloat = random.nextFloat();
                int requestedFloor = -1;
                if (randomFloat < ratio) {
                    int endFloor = random.nextInt(1, floors.size());
                    int startFloor = random.nextInt(1, floors.size());
                    int startTime = i;
                    floors.get(startFloor).generatePassenger(startTime, startFloor, endFloor);
                    requestedFloor = startFloor;
                }

                for (Elevator elevator: elevators) {
                    if (requestedFloor != -1 && elevator.isGoingUp() && requestedFloor > elevator.getCurrentFloor()) {
                        elevator.requestStop(requestedFloor);
                    } else if (requestedFloor != -1 && !elevator.isGoingUp() && requestedFloor < elevator.getCurrentFloor()) {
                        elevator.requestStop(requestedFloor);
                    }
                    Floor currentFloor = floors.get(elevator.getCurrentFloor());
                    if (!currentFloor.upQueue().isEmpty()) {
                        elevator.load(currentFloor.upQueue());
                    }
                    elevator.travel();

                    Queue<Passenger> unloadedPassengers = elevator.unload();

                   for (Passenger passenger : elevator.getPassengerQueue()) {
                        if (!passengerWaitTimes.containsKey(passenger)) {
                            passengerWaitTimes.put(passenger, i - passenger.getStartTime());
                        }
                    }
                }
            }
            calculateStatistics(passengerWaitTimes);
        }

    /**
     * Calculates and prints statistics based on passenger wait times
     * @param passengerWaitTimes Map containing passenger wait times
     */
    private void calculateStatistics(Map<Passenger, Integer> passengerWaitTimes) {
            int totalTime = 0;
            int longestTime = Integer.MIN_VALUE;
            int shortestTime = Integer.MAX_VALUE;

            for (int waitTime : passengerWaitTimes.values()) {
                totalTime += waitTime;
                longestTime = Math.max(longestTime, waitTime);
                shortestTime = Math.min(shortestTime, waitTime);
            }
            int numPassengers = passengerWaitTimes.size();
            double averageTime = numPassengers > 0 ? (double) totalTime / numPassengers : 0.0;

            System.out.println("Average Time: " + averageTime);
            System.out.println("Longest Time: " + (longestTime == Integer.MIN_VALUE ? 0 : longestTime));
            System.out.println("Shortest Time: " + (shortestTime == Integer.MAX_VALUE ? 0 : shortestTime));
        }
}
