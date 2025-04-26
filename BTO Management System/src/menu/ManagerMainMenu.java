package menu;
import entity.*;
import entity.JoinRequest.Status;
import repository.*; 
import services.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ManagerMainMenu implements UserMainMenu{
	
	private Manager currentSessionManager;
    private ViewProjectService viewProjectService;
    private ProjectListingService projectListingService;
    private ManageProjectAppService manageProjectAppService;
    private ManagerEnquiryService managerEnquiryService;
    private ApplicantEnquiryService applicantEnquiryService;
    private OfficerEnquiryService officerEnquiryService;
    private JoinRequestService joinRequestService;
    private ReportService reportService;
    private ProjectRepository projectRepo; 
    private EnquiryRepository enquiryRepo;
    private LoginMenu loginMenu;
	
	public ManagerMainMenu (Manager manager, ViewProjectService viewProjectService,
							ProjectListingService projectListingService, 
							ManageProjectAppService manageProjectAppService,
							JoinRequestService joinRequestService, 
							ReportService reportService,
							ApplicantEnquiryService applicantEnquiryService, 
				    		OfficerEnquiryService officerEnquiryService,
				    		ManagerEnquiryService managerEnquiryService,
							ProjectRepository projectRepo,
							EnquiryRepository enquiryRepo,
							LoginMenu loginMenu) {

		this.currentSessionManager = manager;
        this.viewProjectService = viewProjectService;
        this.projectListingService = projectListingService;
        this.manageProjectAppService = manageProjectAppService;
        this.managerEnquiryService = managerEnquiryService;
        this.applicantEnquiryService = applicantEnquiryService;
        this.officerEnquiryService = officerEnquiryService;
        this.joinRequestService = joinRequestService;
        this.reportService = reportService;
        this.projectRepo = projectRepo;
        this.enquiryRepo = enquiryRepo;
        this.loginMenu = loginMenu;
        this.enquiryRepo = enquiryRepo;
	}
	
	
	public void managerMenu (Scanner sc) throws ParseException {
		int choice = -1;
		projectRepo.updateManagerHandlingProj(currentSessionManager);

		System.out.println("You have logged in as a manager.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
        	System.out.println("\n=== Manager Main Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Process Join Requests");
            System.out.println("4. Process Project Applications");
            System.out.println("5. Process Withdrawal Requests");
            System.out.println("6. Process Enquiries");         
            System.out.println("7. Generate Report");
            System.out.println("8. View Project Listings");
            System.out.println("9. Create New Project Listing");
            System.out.println("10. Change Project Visibility");
            System.out.println("11. Edit Project");
            System.out.println("12. Delete Project");
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
            		viewProfile();
                    break;
                case 2:
            		changePassword(sc, currentSessionManager, loginMenu);                		
                    break;
                case 3:
            		joinRequestService.processJoinRequests(currentSessionManager); 
                    break;
                case 4:
            		manageProjectAppService.processProjectApp(currentSessionManager);
                    break;
                case 5:
            		manageProjectAppService.processWithdrawal(currentSessionManager);
                    break;
                case 6:
                	EnquiryMenu enquiryManagerMenu = new EnquiryMenu(currentSessionManager, projectRepo, enquiryRepo, applicantEnquiryService, officerEnquiryService, managerEnquiryService);
                	enquiryManagerMenu.managerEnquiryMenu(sc); 
                    break;
                case 7: 
                	reportService.generateFilteredApplicantReport(); 
                	break; 
                case 8: 
                	viewProjectService.viewProjectsAsManager(currentSessionManager);
                	break; 
                case 9: 
                	projectListingService.createNewProjectListing(currentSessionManager); 
                	break;
                case 10:
            		manageProjectAppService.toggleVisibility(currentSessionManager);
            		break;
                case 11: 
            		projectListingService.editProjectListing(currentSessionManager, sc);
            		break;
                case 12: 
            		projectListingService.deleteProjectListing(currentSessionManager, sc);
                case 0:
	                System.out.println("Logging out...");
	                break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
	}
	public void processJoinRequests(Manager manager) {
	    List<JoinRequest> requests = manager.getJoinRequests();
	    List<JoinRequest> pendingRequests = requests.stream()
	    											   .filter(n -> n.getStatus().equals(Status.PENDING))
	    											   .collect(Collectors.toList());
	    if (pendingRequests.isEmpty()) {
	        System.out.println("No pending join requests to process.");
	        return;
	    }

	    Iterator<JoinRequest> it = pendingRequests.iterator();
	    while(it.hasNext()) {
	    		JoinRequest request = it.next();
	        System.out.println(request.toString());
	        System.out.print("Approve this join request? (y/n): ");
	        Scanner sc = new Scanner(System.in); 
			String input = sc.nextLine().trim().toLowerCase();

	        if (input.equals("y")) {
	            joinRequestService.approveJoinRequest(manager, request);
	            System.out.println("Join request approved.");
	            System.out.println(request.getProject());
	            System.out.format("%d out of 10 officer slots are occupied. %n%n", request.getProject().getNumberOfOfficers());
	        } else {
	            joinRequestService.rejectJoinRequest(manager, request);
	            System.out.println("Join request rejected.");
	        }
	    }
	}
	
	public void processEnquiries(Scanner sc, EnquiryRepository enquiryRepo) {
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
		    	while (true) {
			        System.out.print("Enter Enquiry ID to reply to (enter 0 to cancel): ");
			        String enquiryID = sc.nextLine();
			        
			        if (enquiryID.equalsIgnoreCase("0") ) {
			        	System.out.println("Returning to Enquiries Menu.");
			        	break;
			        }
			        
			        Enquiry enquiry = enquiryRepo.searchByID(enquiryID);
			        
			        if (enquiry == null) {			    
			        	continue; 
			        }
			        
			        System.out.print("Enter your response: ");
			        String response = sc.nextLine();
			        managerEnquiryService.replyEnquiry(currentSessionManager, enquiryID, response);
		    	}
		        continue;
		    } else if (input.equals("n")) {
		        System.out.println("Returning to main menu.");
		        break;
		    } else {System.out.println("Invalid input. Please enter y or n. ");}
		}
	}

	@Override
	public void viewProfile() {
		System.out.println("\n--- Profile ---");
		System.out.println(currentSessionManager);
	}
	
	
}
