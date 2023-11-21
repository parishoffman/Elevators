import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;

/**
 * The Elevator class represents an elevator that is capable of moving
 * between loading and unloading passengers, and handling stop requests
 */
public class Elevator {
     public PriorityQueue<Passenger> up;
     private PriorityQueue<Passenger> down;
     public PriorityQueue<Integer> stopUp;
     private PriorityQueue<Integer> stopDown;
     private int currentFloor;
     private boolean goingUp;
     private final int capacity;
     private final int numFloors;

    /**
     * Constructs an elevator with the specified number of floors
     * @param numFloors Total number of floors
     */
     Elevator(int numFloors, int capacity) {
         this.up = new PriorityQueue<>();
         this.down = new PriorityQueue<>();
         this.stopUp = new PriorityQueue<>();
         this.stopDown = new PriorityQueue<>();
         this.currentFloor = 1;
         this.numFloors = numFloors;
         goingUp = true;
         this.capacity = capacity;
     }

    /**
     * Gets the current floor of the elevator
     * @return The current floor
     */
    public int getCurrentFloor() {
         return currentFloor;
    }

    /**
     * Checks if the elevator is currently moving up
     * @return True if the elevatir is moving up, false otherwise
     */
    public boolean isGoingUp() {
         return goingUp;
    }

    /**
     * Gets the queue of passengers currently in the elevator
     * @return The queue of passengers
     */
    public Queue<Passenger> getPassengerQueue() {
         return up;
    }

    /**
     * Gets the queue of floors where the elevator needs to stop
     * @return The queue of stop requests
     */
    public Queue<Integer> getRequestQueue() {
         return stopUp;
    }

    /**
     * Checks to see if the elevator is at capacity
     * @return True if the elevator has space for more passengers
     */
    public boolean hasSpace() {
        return (up.size() + down.size()) < capacity;
    }
    /**
     * Loads passengers from the specified queue into the elevator
     * @param peopleWaiting The queue of passengers waiting for the elevator
     */
    public void load(Queue<Passenger> peopleWaiting) {
         while (!peopleWaiting.isEmpty() && hasSpace()) {
             Passenger data = peopleWaiting.remove();
             if (data.getEndFloor() > currentFloor) {
                 up.add(data);
                 if (!stopUp.isEmpty() && stopUp.peek() == data.getStartFloor()) {
                     stopUp.remove();
                 }
             } else {
                 down.add(data);
                 if (!stopDown.isEmpty() && stopDown.peek() == data.getStartFloor()) {
                     stopDown.remove();
                 }
             }
         }
     }

    /**
     * Unloads passengers from the elevator whose destination is the current floor
     * @return The queue of passengers leaving the elevator
     */
    public Queue<Passenger> unload() {
        PriorityQueue<Passenger> passengersLeaving = new PriorityQueue<>();
         if (goingUp) {
             while (!up.isEmpty() && up.peek().getEndFloor() == currentFloor) {
                 passengersLeaving.add(up.remove());
             }
         }
         else {
             while (!down.isEmpty() && down.peek().getEndFloor() == currentFloor) {
                 passengersLeaving.add(down.remove());
             }
         }
         return passengersLeaving;
    }

    /**
     * Moves the elevator to the next floor based on its current direction and stop requests
     */
    public void travel() {
        int prevFloor = currentFloor;
        if (goingUp) {
            if (!stopUp.isEmpty()) {
                currentFloor = Math.min(currentFloor + 5, stopUp.peek());
            } else {
                goingUp = false;
                currentFloor = Math.min(currentFloor + 5, numFloors - 1);
            }
        }
        else {
            if (!stopDown.isEmpty()) {
                currentFloor = Math.max(currentFloor - 5, stopDown.peek());
            } else {
                goingUp = true;
                currentFloor = Math.max(currentFloor - 5, 0);
            }
        }
        if (currentFloor == prevFloor) {
            if (goingUp) {
                currentFloor = Math.min(currentFloor + 5, numFloors - 1);
            }
            else {
                currentFloor = Math.max(currentFloor - 5, 1);
            }
        }

        while (!stopUp.isEmpty() && stopUp.peek() == currentFloor) {
            stopUp.remove();
        }
        while (!stopDown.isEmpty() && stopDown.peek() == currentFloor) {
            stopDown.remove();
        }

    }

    /**
     * Adds a floor to the queue of stop requests based on the current direction
     * @param floor The floor where the elevator should stop
     */
    public void requestStop(int floor) {
         if (floor > currentFloor) {
             stopUp.add(floor);
         } else if (floor < currentFloor) {
             stopDown.add(floor);
         }
     }
}
