package menu;

import entity.*;
import repository.*;
import services.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class OfficerMainMenu implements UserMainMenu {
    private Officer currentSessionOfficer;
    private BookingService bookingService;
    private ViewProjectService viewProjectService;
    private ProjectApplicationService projectApplicationService;
    private ApplicantEnquiryService applicantEnquiryService;
    
    private JoinRequestService joinRequestService;
    private OfficerEnquiryService officerEnquiryService;
    private ManagerEnquiryService managerEnquiryService;
    private ProjectRepository projectRepository;
    private EnquiryRepository enquiryRepository;
    private ReceiptRepository receiptRepository;
    private ApplicantRepository applicantRepository;
    // private OfficerEnquiryService officerEnquiryService; put in officerService
    
    private LoginMenu loginMenu;
    
  
    public OfficerMainMenu(Officer officer, 
    		BookingService bookingService, 
    		ViewProjectService viewProjectService, 
    		ProjectApplicationService projectApplicationService, 
    		JoinRequestService joinRequestService, 
    		ApplicantEnquiryService applicantEnquiryService, 
    		OfficerEnquiryService officerEnquiryService,
    		ManagerEnquiryService managerEnquiryService,
    		ProjectRepository projectRepository, 
    		EnquiryRepository enquiryRepository, 
    		ReceiptRepository receiptRepository,
    		ApplicantRepository applicantRepository,
    		LoginMenu loginMenu) {
        this.currentSessionOfficer = officer;
        this.bookingService = bookingService;
        this.viewProjectService = viewProjectService;
        this.projectApplicationService = projectApplicationService;
        this.joinRequestService = joinRequestService;
        this.applicantEnquiryService = applicantEnquiryService;
        this.officerEnquiryService = officerEnquiryService;
        this.managerEnquiryService = managerEnquiryService;
        this.projectRepository = projectRepository;
        this.enquiryRepository = enquiryRepository;
        this.receiptRepository = receiptRepository;
        this.applicantRepository = applicantRepository;
        this.loginMenu = loginMenu;
    }

    public void officerMenu(Scanner sc) {
        int choice = -1;
        projectRepository.updateOfficerHandlingProj(currentSessionOfficer);
        
        System.out.println("You have logged in as an officer.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();
        
        while (choice != 0) {
        	System.out.println("\n=== Officer Main Menu ===");
        	
        	System.out.println("\n== Applicant Functions ==");
            System.out.println("1. View profile");
            System.out.println("2. Change password");
            System.out.println("3. View booking");
            System.out.println("4. Manage your project application");
            System.out.println("5. Manage your enquiries");     	
            System.out.println();
            System.out.println("\n== Officer Functions ==");
            System.out.println("6. Register to handle a project");
            System.out.println("7. View registration status");
            System.out.println("8. View projects you are a part of");
            System.out.println("9. Manage project enquiries");
            System.out.println("10. Assist in flat booking");
            System.out.println("11. Generate booking receipt");
            System.out.println("0. Logout");
            System.out.println();
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
            	case 1:
            		viewProfile();
                    break;
            	case 2:
                    changePassword(sc, currentSessionOfficer, loginMenu);
                    break;
            	case 3:
                	System.out.println("Loading booking details...");
                    bookingService.viewBooking(currentSessionOfficer);
                    break;
            	case 4:
    				// calling project app menu
                	ProjectAppMenu projectAppMenu = new ProjectAppMenu(currentSessionOfficer, 
                			viewProjectService, projectApplicationService, receiptRepository);
                	projectAppMenu.projectAppMainMenu(sc);
                    break;
                case 5: 
                	EnquiryMenu enquiryApplicantMenu = new EnquiryMenu(currentSessionOfficer, projectRepository, enquiryRepository, applicantEnquiryService, officerEnquiryService, managerEnquiryService);
                	enquiryApplicantMenu.applicantEnquiryMenu(sc);
                	break;
            	case 6:
                    joinRequestService.handleProjectRegistration(currentSessionOfficer, sc);
                    break;
                case 7:
                	joinRequestService.viewJoinRequestStatus(currentSessionOfficer);
                    break;               
                case 8:
                	viewProjectService.viewProjectsAsOfficer(currentSessionOfficer);
                    break;
                case 9:
                	EnquiryMenu enquiryOfficerMenu = new EnquiryMenu(currentSessionOfficer, projectRepository, enquiryRepository, applicantEnquiryService, officerEnquiryService, managerEnquiryService);
                	enquiryOfficerMenu.officerEnquiryMenu(sc);
                    break;
                case 10:
                    bookingService.assistBookingFlat(currentSessionOfficer);
                    break;
                case 11:
                	System.out.println("Enter applicant NRIC: ");
                	String applicantNRIC = sc.nextLine();
                    receiptRepository.printReceiptsByNric(applicantNRIC);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    
    @Override
    public void viewProfile() {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + currentSessionOfficer.getName());
        System.out.println("NRIC: " + currentSessionOfficer.getNRIC());
        System.out.println("Age: " + currentSessionOfficer.getAge());
        System.out.println("Marital Status: " + (currentSessionOfficer.isMarried() ? "Married" : "Single"));
        
        if (currentSessionOfficer.getHandlingProj() != null) {
            System.out.println("Handling Project: " + currentSessionOfficer.getHandlingProj().getName()); // should be kept as .getHandlingProj()
        } else {
            System.out.println("Handling Project: None");
        }

        if (currentSessionOfficer.getJoinRequest() != null) {
            System.out.println("Join Request Status: " + currentSessionOfficer.getJoinRequest().getStatus());
        } else {
            System.out.println("Join Request Status: None");
        }
    }

}