package interfaces;

import entity.Applicant;
import services.*;
import java.util.Scanner;

public class ApplicantMainMenu {
    private Applicant currentSessionApplicant;
    private AccountService accountService;
    private BookingService bookingService;

    public ApplicantMainMenu(Applicant applicant, AccountService accountService, BookingService bookingService) {
        this.currentSessionApplicant = applicant;
        this.accountService = accountService;
        this.bookingService = bookingService;
    }

    public void applicantMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        
        System.out.println("You have logged in as an applicant.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Applicant Main Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. View Booking");
            System.out.println("4. Manage your project application");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        viewProfile();
                        break;
                    case 2:
                        changePassword(sc);
                        break;
                    case 3:
                        viewBooking();
                        break;
                    case 4:
                    	projectAppMenu(viewProjectService, projectApplicationService, bookingService);
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + currentSessionApplicant.getName());
        System.out.println("NRIC: " + currentSessionApplicant.getNRIC());
        System.out.println("Age: " + currentSessionApplicant.getAge());
        System.out.println("Marital Status: " + (currentSessionApplicant.isMarried() ? "Married" : "Single"));
    }

    private void changePassword(Scanner sc) {
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        accountService.changePassword(currentSessionApplicant, newPassword);
    }

    public void projectAppMenu(ViewProjectService viewProjectService, 
    		ProjectApplicationService projectApplicationService,
            BookingService bookingService) {
    	ProjectAppMenu projectAppMenu = new ProjectAppMenu(currentSessionApplicant, 
    			viewProjectService, projectApplicationService, bookingService);
    	projectAppMenu.projectAppMainMenu();
    }

    private void viewBooking() {
        bookingService.viewBooking(currentSessionApplicant);
        System.out.println("Loading booking details...");
    }
}