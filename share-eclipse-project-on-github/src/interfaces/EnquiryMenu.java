package interfaces;
import entity.*;
import repository.*;
import services.*;
import java.util.*;

public class EnquiryMenu {
	private User currentSessionUser;
	private ProjectRepository projectRepo;
	private EnquiryRepository enquiryRepo;
	private ApplicantEnquiryService aeservice;
	public EnquiryMenu(User currentSessionUser,
					   ProjectRepository projectRepo,
					   EnquiryRepository enquiryRepo,
					   ApplicantEnquiryService aeservice) {
		this.currentSessionUser = currentSessionUser;
		this.projectRepo = projectRepo;
		this.enquiryRepo = enquiryRepo;
		this.aeservice = aeservice;
		
	}
	
	public void applicantEnquiryMenu(Scanner sc) {
		int choice = -1;
        
        System.out.println("You are now managing your enquiries as an applicant.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Enquiry Menu ===");
            System.out.println("0. Return to Main Menu");
            System.out.println("1. Submit a new enquiry");
            System.out.println("2. View and manage your past enquiries");
            System.out.print("Enter choice: ");
            
            while (true) {
            	try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == 0 || choice == 1 || choice == 2) {break;}
                    else {System.out.println("Invalid input. Please enter 0 or 1 or 2. ");}
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
            
            switch (choice) {
            case 0:
            	break;
            case 1:
            	System.out.println("Please enter the name of the project which you want to enquire: ");
            	String projectName = sc.nextLine().trim();
            	Project project = projectRepo.searchByName(projectName);
            	if (project == null) {
            		System.out.println("Project is not found. ");
            		break;
            	}
            	
            	System.out.println("Please enter your message: ");
            	String message = sc.nextLine();
            	// downcasting because we know for sure this is an applicant
            	aeservice.submitEnquiry((Applicant) currentSessionUser, project, message);
            	break;
            	
            case 2: 
            	aeservice.viewEnquiries((Applicant) currentSessionUser);
            	String subChoice = null;
            	while (true) {
            		System.out.println("\n\nDo you wish to edit or delete any of your enquiries? ");
                	System.out.println("Please enter y or n. ");
                	subChoice = sc.nextLine();
                	if (subChoice == "y" || subChoice == "n") {break;}
                    else {System.out.println("Invalid input. Please enter y or n. ");}
            	}
            	if (subChoice == "n") {break;}
            	
            	System.out.println("\n\nEnter the ID of the enquiry: ");
            	String enquireID = sc.nextLine();
            	Enquiry enquiry = null;
            	try {
            		enquiry = enquiryRepo.searchByID(enquireID).get();
            	} catch (NoSuchElementException e) {
            		System.out.println("No enquiries are found with this ID. ");
            		break;
            	}
            	
            	// choose to edit or delete or exit
            	System.out.println("\n\nEnter 0 to edit the enquiry");
            	System.out.println("\n\nEnter 1 to delete the enquiry");
            	System.out.println("\n\nEnter 2 to exit");
            	int choice012 = -1;
            	while (true) {
                	try {
                        choice012 = Integer.parseInt(sc.nextLine());
                        if (choice012 == 0 || choice012 == 1 || choice012 == 2) {break;}
                        else {System.out.println("Invalid input. Please enter 0 or 1 or 2. ");}  
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            	
            	if (choice012 == 0) {
            		// edit enquiry
            		System.out.println("Please enter the new message: ");
            		String newMessage = sc.nextLine();
            		aeservice.editEnquiry((Applicant)currentSessionUser, enquireID, newMessage);
            		break;
            	}
            	else if (choice012 == 1) {
            		aeservice.deleteEnquiry((Applicant) currentSessionUser, enquireID);
            		break;
            	}
            	else {
            		break;
            	}
            }
        }
        return;
	}
}
