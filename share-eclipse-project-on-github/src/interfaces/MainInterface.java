package interfaces;

import java.util.List;
import entity.*;
import interfaces.*;
import repository.*;
import services.*;

public class MainInterface {

    public static void main(String[] args) {
        // Setup repositories and services
        ApplicantRepository applicantRepo = new ApplicantRepository();
        OfficerRepository officerRepo = new OfficerRepository();
        ManagerRepository managerRepo = new ManagerRepository();
        ReceiptRepository receiptRepo = new ReceiptRepository();
        ProjectRepository projectRepo = new ProjectRepository();
        
     // Setup services
        AccountService accountService = new AccountService();
        BookingService bookingService = new BookingService(receiptRepo);
        List<Project> allProjects = projectRepo.getProjects();
        ViewProjectService viewProjectService = new ViewProjectService(allProjects);
        ProjectApplicationService projectAppService = new ProjectApplicationService();

        // Login
        LoginInterface loginInterface = new LoginInterface(applicantRepo, officerRepo, managerRepo, accountService);
        User currentUser = loginInterface.login();

        if (currentUser instanceof entity.Applicant) {
            ApplicantMainMenu appMenu = new ApplicantMainMenu(
                (entity.Applicant) currentUser,
                accountService,
                bookingService,
                viewProjectService,
                projectApplicationService
            );
            appMenu.applicantMenu();
        }
        // Later you can handle OfficerMainMenu or ManagerMainMenu here.
    }

}
