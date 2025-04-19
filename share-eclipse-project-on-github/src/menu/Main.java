package menu;

import java.io.*;
import java.util.*;
import entity.*;
import menu.*;
import repository.*;
import services.*;

public class Main {

    public static void main(String[] args) {
    	// instantiate repositories with csv	
        ApplicantRepository applicantRepo = ApplicantRepository.getInstance();
        OfficerRepository officerRepo = OfficerRepository.getInstance();
        ManagerRepository managerRepo = ManagerRepository.getInstance();
        ProjectRepository projectRepo = ProjectRepository.getInstance();
        
        // instantiate other repositories
        ReceiptRepository receiptRepo = ReceiptRepository.getInstance();
        EnquiryRepository enquiryRepo = EnquiryRepository.getInstance();
        JoinRequestRepository joinRequestRepo = JoinRequestRepository.getInstance();
        ProjectAppRepository projectAppRepo = ProjectAppRepository.getInstance();
        
        // check whether this is the first starting up or not, and import data
        File f = new File("save");

        try {
        	if (f.exists()) { 
        		// not the first starting up, therefore import from .ser files
        		// importFromSer() returns the superclass Repository
        		// therefore need to do downcasting accordingly
            	applicantRepo = (ApplicantRepository) applicantRepo.importFromSer();
            	officerRepo = (OfficerRepository) officerRepo.importFromSer();
            	managerRepo = (ManagerRepository) managerRepo.importFromSer();
            	projectRepo = (ProjectRepository) projectRepo.importFromSer();
            	receiptRepo = (ReceiptRepository) receiptRepo.importFromSer();
            	enquiryRepo = (EnquiryRepository) enquiryRepo.importFromSer();
            	joinRequestRepo = (JoinRequestRepository) joinRequestRepo.importFromSer();
            	projectAppRepo = (ProjectAppRepository) projectAppRepo.importFromSer();
            } else {
            	// is the first starting up
            	// importFromCSV() is a method that directly modifies the applicant repository
            	// therefore no assignment is required
            	applicantRepo.importFromCSV();
            	officerRepo.importFromCSV();
            	managerRepo.importFromCSV();
            	projectRepo.importFromCSV(managerRepo, officerRepo);
            }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        
        // set up entities
        List<Project> allProjects = projectRepo.getProjects();
        
        // set up all services
        AccountService accountService = new AccountService();
        ApplicantEnquiryService applicantEnquiryService = new ApplicantEnquiryService(applicantRepo, enquiryRepo);
        BookingService bookingService = new BookingService(receiptRepo);
        JoinRequestService joinRequestService = new JoinRequestService(joinRequestRepo);
        ManageProjectAppService manageProjectAppService = new ManageProjectAppService(projectRepo);
        ManagerEnquiryService managerEnquiryService = new ManagerEnquiryService(enquiryRepo);
        OfficerEnquiryService officerEnquiryService = new OfficerEnquiryService(enquiryRepo);
        ProjectApplicationService projectAppService = new ProjectApplicationService(projectAppRepo);
        ProjectListingService projectListingService = new ProjectListingService(projectRepo);
        ReportService reportService = new ReportService(applicantRepo);
        ViewProjectService viewProjectService = new ViewProjectService(allProjects);

        
        // log in 
        LoginMenu loginMenu = new LoginMenu(applicantRepo, officerRepo, managerRepo, accountService);
        Scanner sc = new Scanner(System.in);
        User currentUser = loginMenu.login(sc);   
        
        // if current user is an officer: 
        if (currentUser instanceof entity.Officer) {
            OfficerMainMenu officerMainMenu = new OfficerMainMenu(
                    (entity.Officer) currentUser,
                    bookingService,
                    viewProjectService,
                    projectAppService,
                    joinRequestService,
                    applicantEnquiryService,
                    officerEnquiryService,
                    projectRepo, 
                    enquiryRepo,
                    receiptRepo
                );
                officerMainMenu.officerMenu(sc);
        }
        
        // if current user is an applicant: 
        else if (currentUser instanceof entity.Applicant) {
            ApplicantMainMenu applicantMainMenu = new ApplicantMainMenu(
                (entity.Applicant) currentUser,
                bookingService,
                viewProjectService,
                projectAppService, 
                applicantEnquiryService,
                officerEnquiryService,
                projectRepo, 
                enquiryRepo
            );
            applicantMainMenu.applicantMenu(sc);
        }
        
        // if current user is a manager: 
        else if (currentUser instanceof entity.Manager) {
        	ManagerMainMenu managerMainMenu = new ManagerMainMenu((entity.Manager)currentUser,
			           viewProjectService, 
			           projectListingService, 
			           manageProjectAppService, 
			           managerEnquiryService, 
			           joinRequestService, 
			           reportService);
			managerMainMenu.managerMenu(sc); 
        }
        // if login failed, current user is null
        else {
        	System.out.println("Login failed. The program has terminated. ");
        }
        
        
        
        // save repositories to .ser file before exiting
        applicantRepo.saveToSer();
    	officerRepo.saveToSer();
    	managerRepo.saveToSer();
    	projectRepo.saveToSer();
    	receiptRepo.saveToSer();
    	enquiryRepo.saveToSer();
    	joinRequestRepo.saveToSer();
    	projectAppRepo.saveToSer();
    }
}
