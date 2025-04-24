package menu;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import entity.*;
import menu.*;
import repository.*;
import services.*;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
    	// instantiate all repositories
        ApplicantRepository applicantRepo = ApplicantRepository.getInstance();
        OfficerRepository officerRepo = OfficerRepository.getInstance();
        ManagerRepository managerRepo = ManagerRepository.getInstance();
        ProjectRepository projectRepo = ProjectRepository.getInstance();
        ReceiptRepository receiptRepo = ReceiptRepository.getInstance();
        EnquiryRepository enquiryRepo = EnquiryRepository.getInstance();
        JoinRequestRepository joinRequestRepo = JoinRequestRepository.getInstance();
        ProjectAppRepository projectAppRepo = ProjectAppRepository.getInstance();
        
        // check whether this is the first starting up or not, and import data
        File f = new File("save\\all-repositories.ser");

        try {
        	if (f.exists()) { 
        		// not the first starting up, therefore import from .ser files
        		// importFromSer() returns the superclass Repository
        		// therefore need to do downcasting accordingly

				FileInputStream fis = new FileInputStream("save\\all-repositories.ser");
				ObjectInputStream in = new ObjectInputStream(fis);
				
				applicantRepo = (ApplicantRepository) applicantRepo.importFromSer(in);
	            	officerRepo = (OfficerRepository) officerRepo.importFromSer(in);
	            	managerRepo = (ManagerRepository) managerRepo.importFromSer(in);
	            	projectRepo = (ProjectRepository) projectRepo.importFromSer(in);
	            	receiptRepo = (ReceiptRepository) receiptRepo.importFromSer(in);
	            	enquiryRepo = (EnquiryRepository) enquiryRepo.importFromSer(in);
	            	joinRequestRepo = (JoinRequestRepository) joinRequestRepo.importFromSer(in);
	            	projectAppRepo = (ProjectAppRepository) projectAppRepo.importFromSer(in);
            	
				fis.close();
				in.close();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        

        
        // set up entities
        List<Project> allProjects = projectRepo.getProjects();
        
        // set up all services
        AccountService accountService = new AccountService();
        ApplicantEnquiryService applicantEnquiryService = new ApplicantEnquiryService(applicantRepo, officerRepo, enquiryRepo);
        BookingService bookingService = new BookingService(
        		ReceiptRepository.getInstance(),
        		ProjectAppRepository.getInstance());
        JoinRequestService joinRequestService = new JoinRequestService(joinRequestRepo, projectRepo);
        ManageProjectAppService manageProjectAppService = new ManageProjectAppService(projectRepo);
        ManagerEnquiryService managerEnquiryService = new ManagerEnquiryService(enquiryRepo);
        OfficerEnquiryService officerEnquiryService = new OfficerEnquiryService(enquiryRepo);
        ProjectApplicationService projectAppService = new ProjectApplicationService(projectAppRepo, projectRepo);
        ProjectListingService projectListingService = new ProjectListingService(projectRepo);
        ReportService reportService = new ReportService(applicantRepo);
        ViewProjectService viewProjectService = new ViewProjectService(allProjects);

        // log in 
        LoginMenu loginMenu = new LoginMenu(applicantRepo, officerRepo, managerRepo, accountService);
        Scanner sc = new Scanner(System.in);
        User currentUser = loginMenu.login(sc);   
        
        // if current user is an officer: 
        if (currentUser instanceof Officer) {
            OfficerMainMenu officerMainMenu = new OfficerMainMenu(
                    (Officer) currentUser,
                    bookingService,
                    viewProjectService,
                    projectAppService,
                    joinRequestService,
                    applicantEnquiryService,
                    officerEnquiryService,
                    managerEnquiryService,
                    projectRepo, 
                    enquiryRepo,
                    receiptRepo,
                    applicantRepo,
                    loginMenu
                );
                officerMainMenu.officerMenu(sc);
        }
        
        // if current user is an applicant: 
        else if (currentUser instanceof Applicant) {
            ApplicantMainMenu applicantMainMenu = new ApplicantMainMenu(
                (Applicant) currentUser,
                bookingService,
                viewProjectService,
                projectAppService, 
                applicantEnquiryService,
                officerEnquiryService,
                managerEnquiryService,
                projectRepo, 
                enquiryRepo,
                receiptRepo,
                loginMenu
            );
            applicantMainMenu.applicantMenu(sc);
        }
        
        // if current user is a manager: 
        else if (currentUser instanceof Manager) {
        	ManagerMainMenu managerMainMenu = new ManagerMainMenu((Manager)currentUser,
			           viewProjectService, 
			           projectListingService, 
			           manageProjectAppService, 
			           joinRequestService,
			           reportService,
			           applicantEnquiryService, 
			    	   officerEnquiryService,
			    	   managerEnquiryService, 
			           projectRepo,
			           enquiryRepo,
			           loginMenu);
			managerMainMenu.managerMenu(sc); 
        }
        // if login failed, current user is null
        else {
        	System.out.println("Login failed. The program has terminated. ");
        }
        
        
        
        // save repositories to .ser file before exiting
        if (!f.exists()) {
        	f.getParentFile().mkdirs();
        	f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream("save\\all-repositories.ser");
        ObjectOutputStream out = new ObjectOutputStream(fos);

        applicantRepo.saveToSer(out);
	    	officerRepo.saveToSer(out);
	    	managerRepo.saveToSer(out);
	    	projectRepo.saveToSer(out);
	    	receiptRepo.saveToSer(out);
	    	enquiryRepo.saveToSer(out);
	    	joinRequestRepo.saveToSer(out);
	    	projectAppRepo.saveToSer(out);
	    	
	    	fos.close();
	    	out.close();
    }
}
