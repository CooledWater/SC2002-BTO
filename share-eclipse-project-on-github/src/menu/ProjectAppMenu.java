package menu;

import entity.Applicant;
import entity.FlatType;
import entity.Project;
import entity.ProjectApp;
import entity.AppStatus;
import repository.ReceiptRepository;
import services.ViewProjectService;
import services.ProjectApplicationService;  
import java.util.List;
import java.util.Scanner;

public class ProjectAppMenu {
    private Applicant currentSessionApplicant;
    private ViewProjectService viewService;
    private ProjectApplicationService projectApplicationService; 
    private ReceiptRepository receiptRepository;        
    
    public ProjectAppMenu(Applicant applicant,
                          ViewProjectService viewService,
                          ProjectApplicationService projectApplicationService,
                          ReceiptRepository receiptRepository) {
        this.currentSessionApplicant = applicant;
        this.viewService = viewService;
        this.projectApplicationService = projectApplicationService;
        this.receiptRepository = receiptRepository;
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
            System.out.println("5. View Booking Receipt.");
            System.out.println("0. Return to Applicant Menu");
            System.out.print("Enter choice: ");
            
            while (true) {
            	try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 || choice == 0) {break;}
                    else {System.out.println("Invalid input. Please enter 1, 2, 3, 4 or 0. ");}
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
                ProjectApp existingApp = currentSessionApplicant.getProjectApp();
                if (existingApp != null && existingApp.getStatus() != AppStatus.UNSUCCESSFUL) {
                    System.out.println("You already have an ongoing application for project: " + existingApp.getProject().getName());
                    System.out.println("Please withdraw your existing application before applying for a new project.");
                    break;
                }
                
                System.out.print("Enter project name to apply: ");
                String projName = sc.nextLine().trim();
                
                // Fetch project by name
                Project selectedProject = viewService.getProjectByName(projName);
                if (selectedProject == null) {
                    System.out.println("Project not found. Please check the name and try again.");
                    break;
                }
                
                // Check Project Visibility (date checks) 
                if(!selectedProject.isVisible()) {
                	System.out.println("This project is not currently available for applications.");
                	break;
                }
                
                // Show flat type options from enum
                FlatType selectedFlatType = null;
                
                // If married, ask for flat type
                if (currentSessionApplicant.isMarried()) {
                    int flatChoice = -1;
                    while (true) {
                        System.out.println("Select flat type:");
                        System.out.println("2. TWO_ROOM");
                        System.out.println("3. THREE_ROOM");
                        System.out.println("0. Cancel Application");
                        System.out.print("Enter choice: ");
                        try {
                            flatChoice = Integer.parseInt(sc.nextLine());
                            if (flatChoice == 0) {
                                System.out.println("Returning to Project Application Menu...");
                                break; // break out of loop and case
                            } else if (flatChoice == 2 || flatChoice == 3) {
                                selectedFlatType = (flatChoice == 2) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter 2, 3, or 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    if (flatChoice == 0) break;
                } else {
                    // Applicant is single — inform them and ask if they want to proceed
                    System.out.println("As a single applicant, you are only eligible for TWO_ROOM flats.");
                    System.out.print("Would you still like to proceed with the application? (y/n): ");
                    String response = sc.nextLine().trim().toLowerCase();

                    if (response.equals("y")) {
                        selectedFlatType = FlatType.TWO_ROOM;
                    } else {
                        System.out.println("Application cancelled. Returning to previous menu...");
                        break;
                    }
                }

                // Call the updated service method with enum
                if (selectedFlatType != null) {
                    projectApplicationService.applyProject(currentSessionApplicant, selectedProject, selectedFlatType);
                }
                break;
            case 3:
                // only if they have a pending or successful app
                ProjectApp app = currentSessionApplicant.getProjectApp();
                if (app == null) {
                    System.out.println("No application to withdraw.");
                } else {
                    projectApplicationService.requestWithdrawal(currentSessionApplicant);
                }
                break;
            case 4:
                viewProjectDetails();
                break;
            case 5:
                receiptRepository.printReceiptsByNric(currentSessionApplicant.getNRIC());
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
    }
}

