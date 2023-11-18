/**
 * The Passenger class represents a passenger in an elevator simulation, with information
 * about the starting floor, ending floor, and the time the passenger was generates
 *
 * This class implements the Comparable interface, allowing passengers to be compared based on
 * their ending floors for sorting purposes
 */
public class Passenger implements Comparable<Passenger>{
    private int endFloor;
    private int startTime;
    private int startFloor;

    /**
     * Constructs a Passenger object with the specified start time, start floor, and end floor
     * @param startTime The time at which a passenger was generated
     * @param startFloor The floor where the passenger is located
     * @param endFloor The floor the passenger wants to go to
     */
    Passenger(int startTime, int startFloor, int endFloor) {
        this.endFloor = endFloor;
        this.startFloor = startFloor;
        this.startTime = startTime;
    }

    /**
     * Gets the ending floor where the passenger wants to go to
     * @return The ending floor
     */
    public int getEndFloor() {
        return endFloor;
    }

    /**
     * Gets the starting floor where the passenger is
     * @return The starting floor
     */
    public int getStartFloor() {
        return startFloor;
    }

    /**
     * Sets the starting floor where the passenger is located
     * @param startFloor The new starting floor
     */
    public void setStartFloor(int startFloor) {
        this.startFloor = startFloor;
    }

    /**
     * Gets the time at which the passenger was generated
     * @return The start time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Compares this passenger to another passenger based on their ending floors
     *
     * @param other the object to be compared.
     * @return A negative integer, zero, or a positive integer as this passenger is
     * less than, equal to, or greater than the specified passenger
     */
    public int compareTo(Passenger other) {
        return Integer.compare(this.endFloor, other.endFloor);
    }
}
