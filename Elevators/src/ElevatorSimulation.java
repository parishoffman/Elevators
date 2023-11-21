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
        public ElevatorSimulation(int numElevators, int numFloors, int tick, float ratio, int capacity) {
            this.elevators = new ArrayList<>();
            this.floors = new ArrayList<>();
            this.ratio = ratio;
            this.tick = tick;

            for (int i = 0; i < numElevators; i++) {
                elevators.add(new Elevator(numFloors, capacity));
            }

            for (int i = 1; i <= numFloors; i++) {
                floors.add(new Floor(i));
            }
        }

    /**
     * Runs the elevator simulation, generating passengers, moving elevators,
     * and calculating statistics
     */
    public void runSimulation(String structure) {
            passengerWaitTimes = new HashMap<>();
            Random random = new Random();
            for (int i = 0; i < tick; i++) {
                List<Integer> requestedFloors;
                if (structure.equals("linked")) {
                    requestedFloors = new LinkedList<>();
                }
                else {
                    requestedFloors = new ArrayList<>();
                }

                for (int floor = 0; floor < floors.size(); floor++) {
                    float randomFloat = random.nextFloat();
                    if (randomFloat < ratio) {
                        int endFloor = random.nextInt(1, floors.size());
                        floors.get(floor).generatePassenger(i, floor + 1, endFloor);
                        requestedFloors.add(floor);
                    }
                }

                for (Elevator elevator: elevators) {
                   /* System.out.println("--------------------------------------------");
                    System.out.println("Elevator floor: " + elevator.getCurrentFloor());
                    System.out.println("Passengers on the elevator: " + elevator.getPassengerQueue().size());
                    System.out.println("Requested Passengers: " + elevator.getRequestQueue().size());*/
                    for (Integer requestedFloor : requestedFloors) {
                        if (elevator.isGoingUp() && requestedFloor > elevator.getCurrentFloor()) {
                            elevator.requestStop(requestedFloor);
                        } else if (!elevator.isGoingUp() && requestedFloor < elevator.getCurrentFloor()) {
                            elevator.requestStop(requestedFloor);
                        }
                    }
                    Floor currentFloor = floors.get(elevator.getCurrentFloor());
                    if (elevator.isGoingUp()) {
                        elevator.load(currentFloor.upQueue());
                    }
                    else {
                        elevator.load(currentFloor.downQueue());
                    }

                    elevator.travel();

                    Queue<Passenger> unloadedPassengers = elevator.unload();

                    for (Passenger passenger : unloadedPassengers) {
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
