import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Request> requests = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        ElevatorSystem es = new ElevatorSystem(requests, elevators);
        Elevator elevator1 = new Elevator(0, 4, -1, Direction.AWAIT);
        Elevator elevator2 = new Elevator(1, 7, -1, Direction.AWAIT);
        Elevator elevator3 = new Elevator(2, 12, 8, Direction.DOWN);
        Elevator elevator4 = new Elevator(3, 5, -1, Direction.AWAIT);
        Elevator elevator5 = new Elevator(4, 5, -1, Direction.AWAIT);
        Elevator elevator6 = new Elevator(5, 6, 12, Direction.UP);
        Elevator elevator7 = new Elevator(6, 5, -1, Direction.AWAIT);

        es.addElevator(elevator1);
        es.addElevator(elevator2);
        es.addElevator(elevator3);
        es.addElevator(elevator4);
        es.addElevator(elevator5);
        es.addElevator(elevator6);
        es.addElevator(elevator7);
        es.removeElevator(elevator2);
        es.addRequest(12, 2);
        es.addRequest(2, 12);
        es.addRequest(8, 2);
        es.addRequest(8, 14);
        System.out.println(elevators.get(1).getId());


        Scanner sc = new Scanner(System.in);
        System.out.print("Calling Floor: ");
        int callingFloor = sc.nextInt();
        System.out.print("Destination Floor: ");
        int destFloor = sc.nextInt();
        es.addRequest(callingFloor, destFloor);
        while (requests.size() != 0) {
            es.executeRequests(requests.get(0));
        }
    }
}