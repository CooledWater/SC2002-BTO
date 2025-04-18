package interfaces;

import entity.*;
import services.*;
import java.util.*;


public class OfficerMainMenu implements OfficerInterface {
	private Officer currentSessionOfficer;
    private AccountService accountService;
    private BookingService bookingService;
    private JoinRequestService joinRequestService;
    private ViewProjectService viewProjectService;

    public OfficerMainMenu(Officer officer, AccountService accountService, BookingService bookingService, JoinRequestService joinRequestService, ViewProjectService viewProjectService) {
        this.currentSessionOfficer = officer;
        this.accountService = accountService;
        this.bookingService = bookingService;
        this.joinRequestService = joinRequestService;
        this.viewProjectService = viewProjectService;
    }
    
	public void officerMenu(Scanner sc) {
        int choice = -1;
        
        System.out.println("You have logged in as an officer.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Officer Main Menu ===");
            System.out.println("\n== Applicant Services ==");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. View Booking");
            System.out.println("4. Manage your project application");
            System.out.println("5. Manage your enquiries as applicant");
            System.out.println();
            System.out.println("\n== Officer Services ==");
            System.out.println("6. Register as officer for a project");
            System.out.println("7. View officer registration status");
            System.out.println("8. View handling project details");
            System.out.println("9. View handling project enquiries");
            System.out.println("10. Reply enquiry");
            System.out.println("11. Update applicant status");
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
                changePassword(sc, currentSessionOfficer);
                break;
            case 3:
            	System.out.println("Loading booking details...");
                bookingService.viewBooking(currentSessionOfficer);
                break;
            case 4:
            	// calling project app menu
            	ProjectAppMenu projectAppMenu = new ProjectAppMenu(currentSessionOfficer, 
            			viewProjectService, projectApplicationService, bookingService);
            	projectAppMenu.projectAppMainMenu(sc);
                break;
            case 5: 
            	
            
            case 6:
            	// is there no way to view all projects as officer?
            	viewProjectService.viewProjectsAsOfficer(currentSessionOfficer);
            	System.out.println("Select a project");
            	joinRequestService.submitJoinRequest(currentSessionOfficer, ) // how to select project?
            	break;
            	
            case 6:
                System.out.println("Available Projects to Register For:");
                Project selectedProject = viewProjectService.viewProjectsAsOfficer(currentSessionOfficer, sc);
                if (selectedProject != null) {
                    joinRequestService.submitJoinRequest(currentSessionOfficer, selectedProject);
                    System.out.println("Join request submitted for project: " + selectedProject.getName());
                } else {
                    System.out.println("No project selected or action cancelled.");
                }
                break;
            
            case 7:
            	Status status = joinRequestService.getJoinRequestStatus(currentSessionOfficer);
            	System.out.println("Your registration status is: " + status);
            	break;
            	
            case 8:
            	break;
            	
            case 9:
            	break;
            	
            case 10:
            	break;
            	
            case 0:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
            }
        }
    }
	
	
	public void viewProfile() {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + currentSessionOfficer.getName());
        System.out.println("NRIC: " + currentSessionOfficer.getNRIC());
        System.out.println("Age: " + currentSessionOfficer.getAge());
        System.out.println("Marital Status: " + (currentSessionOfficer.isMarried() ? "Married" : "Single"));
        System.out.println("Handling Project: " + currentSessionOfficer.getHandlingProj().getName());
        System.out.println("Join Request: " + currentSessionOfficer.getJoinRequest().getStatus());
    }
}



/*
class OfficerApplication implements OfficerInterface {

    private final JoinRequest joinRequestService;
    private final ProjectListing projectListingService;
    private final OfficerEnquiry officerEnquiryService;
    private final Booking bookingService;
    private final Report reportService;
    private final Applicant applicantRepository;
    private final Officer officerRepository;
    private final Project projectRepository;

    public OfficerApplication(JoinRequest joinRequestService, ProjectListing projectListingService, OfficerEnquiry officerEnquiryService, Booking bookingService, Report reportService, Applicant applicantRepository, Officer officerRepository, Project projectRepository) {
        this.joinRequestService = joinRequestService;
        this.projectListingService = projectListingService;
        this.officerEnquiryService = officerEnquiryService;
        this.bookingService = bookingService;
        this.reportService = reportService;
        this.applicantRepository = applicantRepository;
        this.officerRepository = officerRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean registerForProject(int officerId, int projectId) {
        // Implementation logic for registration
        // Check for existing applicant status, other project officer status, etc.
        // Create a JoinRequest and return true if successful.
        return joinRequestService.createJoinRequest(officerId, projectId);
    }

    @Override
    public JoinRequest.JoinRequestStatus viewRegistrationStatus(int officerId, int projectId) {
        // Implementation to retrieve and return JoinRequest status.
        return joinRequestService.getJoinRequestStatus(officerId, projectId);
    }

    @Override
    public boolean applyForProject(int officerId, int projectId) {
        // Implementation to apply for a BTO project.
        // Check if officer is already handling the same project.
        // Create the ProjectApp and return true if successful.
        return projectListingService.applyForProject(officerId, projectId);
    }

    @Override
    public Project viewProjectDetails(int officerId, int projectId) {
        // Implementation to view project details.
        return projectListingService.getProjectDetails(projectId);
    }

    @Override
    public List<Enquiry> viewProjectEnquiries(int officerId, int projectId) {
        // Implementation to view project enquiries.
        return officerEnquiryService.getProjectEnquiries(officerId, projectId);
    }

    @Override
    public boolean replyToEnquiry(int officerId, int enquiryId, String reply) {
        // Implementation to reply to an enquiry.
        return officerEnquiryService.replyToEnquiry(officerId, enquiryId, reply);
    }

    @Override
    public boolean updateApplicantStatusToBooked(int officerId, int applicantId, int projectId) {
        // Implementation to update applicant status to booked.
        return bookingService.updateApplicantStatusToBooked(applicantId, projectId);
    }

    @Override
    public Receipt generateBookingReceipt(int officerId, int applicantId, int projectId) {
        // Implementation to generate booking receipt.
        return reportService.generateBookingReceipt(applicantId, projectId);
    }

    @Override
    public boolean updateFlatCounts(int projectId, FlatType flatType) {
        // Implementation to update flat counts.
        return bookingService.updateFlatCounts(projectId, flatType);
    }

    @Override
    public Applicant retrieveApplicantApplication(String applicantNric) {
        // Implementation to retrieve applicant application.
        return applicantRepository.findByNric(applicantNric);
    }

    @Override
    public boolean updateApplicantProfileWithFlatType(int applicantId, FlatType flatType) {
        // Implementation to update applicant profile with flat type.
        return applicantRepository.updateFlatType(applicantId, flatType);
    }
}
*/