package services;

import entity.*;

import java.time.LocalDate;
import java.util.*;
import repository.ReceiptRepository;

public class BookingService {
    private Map<Project, List<Applicant>> pendingBookings = new HashMap<>();
    private ReceiptRepository receiptRepository;

    public BookingService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public void bookFlat(Applicant applicant) {      
        ProjectApp app = applicant.getProjectApp();
        if (app == null || app.getProject() == null || app.getStatus() != AppStatus.SUCCESSFUL) {
            System.out.println("You must have an approved project application before booking.");
            return;
        }

        if (app.getStatus() == AppStatus.BOOKED) {
            System.out.println("You have already booked a flat.");
            return;
        }
        
        Project project = app.getProject();
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
                } else if (choice == 1 && project.getNumberOf3Rooms() > 0) {
                    flatType = FlatType.THREE_ROOM;
                    project.setNumberOf3Rooms(project.getNumberOf3Rooms() - 1);
                } else {
                    System.out.println("Invalid selection or flat type not available.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 0 or 1.");
            }
        }

        app.setFlatType(flatType);
        app.setStatus(AppStatus.PENDING);
        
        pendingBookings.computeIfAbsent(project, k -> new ArrayList<>()).add(applicant);
        System.out.println("Booking request submitted. Pending officer approval.");
    }
    
    public void assistBookingFlat(Officer officer) {
        Project handledProject = officer.getHandlingProj();
        if (handledProject == null) {
            System.out.println("You are not handling any projects currently.");
            return;
        }

        List<Applicant> applicants = pendingBookings.getOrDefault(handledProject, new ArrayList<>());
        if (applicants.isEmpty()) {
            System.out.println("No pending booking requests for this project.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        while (!applicants.isEmpty()) {
            System.out.println("\nPending bookings for project: " + handledProject.getName());
            for (int i = 0; i < applicants.size(); i++) {
                Applicant a = applicants.get(i);
                ProjectApp app = a.getProjectApp();
                System.out.printf("%d. %s (%d y/o), Requested: %s\n", i + 1, a.getNRIC(), a.getAge(), app.getFlatType());
            }

            System.out.print("Enter number to review (or 0 to exit): ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 0) {
                    System.out.println("Exiting booking approval.");
                    return;
                }
                if (choice < 1 || choice > applicants.size()) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                Applicant selected = applicants.get(choice - 1);
                ProjectApp app = selected.getProjectApp();
                FlatType flatType = app.getFlatType();

                System.out.printf("Approve booking for %s (%s)? (y/n): ", selected.getName(), flatType);
                String input = sc.nextLine().trim().toLowerCase();

                if (input.equals("y")) {
                    // Check availability
                    boolean hasUnit = false;
                    if (flatType == FlatType.TWO_ROOM && handledProject.getNumberOf2Rooms() > 0) {
                        handledProject.setNumberOf2Rooms(handledProject.getNumberOf2Rooms() - 1);
                        hasUnit = true;
                    } else if (flatType == FlatType.THREE_ROOM && handledProject.getNumberOf3Rooms() > 0) {
                        handledProject.setNumberOf3Rooms(handledProject.getNumberOf3Rooms() - 1);
                        hasUnit = true;
                    }

                    if (!hasUnit) {
                        System.out.println("No units available for this flat type. Booking rejected.");
                        app.setStatus(AppStatus.UNSUCCESSFUL);
                    } else {
                        app.setStatus(AppStatus.BOOKED);
                        LocalDate bookingDate = LocalDate.now();
                        
                        Receipt receipt = new Receipt(
                            selected.getNRIC(),
                            selected.getName(),
                            selected.getAge(),
                            selected.isMarried(),
                            flatType,
                            handledProject.getName(),
                            handledProject.getNeighbourhood(),
                            bookingDate
                        );
                        receiptRepository.addReceipt(receipt);

                        System.out.println("Booking confirmed for applicant " + receipt.getApplicantName()); 
                    }

                } else if (input.equals("n")) {
                    app.setStatus(AppStatus.UNSUCCESSFUL);
                    System.out.println("Booking marked as unsuccessful.");
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    continue;
                }

                applicants.remove(selected);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        System.out.println("All pending bookings have been processed.");
    }



    public void viewBooking(Applicant applicant) {
        ProjectApp app = applicant.getProjectApp();
        if (app == null || app.getStatus() != AppStatus.BOOKED) {
            System.out.println("You have not made a booking.");
            return;
        }

        System.out.println("=== Booking Details ===");
        System.out.println("Flat Type: " + app.getFlatType());
        System.out.println("Project Name: " + app.getProject().getName());
        System.out.println("Neighbourhood: " + app.getProject().getNeighbourhood());
        System.out.println("Booking Status: " + app.getStatus());
        System.out.println("=======================");
    }
}

