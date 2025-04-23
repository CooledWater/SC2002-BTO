package services;

import entity.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import repository.ReceiptRepository;
import repository.ProjectAppRepository;

public class BookingService {
    private ReceiptRepository receiptRepository;
    private ProjectAppRepository projectAppRepository;

    public BookingService(ReceiptRepository receiptRepository,
    		ProjectAppRepository projectAppRepository) {
        this.receiptRepository = receiptRepository;
        this.projectAppRepository = projectAppRepository;
    }
    
    public void assistBookingFlat(Officer officer) {
        List<ProjectApp> pending = projectAppRepository.getProjectApps().stream()
                .filter(pa -> pa.getStatus() == AppStatus.SUCCESSFUL)
                .filter(pa -> pa.getProject().getOfficers().contains(officer))
                .collect(Collectors.toList());

            if (pending.isEmpty()) {
                System.out.println("No pending booking requests for any of your projects.");
                return;
            }

        Scanner sc = new Scanner(System.in);

        while (!pending.isEmpty()) {
            System.out.println("\nPending bookings:");
            for (int i = 0; i < pending.size(); i++) {
                ProjectApp pa = pending.get(i);
                System.out.printf("%d) %s applied for %s (%s) – flat type %s%n",
                    i+1,
                    pa.getApplicant().getNRIC(),
                    pa.getProject().getName(),
                    pa.getProject().getNeighbourhood(),
                    pa.getFlatType());
            }

            System.out.print("Enter number to review (or 0 to exit): ");
            int choice = Integer.parseInt(sc.nextLine());

                if (choice == 0) {
                    System.out.println("Exiting booking approval.");
                    return;
                }
                if (choice < 1 || choice > pending.size()) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                ProjectApp app = pending.get(choice - 1);
                Applicant a = app.getApplicant();
                FlatType ft = app.getFlatType();
                Project proj = app.getProject();

                System.out.printf("Approve booking for %s (%s)? (y/n): ", a.getNRIC(), ft);
                String input = sc.nextLine().trim().toLowerCase();

                if (input.equals("y")) {
                    // Check availability
                    boolean hasUnit = false;
                    if (ft == FlatType.TWO_ROOM && proj.getNumberOf2Rooms() > 0) {
                        proj.setNumberOf2Rooms(proj.getNumberOf2Rooms() - 1);
                        hasUnit = true;
                    } else if (ft == FlatType.THREE_ROOM && proj.getNumberOf3Rooms() > 0) {
                        proj.setNumberOf3Rooms(proj.getNumberOf3Rooms() - 1);
                        hasUnit = true;
                    }

                    if (!hasUnit) {
                        System.out.println("No units available for this flat type. Booking marked unsuccessful.");
                        app.setStatus(AppStatus.UNSUCCESSFUL);
                    } else {
                        app.setStatus(AppStatus.BOOKED);
                        LocalDate bookingDate = LocalDate.now();
                        
                        Receipt receipt = new Receipt(
                            a.getNRIC(),
                            a.getName(),
                            a.getAge(),
                            a.isMarried(),
                            ft,
                            proj.getName(),
                            proj.getNeighbourhood(),
                            bookingDate
                        );
                        receiptRepository.addReceipt(receipt);

                        System.out.println("Booking confirmed for applicant " + receipt.getApplicantName()); 
                    }

                } else if (input.equals("n")) {
                    app.setStatus(AppStatus.UNSUCCESSFUL);
                    System.out.println("Booking marked unsuccessful.");
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    continue;
                }

                pending.remove(app);

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

