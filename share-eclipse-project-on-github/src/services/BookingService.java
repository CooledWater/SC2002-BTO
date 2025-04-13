package services;

import entity.Applicant;
import entity.Booking;
import entity.FlatType;
import entity.Project;

import java.time.LocalDate;
import java.util.Scanner;

public class BookingService {

    public void bookFlat(Applicant applicant) {
        if (applicant.getBooking() != null) {
            System.out.println("You have already booked a flat.");
            return;
        }

        if (applicant.getProjectApp() == null || applicant.getProjectApp().getProject() == null) {
            System.out.println("You must have an approved project application before booking.");
            return;
        }

        Project project = applicant.getProjectApp().getProject();
        Scanner sc = new Scanner(System.in);

        FlatType flatType = null;
        while (flatType == null) {
            System.out.println("Select flat type to book:");
            System.out.println("0 - TWO_ROOM");
            System.out.println("1 - THREE_ROOM");
            System.out.print("Enter choice: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 0 && project.getNumberOf2Rooms() > 0) {
                    flatType = FlatType.TWO_ROOM;
                    project.setNumberOf2Rooms(project.getNumberOf2Rooms() - 1);
                } else if (choice == 1 && project.getNumberof3Rooms() > 0) {
                    flatType = FlatType.THREE_ROOM;
                    project.setNumberof3Rooms(project.getNumberof3Rooms() - 1);
                } else {
                    System.out.println("Invalid selection or flat type not available.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 0 or 1.");
            }
        }

        Booking booking = new Booking(flatType, project, LocalDate.now());
        applicant.setBooking(booking);
        System.out.println("Booking successful!");
    }

    public void viewBooking(Applicant applicant) {
        Booking booking = applicant.getBooking();
        if (booking == null) {
            System.out.println("You have not made a booking.");
            return;
        }

        System.out.println("=== Booking Details ===");
        System.out.println("Flat Type: " + booking.getFlatType());
        System.out.println("Project Name: " + booking.getProject().getName());
        System.out.println("Neighbourhood: " + booking.getProject().getNeighbourhood());
        System.out.println("Booking Date: " + booking.getBookingDate());
        System.out.println("=======================");
    }
}

