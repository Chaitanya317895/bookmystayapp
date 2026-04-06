


    abstract class Room {
        String type;
        int beds;
        double price;

        public Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        // Abstract method
        abstract void displayDetails();
    }

    // Single Room
    class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 1000);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // Double Room
    class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 2000);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // Suite Room
    class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 5000);
        }

        void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // Main Class
    public class bookmystayapp {

        public static void main(String[] args) {

            System.out.println("===== Book My Stay App v2.1 =====");

            // Polymorphism
            Room r1 = new SingleRoom();
            Room r2 = new DoubleRoom();
            Room r3 = new SuiteRoom();

            // Static Availability
            int singleAvailable = 5;
            int doubleAvailable = 3;
            int suiteAvailable = 2;

            // Display details
            r1.displayDetails();
            System.out.println("Available: " + singleAvailable);

            r2.displayDetails();
            System.out.println("Available: " + doubleAvailable);

            r3.displayDetails();
            System.out.println("Available: " + suiteAvailable);

            System.out.println("=================================");
        }
    }