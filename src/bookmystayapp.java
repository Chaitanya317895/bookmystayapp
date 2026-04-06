import java.util.*;

// Reservation (from previous use cases)
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
        return customerName + " | " + roomType + " | Room ID: " + roomId;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Report Service
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\nBooking History:");
        for (Reservation r : history.getAllReservations()) {
            System.out.println(r);
        }
    }

    // Summary report
    public void generateSummary() {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            countMap.put(r.roomType,
                    countMap.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\nBooking Summary:");
        for (String type : countMap.keySet()) {
            System.out.println(type + " -> " + countMap.get(type));
        }
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v8.0 =====");

        BookingHistory history = new BookingHistory();

        // Sample confirmed bookings (from UC6)
        history.addReservation(new Reservation("Alice", "Single Room", "SI-1"));
        history.addReservation(new Reservation("Bob", "Double Room", "DO-1"));
        history.addReservation(new Reservation("Charlie", "Suite Room", "SU-1"));

        BookingReportService reportService = new BookingReportService(history);

        // Display all bookings
        reportService.displayAllBookings();

        // Generate summary
        reportService.generateSummary();

        System.out.println("=================================");
    }
}