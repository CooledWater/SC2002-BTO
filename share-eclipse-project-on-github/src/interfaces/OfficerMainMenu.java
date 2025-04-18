package interfaces;

import entity.*;
import repository.*;
import services.*;

import java.util.Scanner;
import java.util.List;

public class OfficerMainMenu {
    private Officer currentOfficer;
    private JoinRequestService joinRequestService;
    private ProjectRepository projectRepository;

    public OfficerMainMenu(Officer officer, JoinRequestService joinRequestService,
    		ProjectRepository projectRepository) {
        this.currentOfficer = officer;
        this.joinRequestService = joinRequestService;
        this.projectRepository = projectRepository;
    }

    public void officerMenu(Officer officer) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n=== Officer Main Menu ===");
            System.out.println("1. Register to Handle Project");
            System.out.println("2. View Registration Status");
            System.out.println("3. View Project Details");
            System.out.println("4. Manage Project Enquiries");
            System.out.println("5. Assist in Flat Booking");
            System.out.println("6. Generate Booking Receipt");
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
                    handleProjectRegistration(officer, sc);
                    break;
                case 2:
                	viewJoinRequestStatus(officer);
                    break;
                case 3:
                    officerService.viewHandledProjectDetails(currentOfficer);
                    break;
                case 4:
                    officerService.manageEnquiries(currentOfficer);
                    break;
                case 5:
                    officerService.assistFlatBooking(currentOfficer);
                    break;
                case 6:
                    officerService.generateReceipt(currentOfficer);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    

    private void handleProjectRegistration(Officer officer, Scanner sc) {
        List<Project> allProjects = projectRepository.getAllProjects();
        System.out.println("\n=== Available Projects ===");

        for (int i = 0; i < allProjects.size(); i++) {
            Project p = allProjects.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, p.getName(), p.getNeighbourhood());
        }

        System.out.print("Enter the number of the project to register for (or 0 to cancel): ");
        int selection = -1;
        try {
            selection = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (selection == 0) {
            System.out.println("Cancelled project registration.");
            return;
        }

        if (selection < 1 || selection > allProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Project selectedProject = allProjects.get(selection - 1);
        joinRequestService.submitJoinRequest(officer, selectedProject);
    }

    private void viewJoinRequestStatus(Officer officer) {
        if (officer.getJoinRequest() == null) {
            System.out.println("You have not submitted a join request yet.");
        } else {
            System.out.println("Your join request status: " + joinRequestService.getJoinRequestStatus(officer));
        }
    }

}