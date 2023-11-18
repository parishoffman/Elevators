public class Passenger implements Comparable<Passenger>{
    private int endFloor;
    private int startTime;
    private int startFloor;

    Passenger(int startTime, int startFloor, int endFloor) {
        this.endFloor = endFloor;
        this.startFloor = startFloor;
        this.startTime = startTime;
    }

    public int getEndFloor() {
        return endFloor;
    }
    public int getStartFloor() {
        return startFloor;
    }
    public void setStartFloor(int startFloor) {
        this.startFloor = startFloor;
    }

    public int getStartTime() {
        return startTime;
    }

    public int compareTo(Passenger other) {
        return Integer.compare(this.endFloor, other.endFloor);
    }
}
