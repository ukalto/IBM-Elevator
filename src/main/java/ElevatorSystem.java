import java.util.*;

public class ElevatorSystem {
    private final List<Request> requests;
    private final List<Elevator> elevators;

    public ElevatorSystem(List<Request> requests, List<Elevator> elevators) {
        this.requests = requests;
        this.elevators = elevators;
    }

    // Adds a request to the list.
    public void addRequest(int callingFloor, int destinationFloor) {
        if (callingFloor == destinationFloor) System.out.println("Please choose a new destination floor.");
        else if (callingFloor > 55 || destinationFloor > 55)
            throw new IllegalArgumentException("There are only 55 floors.");
        else {
            this.requests.add(new Request(callingFloor, destinationFloor, figureOutDirection(callingFloor, destinationFloor)));
            requestMessage(callingFloor, destinationFloor, figureOutDirection(callingFloor, destinationFloor));
        }
    }

    // Adds an elevator if it doesnt exist or if there are not 7 elevators registered.
    public void addElevator(Elevator elevator) {
        if (!elevators.contains(elevator) && elevators.size() < 7) this.elevators.add(elevator);
        else throw new IllegalArgumentException("You can't add more than 7 elevators or this elevator already exists.");
    }

    // Adds an elevator if it doesnt exist or if there are not 7 elevators registered.
    public void removeElevator(Elevator elevator) {
        if (elevators.contains(elevator)) this.elevators.remove(elevator);
        else throw new IllegalArgumentException("You can't delete an elevator which doesn't exist.");
    }

    // Returns the closest available elevator if there is one
    private Elevator checkAvailableElevators(Request request) {
        Optional<Elevator> closestElevator = elevators.stream()
                .filter(d -> d.getDirection() == Direction.AWAIT)
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - request.getCallingFloor())));
        if (closestElevator.isEmpty()) throw new IllegalArgumentException("No elevator available!");
        else return closestElevator.get();
    }

    // Figures out if the elevators goes up or down depending on the calling and the destination floor.
    private Direction figureOutDirection(int callingFloor, int destinationFloor) {
        if (callingFloor == destinationFloor) return Direction.AWAIT;
        else if (callingFloor > destinationFloor) return Direction.DOWN;
        else return Direction.UP;
    }

    // Resets the elevator to waiting position when he arrived his destination.
    private void resetElevator(Elevator elevator) {
        int index = elevators.indexOf(elevator);
        elevators.get(index).setDirection(Direction.AWAIT);
        elevators.get(index).setDestinationFloor(-1);
    }

    // Calls the elevator, if there is not elevator in the called floor.
    private void callElevator(Request request) {
        Elevator elevator = checkAvailableElevators(request);
        // sets the destination floor
        elevator.setDestinationFloor(request.getCallingFloor());
        // sets the direction
        elevator.setDirection(figureOutDirection(elevator.getCurrentFloor(), request.getCallingFloor()));
        // elevator moves
        elevator.move(elevator.getCurrentFloor(), request.getCallingFloor());
        request.setElevID(elevator.getId());
    }

    // If there is no elevator in the current floor it calls one. Afterwards it executes the request.
    // If the elevator arrived it sets his destination to default -1.
    public void executeRequests(Request request) {
        //gets the elevator
        Elevator elevator = checkAvailableElevators(request);
        // sets the id of the elevator which is used for the certain request
        request.setElevID(elevator.getId());
        // sends the call message
        callMessage(elevator.getCurrentFloor(), request.getCallingFloor(), request.getElevID());
        // calls the elevator
        callElevator(request);
        // sends the move message
        elevatorMoveMessage(request.getCallingFloor(), request.getDestinationFloor(), elevator.getId());
        // sets the destination floor
        elevator.setDestinationFloor(request.getDestinationFloor());
        // sets the direction
        elevator.setDirection(figureOutDirection(request.getCallingFloor(), request.getDestinationFloor()));
        // elevator moves
        elevator.move(elevator.getCurrentFloor(), request.getDestinationFloor());
        // resets the elevator
        resetElevator(elevator);
        System.out.format("[Elevator %s arrived, Current floor: %s, Destination floor: %s, Direction: %s]\n", elevator.getId(), elevator.getCurrentFloor(), elevator.getDestinationFloor(), elevator.getDirection());
        requests.remove(request);
    }

    // Sends a message where the closest elevator is, where an elevator is called and which direction he goes.
    private void callMessage(int currentFloor, int callingFloor, int elevID) {
        if (currentFloor != callingFloor) {
            if (figureOutDirection(currentFloor, callingFloor) == Direction.UP) {
                System.out.format("Elevator %s is currently at floor %s and is called at floor %s, therefore he is going up.\n", elevID, currentFloor, callingFloor);
            } else {
                System.out.format("Elevator %s is currently at floor %s and is called at floor %s, therefore he is going down.\n", elevID, currentFloor, callingFloor);
            }
        }
    }

    // Request message is sent when a request is added to the list.
    private void requestMessage(int callingFloor, int destFloor, Direction direction) {
        System.out.format("A new request came in. Direction: %s, Calling Floor: %s, Destination Floor: %s\n", direction, callingFloor, destFloor);
    }

    // Message which elevator is going where
    private void elevatorMoveMessage(int callingFloor, int destFloor, int elevID) {
        if (figureOutDirection(callingFloor, destFloor) == Direction.UP) {
            System.out.format("Elevator %s is going from floor: %s up to floor %s.\n", elevID, callingFloor, destFloor);
        } else {
            System.out.format("Elevator %s is going from floor: %s down to floor %s.\n", elevID, callingFloor, destFloor);
        }
    }
}