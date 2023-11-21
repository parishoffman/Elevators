import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Floor class represents a floor in a building with up and down queues
 * for passengers waiting
 */
class Floor {
    private Queue<Passenger> up;
    private Queue<Passenger> down;
    private final int floorNum;

    /**
     * Constructs a Floor object with the specified floor number
     * @param floorNum The floor number
     */
    public Floor(int floorNum, String structure) {
        this.floorNum = floorNum;
        if (structure.equals("linked")) {
            this.up = new LinkedList<>();
            this.down = new LinkedList<>();
        }
        else {
            this.up = new ArrayDeque<>();
            this.down = new ArrayDeque<>();
        }
    }

    /**
     * Gets the floor number of this floor
     * @return The floor number
     */
    public int getFloorNum() {
        return floorNum;
    }

    /**
     * Gets the queue of passengers waiting to go up
     * @return The up queue
     */
    public Queue<Passenger> upQueue() {
        return up;
    }

    /**
     * Gets the queue of passengers waiting to go down
     * @return The down queue
     */
    public Queue<Passenger> downQueue() {
        return down;
    }

    /**
     * Generates a new passenger with the specified start and end floors
     * and adds them to the appropriate queue
     * @param startTime Time the passenger is generated
     * @param startFloor The floor where the passenger starts
     * @param endFloor The floor where the passenger wants to go
     */
    public void generatePassenger(int startTime, int startFloor, int endFloor) {
        Passenger passenger = new Passenger(startTime, startFloor, endFloor);
        if (endFloor > startFloor) {
            up.add(passenger);
        } else if (endFloor < startFloor){
            down.add(passenger);
        }
    }

    /**
     * Loads passengers from the floor's queue into the specified elevator queue
     * based on the direction it's going
     * @param elevatorQueue The queue of the elevator
     * @param goingUp True if the elevator is going up, false if going down
     */
    public void load(Queue<Passenger> elevatorQueue, boolean goingUp) {
        Queue<Passenger> targetQueue = (goingUp) ? up : down;

        while (!targetQueue.isEmpty()) {
            Passenger passenger = targetQueue.poll();
            elevatorQueue.add(passenger);
        }
    }
}
