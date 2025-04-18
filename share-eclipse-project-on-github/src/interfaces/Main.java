package interfaces;

import java.io.*;
import java.util.*;
import entity.*;
import interfaces.*;
import repository.*;
import services.*;

public class Main {

    public static void main(String[] args) {
    	// instantiate repositories with csv	
        ApplicantRepository applicantRepo = new ApplicantRepository();
        OfficerRepository officerRepo = new OfficerRepository();
        ManagerRepository managerRepo = new ManagerRepository();
        ProjectRepository projectRepo = new ProjectRepository();
        
        // instantiate other repositories
        ReceiptRepository receiptRepo = new ReceiptRepository();
        EnquiryRepository enquiryRepo = new EnquiryRepository();
        JoinRequestRepository joinRequestRepo = new JoinRequestRepository();
        ProjectAppRepository projectAppRepo = new ProjectAppRepository();
        
        // check whether this is the first starting up or not, and import data
        File f = new File("save");

        try {
        	if (f.exists()) { // not the first starting up
            	applicantRepo.importFromSer();
            	officerRepo.importFromSer();
            	managerRepo.importFromSer();
            	projectRepo.importFromSer();
            	receiptRepo.importFromSer();
            	enquiryRepo.importFromSer();
            	joinRequestRepo.importFromSer();
            	projectAppRepo.importFromSer();
            } else {// is the first starting up
            	applicantRepo.importFromCSV();
            	officerRepo.importFromCSV();
            	managerRepo.importFromCSV();
            	projectRepo.importFromCSV();
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
        ProjectListingService projectListingService = new ProjectListingService();
        ReportService reportService = new ReportService(applicantRepo);
        ViewProjectService viewProjectService = new ViewProjectService(allProjects);

        
        // log in 
        LoginInterface loginInterface = new LoginInterface(applicantRepo, officerRepo, managerRepo, accountService);
        Scanner sc = new Scanner(System.in);
        User currentUser = loginInterface.login(sc);
        
        // if current user is an applicant: 
        if (currentUser instanceof entity.Applicant) {
            ApplicantMainMenu applicantMainMenu = new ApplicantMainMenu(
                (entity.Applicant) currentUser,
                bookingService,
                viewProjectService,
                projectAppService, 
                applicantEnquiryService, 
                projectRepo, 
                enquiryRepo

            );
            applicantMainMenu.applicantMenu(sc);
        }
        // if current user is an officer: 
        
        // if current user is a manager: 
        
        
        
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
