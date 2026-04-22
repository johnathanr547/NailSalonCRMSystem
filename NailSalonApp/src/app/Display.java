package app;

import java.util.Scanner; 

public class Display {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        System.out.println("===========================");
        System.out.println("   NAIL TECH SCHEDULER");
        System.out.println("===========================");
        System.out.println("1. Availability Calendar");
        System.out.println("2. Appointment View");
        System.out.println("0. Exit");
        System.out.println("===========================");
        System.out.print("Choose an option: ");
 
        int choice = scanner.nextInt();
 
        switch (choice) {
            case 1 -> showCalendar();
            case 2 -> showAppointments();
            case 0 -> System.out.println("Goodbye!");
            default -> System.out.println("Invalid choice. Please enter 1, 2, or 0.");
        }
 
        scanner.close();
    }
 
    static void showCalendar() {
        System.out.println("\n--- AVAILABILITY CALENDAR ---");
        System.out.println("Mon Apr 21: 10:00 AM - BOOKED | 2:00 PM - FREE");
        System.out.println("Tue Apr 22: 11:00 AM - FREE   | 3:00 PM - BOOKED");
        System.out.println("Wed Apr 23: All slots FREE");
    }
 
    static void showAppointments() {
        System.out.println("\n--- APPOINTMENTS ---");
        System.out.println("#1 | Jordan Lee  | Gel Manicure     | Apr 21 10:00 AM | APPROVED");
        System.out.println("#2 | Mia Torres  | Acrylic Full Set | Apr 21 1:00 PM  | PENDING");
        System.out.println("#3 | Riley Chen  | Nail Art         | Apr 22 11:00 AM | APPROVED");
    }

}
