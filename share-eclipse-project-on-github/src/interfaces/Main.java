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
        ProjectAppRepository projectAppRepo = new ProjectAppRepository();
        
        // instantiate other repositories
        ReceiptRepository receiptRepo = new ReceiptRepository();
        
        // check whether this is the first starting up or not, and import data
        File f = new File("save");
        if (f.exists()) {
        	try {
	        	applicantRepo.importFromSer();
	        	officerRepo.importFromSer();
	        	managerRepo.importFromSer();
	        	projectRepo.importFromSer();
				receiptRepo.importFromSer();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
        	applicantRepo.importFromCSV();
        	officerRepo.importFromCSV();
        	managerRepo.importFromCSV();
        	projectRepo.importFromCSV();
        }
        
        // set up entities
        List<Project> allProjects = projectRepo.getProjects();
        
        // set up services
        AccountService accountService = new AccountService();
        BookingService bookingService = new BookingService(receiptRepo);
        ViewProjectService viewProjectService = new ViewProjectService(allProjects);
        ProjectApplicationService projectAppService = new ProjectApplicationService(projectAppRepo);
        
        // log in 
        LoginInterface loginInterface = new LoginInterface(applicantRepo, officerRepo, managerRepo, accountService);
        Scanner sc = new Scanner(System.in);
        User currentUser = loginInterface.login(sc);

        if (currentUser instanceof entity.Applicant) {
            ApplicantMainMenu applicantMainMenu = new ApplicantMainMenu(
                (entity.Applicant) currentUser,
                accountService,
                bookingService,
                viewProjectService,
                projectAppService
            );
            applicantMainMenu.applicantMenu(sc);
        }
        // Later you can handle OfficerMainMenu or ManagerMainMenu here.
    }

}
