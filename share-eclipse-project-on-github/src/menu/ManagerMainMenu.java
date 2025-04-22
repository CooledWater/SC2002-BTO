package menu;
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
    
    private LoginMenu loginMenu;
	
	public ManagerMainMenu (Manager manager, ViewProjectService viewProjectService,
							ProjectListingService projectListingService, 
							ManageProjectAppService manageProjectAppService,
							ManagerEnquiryService managerEnquiryService,
							JoinRequestService joinRequestService, 
							ReportService reportService,
							LoginMenu loginMenu) {
		this.currentSessionManager = manager;
        this.viewProjectService = viewProjectService;
        this.projectListingService = projectListingService;
        this.manageProjectAppService = manageProjectAppService;
        this.managerEnquiryService = managerEnquiryService;
        this.joinRequestService = joinRequestService;   
        this.reportService = reportService; 
        this.loginMenu = loginMenu;
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
            System.out.println("10. Change project visibility");
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
                	changePassword(sc, currentSessionManager, loginMenu);
                	break; 
                case 9: 
                	viewProfile();
                	break;
                case 10:
                	manageProjectAppService.toggleVisibility(currentSessionManager);
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
	
	public void processEnquiries(Scanner sc) {
		// filter not necessary to save state for enquiry
		while (true) {
    		System.out.println("Do you want to see only enquiries regarding your active project? (y/n): ");
        	String filterByManaging = sc.nextLine().trim().toLowerCase();
        	if (filterByManaging.equals("y")) {
    			managerEnquiryService.viewEnquiries(currentSessionManager, true);
    			break;
    		} else if (filterByManaging.equals("n")) {
    			managerEnquiryService.viewEnquiries(currentSessionManager, false);
    			break;
    		}
            else {System.out.println("Invalid input. Please enter y or n. ");}
    	}
		
		while (true) {
			System.out.print("\nDo you want to reply to an enquiry? (y/n): ");
			String input = sc.nextLine().trim().toLowerCase();

		    if (input.equals("y")) {
		        System.out.print("Enter Enquiry ID to reply to: ");
		        String enquiryID = sc.nextLine();
		        System.out.print("Enter your response: ");
		        String response = sc.nextLine();
		        managerEnquiryService.replyEnquiry(currentSessionManager, enquiryID, response);
		        break;
		    } else if (input.equals("n")) {
		        System.out.println("Returning to main menu.");
		        break;
		    } else {System.out.println("Invalid input. Please enter y or n. ");}
		}
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
