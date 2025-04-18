package interfaces;

import entity.Applicant;
import entity.FlatType;
import entity.Project;
import entity.ProjectApp;
import entity.AppStatus;
import services.BookingService;
import services.ViewProjectService;
import services.ProjectApplicationService;  
import java.util.List;
import java.util.Scanner;

public class ProjectAppMenu {
    private Applicant currentSessionApplicant;
    private ViewProjectService viewService;
    private ProjectApplicationService projectApplicationService; 
    private BookingService bookingService;        
    
    public ProjectAppMenu(Applicant applicant,
                          ViewProjectService viewService,
                          ProjectApplicationService projectApplicationService,
                          BookingService bookingService) {
        this.currentSessionApplicant = applicant;
        this.viewService = viewService;
        this.projectApplicationService = projectApplicationService;
        this.bookingService = bookingService;
    }

    public void projectAppMainMenu(Scanner sc) {
        int choice = -1;
        
        System.out.println("You are now managing your project application.");
        System.out.println("To choose an option, input the corresponding number.");
        System.out.println();

        while (choice != 0) {
            System.out.println("\n=== Project Application Menu ===");
            System.out.println("1. View Available Projects");
            System.out.println("2. Apply for Project");
            System.out.println("3. Withdraw Application");
            System.out.println("4. View Project Details");
            System.out.println("0. Return to Applicant Menu");
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
                // show all projects applicant can see
                viewService.viewProjectsAsApplicant(currentSessionApplicant);
                break;
            case 2:
                System.out.print("Enter project name to apply: ");
                String projName = sc.nextLine().trim();
                
                // Fetch project by name
                Project selectedProject = viewService.getProjectByName(projName);
                if (selectedProject == null) {
                    System.out.println("Project not found. Please check the name and try again.");
                    break;
                }
                
                // Show flat type options from enum
                FlatType selectedFlatType = null;
                while (selectedFlatType == null) {
                    System.out.println("Select flat type:");
                    System.out.println("1. TWO_ROOM");
                    System.out.println("2. THREE_ROOM");
                    System.out.print("Enter choice (1-2): ");
                    try {
                        int flatChoice = Integer.parseInt(sc.nextLine());
                        switch (flatChoice) {
                            case 1:
                                selectedFlatType = FlatType.TWO_ROOM;
                                break;
                            case 2:
                                selectedFlatType = FlatType.THREE_ROOM;
                                break;
                            default:
                                System.out.println("Invalid option. Try 1 or 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                    }
                }

                // Call the updated service method with enum
                projectApplicationService.applyProject(currentSessionApplicant, selectedProject, selectedFlatType);
                break;
            case 3:
                // only if they have a pending or successful app
                ProjectApp app = currentSessionApplicant.getProjectApp();
                if (app == null || app.getStatus() == AppStatus.BOOKED) {
                    System.out.println("No application to withdraw.");
                } else {
                    projectApplicationService.requestWithdrawal(currentSessionApplicant);
                }
                break;
            case 4:
                viewProjectDetails();
                break;
            case 0:
                System.out.println("Returning to Applicant Main Menu...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private void viewProjectDetails() {
        ProjectApp app = currentSessionApplicant.getProjectApp();
        if (app == null) {
            System.out.println("You have not applied for any project yet.");
            return;
        }

        System.out.println("\n--- Applied Project Details ---");
        System.out.println("Project Name   : " + app.getProject().getName());
        System.out.println("Neighbourhood  : " + app.getProject().getNeighbourhood());
        System.out.println("Flat Type      : " + app.getFlatType());
        System.out.println("Application Status: " + app.getStatus());

        //Logic to get officer name (temporary, not sure yet)
        String officerName = app.getProject().getOfficers().isEmpty()
            ? "None assigned"
            : app.getProject().getOfficers().get(0).getName();
        System.out.println("Assigned Officer: " + officerName);
        System.out.println("-------------------------------");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

}

