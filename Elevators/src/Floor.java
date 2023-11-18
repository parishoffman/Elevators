import java.util.LinkedList;
import java.util.Queue;

class Floor {
    private Queue<Passenger> up;
    private Queue<Passenger> down;
    private final int floorNum;

    public Floor(int floorNum) {
        this.floorNum = floorNum;
        this.up = new LinkedList<>();
        this.down = new LinkedList<>();
    }
    public int getFloorNum() {
        return floorNum;
    }

    public Queue<Passenger> upQueue() {
        return up;
    }

    public Queue<Passenger> downQueue() {
        return down;
    }

    public void generatePassenger(int startTime, int startFloor, int endFloor) {
        Passenger passenger = new Passenger(startTime, startFloor, endFloor);
        if (endFloor > startFloor) {
            up.add(passenger);
        } else if (endFloor < startFloor){
            down.add(passenger);
        }
    }


    public void load(Queue<Passenger> elevatorQueue, boolean goingUp) {
        Queue<Passenger> targetQueue = (goingUp) ? up : down;

        while (!targetQueue.isEmpty()) {
            Passenger passenger = targetQueue.poll();
            elevatorQueue.add(passenger);
        }
    }
}
