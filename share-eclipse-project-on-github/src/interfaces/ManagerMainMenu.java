package interfaces;
import entity.*; 
import repository.*; 
import services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ManagerMainMenu implements UserMainMenu{
	
	private Manager currentSessionManager;
    private ViewProjectService viewProjectService;
    private ProjectListingService projectListingService;
    private ManageProjectAppService manageProjectAppService;
    private ManagerEnquiryService managerEnquiryService;
    private JoinRequestService joinRequestService;
    private ReportService reportService; 
	
	public ManagerMainMenu (Manager manager, ViewProjectService viewProjectService,
							ProjectListingService projectListingService, 
							ManageProjectAppService manageProjectAppService,
							ManagerEnquiryService managerEnquiryService,
							JoinRequestService joinRequestService, ReportService reportService) {
		this.currentSessionManager = manager;
        this.viewProjectService = viewProjectService;
        this.projectListingService = projectListingService;
        this.manageProjectAppService = manageProjectAppService;
        this.managerEnquiryService = managerEnquiryService;
        this.joinRequestService = joinRequestService;   
        this.reportService = reportService; 
	}
	
	
	public void managerMenu (Scanner sc) {
		int choice = -1;
		System.out.println("You have logged in as a manager.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
        	System.out.println("\n=== Manager Main Menu ===");
            System.out.println("1. View Project Listings");
            System.out.println("2. Create New Project Listing");
            System.out.println("3. View and Process Join Requests");
            System.out.println("4. Process Project Applications");
            System.out.println("5. Process Withdrawal Requests");
            System.out.println("6. View and Reply to Project Enquiries");         
            System.out.println("7. Generate Report");
            System.out.println("8. Change Password");
            System.out.println("9. View Profile");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            
            
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewProjectService.viewProjectsAsManager(currentSessionManager);
                    break;
                case 2:
                	projectListingService.createNewProjectListing(currentSessionManager); 
                    break;
                case 3:
                	processJoinRequests(currentSessionManager, joinRequestService); 
                    break;
                case 4:
                	manageProjectAppService.processProjectApp(currentSessionManager);
                    break;
                case 5:
                	manageProjectAppService.processWithdrawal(currentSessionManager);
                    break;
                case 6:
                    processEnquiries(sc); 
                    break;
                case 7: 
                	reportService.generateFilteredApplicantReport(); 
                	break; 
                case 8: 
                	changePassword(sc);
                	break; 
                case 9: 
                	viewProfile();
                	break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
	}
	
	public void processJoinRequests(Manager manager, JoinRequestService joinRequestService) {
	    List<JoinRequest> requests = new ArrayList<>(manager.getJoinRequests());

	    if (requests.isEmpty()) {
	        System.out.println("No join requests to process.");
	        return;
	    }

	    for (JoinRequest request : requests) {
	        System.out.println("Officer: " + request.getOfficer().getName());
	        System.out.println("Project: " + request.getProject().getName());
	        System.out.println("Request Status: " + request.getStatus());
	        System.out.print("Approve this join request? (y/n): ");
	        Scanner sc = new Scanner(System.in); 
			String input = sc.nextLine().trim().toLowerCase();

	        if (input.equals("y")) {
	            joinRequestService.approveJoinRequest(manager, request);
	            System.out.println("Join request approved.");
	        } else {
	            joinRequestService.rejectJoinRequest(manager, request);
	            System.out.println("Join request rejected.");
	        }
	    }
	}
	
	//test
	public void processEnquiries(Scanner sc) {
	    managerEnquiryService.viewEnquiries(currentSessionManager, true);

	    System.out.print("\nDo you want to reply to an enquiry? (y/n): ");
	    String input = sc.nextLine().trim().toLowerCase();

	    if (input.equals("y")) {
	        System.out.print("Enter Enquiry ID to reply to: ");
	        String enquiryID = sc.nextLine();
	        System.out.print("Enter your response: ");
	        String response = sc.nextLine();
	        managerEnquiryService.replyEnquiry(currentSessionManager, enquiryID, response);
	    } else {
	        System.out.println("Returning to main menu.");
	    }	
	}
	
	
	public void changePassword(Scanner sc) {
	    UserMainMenu.super.changePassword(sc, currentSessionManager);
	    System.out.println("Password successfully changed.");
	}

	@Override
	public void viewProfile() {
		System.out.println("\n--- Manager Profile ---");
        System.out.println("Name: " + currentSessionManager.getName());
        System.out.println("NRIC: " + currentSessionManager.getNRIC());
        System.out.println("Age: " + currentSessionManager.getAge());
        System.out.println("Marital Status: " + (currentSessionManager.isMarried() ? "Married" : "Single"));
	}

}
