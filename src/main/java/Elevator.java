import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Elevator {
    private int id;
    private int currentFloor;
    private int destinationFloor;
    private Direction direction;

    public void move(int currentFloor, int destinationFloor) {
        if (currentFloor < destinationFloor) {
            while (currentFloor != destinationFloor) {
                System.out.format("[Elevator floor: %s, Destination floor: %s, Direction: %s]\n", currentFloor, destinationFloor, Direction.UP);
                currentFloor++;
                setCurrentFloor(currentFloor);
            }
        } else if (currentFloor > destinationFloor) {
            while (currentFloor != destinationFloor) {
                System.out.format("[Elevator floor: %s, Destination floor: %s, Direction: %s]\n", currentFloor, destinationFloor, Direction.DOWN);
                currentFloor--;
                setCurrentFloor(currentFloor);
            }
        }
    }
}