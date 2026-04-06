
import java.util.*;

    // Room Domain Model
    abstract class Room {
        String type;
        double price;

        public Room(String type, double price) {
            this.type = type;
            this.price = price;
        }

        public void displayDetails() {
            System.out.println(type + " | Price: ₹" + price);
        }
    }

    class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1000);
        }
    }

    class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2000);
        }
    }

    class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 5000);
        }
    }

    // Inventory (Read-only access used here)
    class RoomInventory {
        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 0); // unavailable
            inventory.put("Suite Room", 1);
        }

        public int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        public Set<String> getRoomTypes() {
            return inventory.keySet();
        }
    }

    // Search Service
    class RoomSearchService {

        private RoomInventory inventory;
        private Map<String, Room> roomMap;

        public RoomSearchService(RoomInventory inventory) {
            this.inventory = inventory;

            // Room details
            roomMap = new HashMap<>();
            roomMap.put("Single Room", new SingleRoom());
            roomMap.put("Double Room", new DoubleRoom());
            roomMap.put("Suite Room", new SuiteRoom());
        }

        public void searchAvailableRooms() {
            System.out.println("Available Rooms:");

            for (String type : inventory.getRoomTypes()) {
                int available = inventory.getAvailability(type);

                // Only show available rooms
                if (available > 0) {
                    Room room = roomMap.get(type);
                    room.displayDetails();
                    System.out.println("Available: " + available);
                    System.out.println("----------------------");
                }
            }
        }
    }

    // Main Class
    public class bookmystayapp {

        public static void main(String[] args) {

            System.out.println("===== Book My Stay App v4.1 =====");

            RoomInventory inventory = new RoomInventory();
            RoomSearchService searchService = new RoomSearchService(inventory);

            // Perform search (read-only)
            searchService.searchAvailableRooms();

            System.out.println("=================================");
        }
    }