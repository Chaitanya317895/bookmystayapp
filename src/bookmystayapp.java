import java.util.*;
class Reservation {
    String customerName;
    String roomType;
    String roomId;

    public Reservation(String customerName, String roomType, String roomId) {
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return customerName + " | " + roomType + " | " + roomId;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> bookingMap;
    private Stack<String> rollbackStack;
    private InventoryService inventory;

    public CancellationService(InventoryService inventory) {
        this.inventory = inventory;
        bookingMap = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Add confirmed booking (simulate UC6)
    public void addBooking(Reservation r) {
        bookingMap.put(r.roomId, r);
    }

    // Cancel booking
    public void cancelBooking(String roomId) {

        if (!bookingMap.containsKey(roomId)) {
            System.out.println("Cancellation Failed: Booking not found for " + roomId);
            return;
        }

        Reservation r = bookingMap.get(roomId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increaseAvailability(r.roomType);

        // Remove booking
        bookingMap.remove(roomId);

        System.out.println("Booking Cancelled: " + r.customerName +
                " | Room ID: " + roomId);
    }

    // Display rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v10.0 =====");

        InventoryService inventory = new InventoryService();
        CancellationService service = new CancellationService(inventory);

        // Sample confirmed bookings
        service.addBooking(new Reservation("Alice", "Single Room", "SI-1"));
        service.addBooking(new Reservation("Bob", "Double Room", "DO-1"));

        // Cancel booking
        service.cancelBooking("SI-1");
        service.cancelBooking("XX-1"); // invalid

        // Display rollback stack
        service.displayRollbackStack();

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("=================================");
    }
}