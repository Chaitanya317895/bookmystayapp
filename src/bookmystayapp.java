import java.util.*;
class Reservation {
    String customerName;
    String roomType;

    public Reservation(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
    }

    public synchronized boolean bookRoom(String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {
            Reservation r;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.poll();
            }

            // Process booking
            boolean success = inventory.bookRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked for " + r.customerName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed for " + r.customerName);
            }
        }
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v11.0 =====");

        // Shared queue
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room"));

        InventoryService inventory = new InventoryService();

        // Multiple threads
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("=================================");
    }
}