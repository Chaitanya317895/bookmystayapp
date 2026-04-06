
import java.util.*;

            // Reservation (from UC5)
            class Reservation {
                String customerName;
                String roomType;

                public Reservation(String customerName, String roomType) {
                    this.customerName = customerName;
                    this.roomType = roomType;
                }
            }

            // Inventory Service
            class InventoryService {
                private Map<String, Integer> inventory;

                public InventoryService() {
                    inventory = new HashMap<>();
                    inventory.put("Single Room", 2);
                    inventory.put("Double Room", 1);
                    inventory.put("Suite Room", 1);
                }

                public int getAvailability(String type) {
                    return inventory.getOrDefault(type, 0);
                }

                public void reduceAvailability(String type) {
                    inventory.put(type, inventory.get(type) - 1);
                }
            }

            // Booking Service
            class BookingService {

                private Queue<Reservation> queue;
                private InventoryService inventory;

                // Track allocated rooms (no duplicates)
                private Set<String> allocatedRoomIds;

                // Map room type → allocated IDs
                private Map<String, Set<String>> allocationMap;

                public BookingService(Queue<Reservation> queue, InventoryService inventory) {
                    this.queue = queue;
                    this.inventory = inventory;
                    this.allocatedRoomIds = new HashSet<>();
                    this.allocationMap = new HashMap<>();
                }

                // Generate unique room ID
                private String generateRoomId(String type, int count) {
                    return type.substring(0, 2).toUpperCase() + "-" + count;
                }

                // Process booking
                public void processBookings() {

                    while (!queue.isEmpty()) {

                        Reservation r = queue.poll(); // FIFO
                        String type = r.roomType;

                        if (inventory.getAvailability(type) > 0) {

                            int count = allocationMap.getOrDefault(type, new HashSet<>()).size() + 1;
                            String roomId = generateRoomId(type, count);

                            // Ensure uniqueness
                            if (!allocatedRoomIds.contains(roomId)) {

                                allocatedRoomIds.add(roomId);

                                allocationMap.putIfAbsent(type, new HashSet<>());
                                allocationMap.get(type).add(roomId);

                                inventory.reduceAvailability(type);

                                System.out.println("Booking Confirmed: " + r.customerName +
                                        " | Room Type: " + type +
                                        " | Room ID: " + roomId);
                            }

                        } else {
                            System.out.println("Booking Failed (No Availability): " +
                                    r.customerName + " | " + type);
                        }
                    }
                }
            }

            // Main Class
            public class bookmystayapp {

                public static void main(String[] args) {

                    System.out.println("===== Book My Stay App v6.0 =====");

                    // Booking Queue
                    Queue<Reservation> queue = new LinkedList<>();
                    queue.add(new Reservation("Alice", "Single Room"));
                    queue.add(new Reservation("Bob", "Single Room"));
                    queue.add(new Reservation("Charlie", "Single Room")); // exceeds

                    // Services
                    InventoryService inventory = new InventoryService();
                    BookingService bookingService = new BookingService(queue, inventory);

                    // Process bookings
                    bookingService.processBookings();

                    System.out.println("=================================");
                }
            }