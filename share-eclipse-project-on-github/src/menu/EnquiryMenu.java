package menu;
import entity.*;
import repository.*;
import services.*;
import java.util.*;

public class EnquiryMenu {
	private User currentSessionUser;
	private ProjectRepository projectRepo;
	private EnquiryRepository enquiryRepo;
	private ApplicantEnquiryService aeservice;
	private OfficerEnquiryService oeservice;
	private ManagerEnquiryService meservice;
	
	public EnquiryMenu(User currentSessionUser,
					   ProjectRepository projectRepo,
					   EnquiryRepository enquiryRepo,
					   ApplicantEnquiryService aeservice,
					   OfficerEnquiryService oeservice,
					   ManagerEnquiryService meservice) {
		this.currentSessionUser = currentSessionUser;
		this.projectRepo = projectRepo;
		this.enquiryRepo = enquiryRepo;
		this.aeservice = aeservice;
		this.oeservice = oeservice;
		this.meservice = meservice;
		
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
            	System.out.println("Enquiry successfully submitted. ");
            	break;
            	
            case 2: 
            	boolean applicantEnquiries = aeservice.viewEnquiries((Applicant) currentSessionUser);
            	if (applicantEnquiries == false) {
            		System.out.println("You have no enquiries.");
            		break;
            	}
            	String subChoice = null;
            	while (true) {
            		System.out.println("\n\nDo you wish to edit or delete any of your enquiries? ");
                	System.out.println("Please enter y or n. ");
                	subChoice = sc.nextLine().trim().toLowerCase();
                	if (subChoice.equals("y") || subChoice.equals("n")) {break;}
                    else {System.out.println("Invalid input. Please enter y or n. ");}
            	}
            	if (subChoice.equals("n")) {break;}
            	// string comparison issue fixed
            	// DO NOT use == for string comparison, as it tests for object identity
            	// use .equals() to test for value equality
            	
            	System.out.println("\n\nEnter the ID of the enquiry: ");
            	String enquireID = sc.nextLine();
        		Enquiry enquiry = enquiryRepo.searchByID(enquireID);
            	if (enquiry == null) {break;}
            	
            	
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
	
	public void officerEnquiryMenu(Scanner sc) {
		int choice = -1;
        
        System.out.println("You are now managing your enquiries as an officer.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Enquiry Menu ===");
            System.out.println("0. Return to Main Menu");
            System.out.println("1. View and respond to project enquiries");
            System.out.print("Enter choice: ");
        
	        while (true) {
	        	try {
	                choice = Integer.parseInt(sc.nextLine());
	                if (choice == 0 || choice == 1) {break;}
	                else {System.out.println("Invalid input. Please enter 0 or 1. ");}
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter a valid number.");
	            }
	        }
	        
	        switch (choice) {
            case 0:
            	break;
            
            case 1:
            	boolean officerEnquiries = oeservice.viewEnquiries((Officer) currentSessionUser);
            	
            	if (((Officer) currentSessionUser).getHandlingProj() == null) {break;} // should be kept as .getHandlingProj()
            	else if (officerEnquiries == false) {
            		System.out.println("There are no enquiries for your project.");
            		break;
            	}
            	String subChoice = null;
            	while (true) {
            		System.out.println("\n\nDo you wish to respond to any enquiries? ");
                	System.out.println("Please enter y or n. ");
                	subChoice = sc.nextLine().trim().toLowerCase();
                	if (subChoice.equals("y") || subChoice.equals("n")) {break;}
                    else {System.out.println("Invalid input. Please enter y or n. ");}
            	}
            	if (subChoice.equals("n")) {break;}
            	
            	System.out.println("\n\nEnter the ID of the enquiry: ");
            	String enquireID = sc.nextLine();
        		Enquiry enquiry = enquiryRepo.searchByID(enquireID);
            	if (enquiry == null) {break;}
            	
            	// choose to respond to enquiry or exit
            	System.out.println("\n\nEnter 0 to respond to enquiry");
            	System.out.println("\n\nEnter 1 to exit");
            	int choice01 = -1;
            	while (true) {
                	try {
                        choice01 = Integer.parseInt(sc.nextLine());
                        if (choice01 == 0 || choice01 == 1) {break;}
                        else {System.out.println("Invalid input. Please enter 0 or 1. ");}  
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            	
            	if (choice01 == 0) {
            		// respond to enquiry
            		System.out.println("Please enter your response: ");
            		String newMessage = sc.nextLine();
            		oeservice.replyEnquiry((Officer)currentSessionUser, enquireID, newMessage);
            		break;
            	}
            	else {
            		break;
            	}
            }
        }
        return;
	}
	
	public void managerEnquiryMenu(Scanner sc) {
		int choice = -1;
        
        System.out.println("You are now managing your enquiries as a manager.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Enquiry Menu ===");
            System.out.println("0. Return to Main Menu");
            System.out.println("1. View and respond to project enquiries");
            System.out.print("Enter choice: ");
        
	        while (true) {
	        	try {
	                choice = Integer.parseInt(sc.nextLine());
	                if (choice == 0 || choice == 1) {break;}
	                else {System.out.println("Invalid input. Please enter 0 or 1. ");}
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter a valid number.");
	            }
	        }
	        
	        switch (choice) {
            case 0:
            	break;
            
            case 1:
            	while (true) { // filter not necessary to save state for enquiry
            		System.out.println("Do you want to see only enquiries regarding your active project? ");
            		System.out.println("Please enter y or n. ");
                	String filterByManaging = sc.nextLine().trim().toLowerCase();
                	if (filterByManaging.equals("y")) {
            			meservice.viewEnquiries((Manager)currentSessionUser, true);
            			break;
            		} else if (filterByManaging.equals("n")) {
            			meservice.viewEnquiries((Manager)currentSessionUser, false);
            			break;
            		}
                    else {System.out.println("Invalid input. Please enter y or n. ");}
            	}
            	
            	String subChoice = null;
            	while (true) {
            		System.out.println("\n\nDo you wish to respond to any enquiries? ");
                	System.out.println("Please enter y or n. ");
                	subChoice = sc.nextLine().trim().toLowerCase();
                	
                	if (subChoice.equals("y") || subChoice.equals("n")) {break;}
                    else {System.out.println("Invalid input. Please enter y or n. ");}
            	}
            	
            	if (subChoice.equals("n")) {break;}
            	
            	System.out.println("\n\nEnter the ID of the enquiry: ");
            	String enquireID = sc.nextLine();
        		Enquiry enquiry = enquiryRepo.searchByID(enquireID);
            	if (enquiry == null) {break;}
            	
            	// choose to respond to enquiry or exit
            	System.out.println("\n\nEnter 0 to respond to enquiry");
            	System.out.println("\n\nEnter 1 to exit");
            	int choice01 = -1;
            	while (true) {
                	try {
                        choice01 = Integer.parseInt(sc.nextLine());
                        if (choice01 == 0 || choice01 == 1) {break;}
                        else {System.out.println("Invalid input. Please enter 0 or 1. ");}  
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            	
            	if (choice01 == 0) {
            		// respond to enquiry
            		System.out.println("Please enter your response: ");
            		String newMessage = sc.nextLine();
            		meservice.replyEnquiry((Manager)currentSessionUser, enquireID, newMessage);
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
