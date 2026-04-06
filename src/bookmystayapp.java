
import java.util.*;
class Service {
    String name;
    double cost;
    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service " + service.name +
                " to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");
        for (Service s : services) {
            System.out.println(s);
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        double total = 0;
        if (services != null) {
            for (Service s : services) {
                total += s.cost;
            }
        }
        return total;
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v7.0 =====");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample Reservation ID (from UC6)
        String reservationId = "SI-1";

        // Add services
        manager.addService(reservationId, new Service("Breakfast", 200));
        manager.addService(reservationId, new Service("WiFi", 100));
        manager.addService(reservationId, new Service("Airport Pickup", 500));

        // Display services
        manager.displayServices(reservationId);

        // Total cost
        double total = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("=================================");
    }
}