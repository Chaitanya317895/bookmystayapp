
import java.util.*;

        // Reservation class (represents booking request)
        class Reservation {
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

        // Booking Request Queue
        class BookingQueue {
            private Queue<Reservation> queue;

            public BookingQueue() {
                queue = new LinkedList<>();
            }

            // Add request
            public void addRequest(Reservation r) {
                queue.add(r);
                System.out.println("Request added: " + r);
            }

            // Display queue
            public void displayQueue() {
                System.out.println("\nCurrent Booking Queue:");
                for (Reservation r : queue) {
                    System.out.println(r);
                }
            }
        }

        // Main Class
        public class bookmystayapp {

            public static void main(String[] args) {

                System.out.println("===== Book My Stay App v5.0 =====");

                BookingQueue bookingQueue = new BookingQueue();

                // Add booking requests
                bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
                bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
                bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

                // Display queue (FIFO order)
                bookingQueue.displayQueue();

                System.out.println("=================================");
            }
        }