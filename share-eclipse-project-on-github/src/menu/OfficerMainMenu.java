package menu;

import entity.*;
import repository.*;
import services.*;

import java.util.Scanner;
import java.util.List;

public class OfficerMainMenu implements UserMainMenu {
    private Officer currentOfficer;
    private BookingService bookingService;
    private ViewProjectService viewProjectService;
    private ProjectApplicationService projectApplicationService;
    
    private JoinRequestService joinRequestService;
    private ProjectRepository projectRepository;   
    // private OfficerEnquiryService officerEnquiryService; put in officerService
    
  
    public OfficerMainMenu(Officer officer, BookingService bookingService, ViewProjectService viewProjectService, 
    		ProjectApplicationService projectApplicationService, JoinRequestService joinRequestService,
    		ProjectRepository projectRepository) {
        this.currentOfficer = officer;
        this.bookingService = bookingService;
        this.viewProjectService = viewProjectService;
        this.projectApplicationService = projectApplicationService;
        this.joinRequestService = joinRequestService;
        this.projectRepository = projectRepository;
        
    }

    public void officerMenu(Officer officer) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
        	System.out.println("\n=== Officer Main Menu ===");
        	
        	System.out.println("\n== Applicant Functions ==");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. View Booking");
            System.out.println("4. Manage your project application");
            System.out.println("5. Manage your enquiries");     	
            System.out.println();
            System.out.println("\n== Officer Functions ==");
            System.out.println("6. Register to Handle Project");
            System.out.println("7. View Registration Status");
            System.out.println("8. View Project Details");
            System.out.println("9. Manage Project Enquiries");
            System.out.println("10. Assist in Flat Booking");
            System.out.println("11. Generate Booking Receipt");
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
                    changePassword(sc, currentOfficer);
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

            
            	case 6:
                    handleProjectRegistration(officer, sc);
                    break;
                case 7:
                	viewJoinRequestStatus(officer);
                    break;
                case 8:
                	currentOfficer.getHandlingProj(); // maybe method in Project to print all details of the project
                    officerService.viewHandledProjectDetails(currentOfficer);
                    break;
                case 9:
                    officerService.manageEnquiries(currentOfficer);
                    break;
                case 10:
                    officerService.assistFlatBooking(currentOfficer);
                    break;
                case 11:
                    officerService.generateReceipt(currentOfficer);
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