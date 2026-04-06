import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// Booking Validator
class BookingValidator {

    public static void validate(String customerName, String roomType,
                                InventoryService inventory)
            throws InvalidBookingException {

        // Validate customer name
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidBookingException("Invalid customer name");
        }

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// Booking Service
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String customerName, String roomType) {
        try {
            // Validation (fail-fast)
            BookingValidator.validate(customerName, roomType, inventory);

            // Allocation
            inventory.reduceAvailability(roomType);

            System.out.println("Booking Successful: " + customerName +
                    " -> " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v9.0 =====");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Test cases
        bookingService.bookRoom("Alice", "Single Room");   // valid
        bookingService.bookRoom("", "Double Room");        // invalid name
        bookingService.bookRoom("Bob", "Luxury Room");     // invalid type
        bookingService.bookRoom("Charlie", "Suite Room");  // no availability

        System.out.println("=================================");
    }
}