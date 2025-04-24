package menu;

import entity.Applicant;
import repository.*;
import services.*;
import java.util.Scanner;

public class ApplicantMainMenu implements UserMainMenu {
    private Applicant currentSessionApplicant;
    private BookingService bookingService;
    private ViewProjectService viewProjectService;
    private ProjectApplicationService projectApplicationService;
    private ApplicantEnquiryService applicantEnquiryService;
    private OfficerEnquiryService officerEnquiryService;
    
    private ProjectRepository projectRepo;
    private EnquiryRepository enquiryRepo;
    private ReceiptRepository receiptRepo;
    
    private LoginMenu loginMenu;

    public ApplicantMainMenu(Applicant applicant,
    						 BookingService bookingService, 
    						 ViewProjectService viewProjectService, 
    						 ProjectApplicationService projectApplicationService,
    						 ApplicantEnquiryService applicantEnquiryService,
    						 OfficerEnquiryService officerEnquiryService,
    						 ProjectRepository projectRepo,
    						 EnquiryRepository enquiryRepo,
    						 ReceiptRepository receiptRepository,
    						 LoginMenu loginMenu) {

        this.currentSessionApplicant = applicant;
        this.bookingService = bookingService;
        this.viewProjectService = viewProjectService;
        this.projectApplicationService = projectApplicationService;
        this.applicantEnquiryService = applicantEnquiryService;
        this.officerEnquiryService = officerEnquiryService;
        this.projectRepo = projectRepo;
        this.enquiryRepo = enquiryRepo;
        this.receiptRepo = receiptRepository;
        this.loginMenu = loginMenu;
    }

    public void applicantMenu(Scanner sc) {
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
            System.out.println("5. Manage your enquiries");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            
            while (true) {
            	try {
                    choice = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
            
            switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                changePassword(sc, currentSessionApplicant, loginMenu);
                break;
            case 3:
            	System.out.println("Loading booking details...");
                bookingService.viewBooking(currentSessionApplicant);
                break;
            case 4:
				// calling project app menu
            	ProjectAppMenu projectAppMenu = new ProjectAppMenu(currentSessionApplicant, 
            			viewProjectService, projectApplicationService, receiptRepo);
            	projectAppMenu.projectAppMainMenu(sc);
                break;
            case 5: 
            	EnquiryMenu enquiryMenu = new EnquiryMenu(currentSessionApplicant, projectRepo, enquiryRepo, applicantEnquiryService, officerEnquiryService);
            	enquiryMenu.applicantEnquiryMenu(sc);
            	break;
            case 0:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    @Override
    public void viewProfile() {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + currentSessionApplicant.getName());
        System.out.println("NRIC: " + currentSessionApplicant.getNRIC());
        System.out.println("Age: " + currentSessionApplicant.getAge());
        System.out.println("Marital Status: " + (currentSessionApplicant.isMarried() ? "Married" : "Single"));
    }

}