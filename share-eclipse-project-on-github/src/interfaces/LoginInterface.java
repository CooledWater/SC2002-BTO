package interfaces;

import java.util.Scanner;

import entity.User;
import repository.ApplicantRepository;
import repository.OfficerRepository;
import repository.ManagerRepository;
import services.AccountService;

public class LoginInterface {
    private ApplicantRepository applicantRepository;
    private OfficerRepository officerRepository;
    private ManagerRepository managerRepository;
    private AccountService accountService;

    public LoginInterface(ApplicantRepository applicantRepository,
                          OfficerRepository officerRepository,
                          ManagerRepository managerRepository,
                          AccountService accountService) {
        this.applicantRepository = applicantRepository;
        this.officerRepository = officerRepository;
        this.managerRepository = managerRepository;
        this.accountService = accountService;
    }

    public User login(Scanner sc) {
        System.out.println("Login as: ");
        System.out.println("1. Applicant");
        System.out.println("2. Officer");
        System.out.println("3. Manager");
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        System.out.print("Enter NRIC: ");
        String nric = sc.nextLine();
        User user = null;

        switch (choice) {
            case 1:
                user = applicantRepository.searchByNRIC(nric);
                break;
            case 2:
                user = officerRepository.searchByNRIC(nric);
                break;
            case 3:
                user = managerRepository.searchByNRIC(nric);
                break;
            default:
                System.out.println("Invalid choice.");
                return null;
        }

        if (user == null) {
            System.out.println("User not found.");
            return null;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (accountService.checkPassword(user, password)) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}
