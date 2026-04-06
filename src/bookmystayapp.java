/**
 * Hotel Booking Management System
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates serialization and deserialization for persistence.
 */

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    String customerName;
    String roomType;

    public Reservation(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return customerName + " -> " + roomType;
    }
}

// System State (Serializable)
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("State saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("State loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }
        return null;
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v12.0 =====");

        PersistenceService service = new PersistenceService();

        // Try loading previous state
        SystemState state = service.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);

            bookings = new ArrayList<>();
            bookings.add(new Reservation("Alice", "Single Room"));

            System.out.println("New state initialized.");
        } else {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Recovered Bookings:");
            for (Reservation r : bookings) {
                System.out.println(r);
            }
        }

        // Save current state before exit
        SystemState newState = new SystemState(inventory, bookings);
        service.save(newState);

        System.out.println("=================================");
    }
}