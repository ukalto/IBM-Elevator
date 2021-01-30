import lombok.Data;

@Data
public class Request {
    private int elevID;
    private int callingFloor;
    private int destinationFloor;
    private Direction direction;

    public Request(int callingFloor, int destinationFloor, Direction direction) {
        this.callingFloor = callingFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }
}