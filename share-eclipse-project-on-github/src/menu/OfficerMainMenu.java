package menu;

import entity.*;
import repository.*;
import services.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class OfficerMainMenu implements UserMainMenu {
    private Officer currentOfficer;
    private BookingService bookingService;
    private ViewProjectService viewProjectService;
    private ProjectApplicationService projectApplicationService;
    private ApplicantEnquiryService applicantEnquiryService;
    
    private JoinRequestService joinRequestService;
    private OfficerEnquiryService officerEnquiryService;
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
    		ProjectRepository projectRepository, 
    		EnquiryRepository enquiryRepository, 
    		ReceiptRepository receiptRepository,
    		ApplicantRepository applicantRepository,
    		LoginMenu loginMenu) {
        this.currentOfficer = officer;
        this.bookingService = bookingService;
        this.viewProjectService = viewProjectService;
        this.projectApplicationService = projectApplicationService;
        this.joinRequestService = joinRequestService;
        this.applicantEnquiryService = applicantEnquiryService;
        this.officerEnquiryService = officerEnquiryService;
        this.projectRepository = projectRepository;
        this.enquiryRepository = enquiryRepository;
        this.receiptRepository = receiptRepository;
        this.applicantRepository = applicantRepository;
        this.loginMenu = loginMenu;
    }

    public void officerMenu(Scanner sc) {
        int choice = -1;
                
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
            System.out.println("8. View the project you are handling");
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
                    changePassword(sc, currentOfficer, loginMenu);
                    break;
            	case 3:
                	System.out.println("Loading booking details...");
                    bookingService.viewBooking(currentOfficer);
                    break;
            	case 4:
    				// calling project app menu
                	ProjectAppMenu projectAppMenu = new ProjectAppMenu(currentOfficer, 
                			viewProjectService, projectApplicationService, bookingService);
                	projectAppMenu.projectAppMainMenu(sc);
                    break;
                case 5: 
                	EnquiryMenu enquiryApplicantMenu = new EnquiryMenu(currentOfficer, projectRepository, enquiryRepository, applicantEnquiryService, officerEnquiryService);
                	enquiryApplicantMenu.applicantEnquiryMenu(sc);
                	break;
            	case 6:
                    handleProjectRegistration(currentOfficer, sc);
                    break;
                case 7:
                	viewJoinRequestStatus(currentOfficer);
                    break;               
                case 8:
                	viewProjectService.viewProjectsAsOfficer(currentOfficer);
                    break;
                case 9:
                	EnquiryMenu enquiryOfficerMenu = new EnquiryMenu(currentOfficer, projectRepository, enquiryRepository, applicantEnquiryService, officerEnquiryService);
                	enquiryOfficerMenu.officerEnquiryMenu(sc);
                    break;
                case 10:
                    bookingService.assistBookingFlat(currentOfficer);
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
    

    private void handleProjectRegistration(Officer officer, Scanner sc) {
        List<Project> allProjects = projectRepository.getProjects();
        System.out.println("\n=== Available Projects ===");

        for (int i = 0; i < allProjects.size(); i++) {
            Project p = allProjects.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, p.getName(), p.getNeighbourhood());
        }

        System.out.print("Enter the number of the project to register for (or 0 to cancel): ");
        int selection = -1;
        try {
            selection = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (selection == 0) {
            System.out.println("Cancelled project registration.");
            return;
        }

        if (selection < 1 || selection > allProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Project selectedProject = allProjects.get(selection - 1);
        joinRequestService.submitJoinRequest(officer, selectedProject);
    }

    private void viewJoinRequestStatus(Officer officer) {
        if (officer.getJoinRequest() == null) {
            System.out.println("You have not submitted a join request yet.");
        } else {
            System.out.println("Your join request status: " + joinRequestService.getJoinRequestStatus(officer));
        }
    }
    
    public void viewProfile() {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + currentOfficer.getName());
        System.out.println("NRIC: " + currentOfficer.getNRIC());
        System.out.println("Age: " + currentOfficer.getAge());
        System.out.println("Marital Status: " + (currentOfficer.isMarried() ? "Married" : "Single"));
        
        if (currentOfficer.getHandlingProj() != null) {
            System.out.println("Handling Project: " + currentOfficer.getHandlingProj().getName());
        } else {
            System.out.println("Handling Project: None");
        }

        if (currentOfficer.getJoinRequest() != null) {
            System.out.println("Join Request Status: " + currentOfficer.getJoinRequest().getStatus());
        } else {
            System.out.println("Join Request Status: None");
        }
    }

}