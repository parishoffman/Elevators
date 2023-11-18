import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;

public class Elevator {
     public PriorityQueue<Passenger> up;
     private PriorityQueue<Passenger> down;
     public PriorityQueue<Integer> stopUp;
     private PriorityQueue<Integer> stopDown;
     private int currentFloor;
     private boolean goingUp;
     private int capacity;
     private final int numFloors;
     ElevatorSimulation simulation;
     Elevator(int numFloors) {
         this.up = new PriorityQueue<>();
         this.down = new PriorityQueue<>();
         this.stopUp = new PriorityQueue<>();
         this.stopDown = new PriorityQueue<>();
         this.currentFloor = 1;
         this.numFloors = numFloors;
         goingUp = true;
     }

    public int getCurrentFloor() {
         return currentFloor;
    }

    public boolean isGoingUp() {
         return goingUp;
    }

    public Queue<Passenger> getPassengerQueue() {
         return up;
    }

    public Queue<Integer> getRequestQueue() {
         return stopUp;
    }
    public void load(Queue<Passenger> peopleWaiting) {
         while (!peopleWaiting.isEmpty()) {
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

    }

    public void requestStop(int floor) {
         if (floor > currentFloor) {
             stopUp.add(floor);
         } else if (floor < currentFloor) {
             stopDown.add(floor);
         }
     }
}
