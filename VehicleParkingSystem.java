import java.util.ArrayList;
import java.util.Scanner;

class Date {
    int day;
    int month;
    int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}

class Time {
    int hour;
    int minute;
    int second;

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }
}

class Vehicle {
    private String regNumber;
    private int type;
    private Date date;
    private Time arrival;
    private Time departure;

    public Vehicle(int type, String regNumber, Time arrival, Date date) {
        this.type = type;
        this.regNumber = regNumber;
        this.arrival = arrival;
        this.date = date;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public int getType() {
        return type;
    }

    public Time getArrival() {
        return arrival;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }

    public Time getDeparture() {
        return departure;
    }

    public Date getDate() {
        return date;
    }

    public void display() {
        System.out.println(getTypeName() + "\t\t\t" + regNumber + "\t\t\t" + date + "\t\t\t" + arrival);
    }

    public String getTypeName() {
        return type == 1 ? "Car" : "Bike";
    }
}

public class VehicleParkingSystem {
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static int vehicleCount = 0;
    private static int carCount = 0;
    private static int bikeCount = 0;
    private static int moneyCollected = 0;

    public static void addVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter type of vehicle (1 for Car, 2 for Bike): ");
        int type = scanner.nextInt();
        System.out.print("Enter vehicle registration number: ");
        String regNumber = scanner.next();
        Time arrival = readTime("Enter arrival time (HH.MM.SS): ");
        Date date = readDate("Enter Date (DD/MM/YYYY): ");

        Vehicle vehicle = new Vehicle(type, regNumber, arrival, date);
        vehicles.add(vehicle);
        vehicleCount++;

        if (type == 1) carCount++;
        else bikeCount++;

        System.out.println("\nVehicle added");
    }

    private static Time readTime(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        scanner.useDelimiter("[.\\s]+");
        int hour = scanner.nextInt();
        int minute = scanner.nextInt();
        int second = scanner.nextInt();
        return new Time(hour, minute, second);
    }

    private static Date readDate(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        scanner.useDelimiter("[/\\s]+"); 
        int day = scanner.nextInt();
        int month = scanner.nextInt();
        int year = scanner.nextInt();
        return new Date(day, month, year);
    }


    public static int timeDifference(Time arr, Time dep) {
        int arrSec = arr.hour * 3600 + arr.minute * 60 + arr.second;
        int depSec = dep.hour * 3600 + dep.minute * 60 + dep.second;
        int diffSec = depSec - arrSec;
        return diffSec / 3600;
    }

    public static void vehicleDeparture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle type (1 for Car/2 for Bike): ");
        int type = scanner.nextInt();
        System.out.print("Enter vehicle number: ");
        String regNumber = scanner.next();
        Time departure = readTime("Enter departure Time (HH.MM.SS): ");

        for (int j = 0; j < vehicles.size(); j++) {
            Vehicle vehicle = vehicles.get(j);
            if (vehicle.getRegNumber().equals(regNumber) && vehicle.getType() == type) {
                vehicle.setDeparture(departure);
                int timeDiff = timeDifference(vehicle.getArrival(), departure);
                int charge = calculateCharge(vehicle.getType(), timeDiff);

                if (vehicle.getType() == 1) carCount--;
                else bikeCount--;

                System.out.println("\nVehicle with registration number " + vehicle.getRegNumber() + " has left the parking after paying Rs. " + charge);
                vehicles.remove(j);
                vehicleCount--;
                moneyCollected += charge;
                return;
            }
        }

        System.out.println("\nWrong Entry, Try Again");
        vehicleDeparture();
    }

    private static int calculateCharge(int type, int timeDiff) {
        if (type == 1) {
            if (timeDiff < 2) return 30;
            else if (timeDiff < 5) return 40;
            else return 60;
        } else {
            if (timeDiff < 2) return 10;
            else if (timeDiff < 5) return 15;
            else return 20;
        }
    }

    public static void displayVehicles() {
        System.out.println("Vehicle Type\t\tVehicle Reg. Number\t\t\tArrival Date\t\t\tArrival Time");
        for (Vehicle vehicle : vehicles) {
            vehicle.display();
        }
    }

    public static void displayTotalVehicles() {
        System.out.println("Number of vehicles parked currently: " + vehicleCount);
        System.out.println("Number of cars parked currently: " + carCount);
        System.out.println("Number of bikes parked currently: " + bikeCount);
    }

    public static void displayTotalAmount() {
        System.out.println("Total amount of money collected: " + moneyCollected);
    }

    public static void searchVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle registration number to search: ");
        String regNumber = scanner.next();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegNumber().equals(regNumber)) {
                System.out.println("Vehicle found:");
                vehicle.display();
                return;
            }
        }
        System.out.println("Vehicle not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("________________________________________________________");
            System.out.println("                 VEHICLE PARKING SYSTEM                 ");
            System.out.println("Available choices: ");
            System.out.println("1. Add a new vehicle");
            System.out.println("2. Get total number of vehicles parked");
            System.out.println("3. Departure of vehicle");
            System.out.println("4. Get total amount of money collected");
            System.out.println("5. Display vehicles parked currently");
            System.out.println("6. Search for a vehicle");
            System.out.println("7. Exit");
            System.out.println("________________________________________________________");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Add new vehicle: ");
                    addVehicle();
                    break;
                case 2:
                    displayTotalVehicles();
                    break;
                case 3:
                    System.out.println("Departure of vehicle: ");
                    vehicleDeparture();
                    break;
                case 4:
                    displayTotalAmount();
                    break;
                case 5:
                    displayVehicles();
                    break;
                case 6:
                    searchVehicle();
                    break;
                case 7:
                    System.exit(0);
            }
        } while(true);
    }
}
