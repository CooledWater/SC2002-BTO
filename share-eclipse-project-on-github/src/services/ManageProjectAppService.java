package services;
import java.util.Scanner;

import repository.*; 
import entity.*;

import java.util.*;

public class ManageProjectAppService {
    private ProjectRepository projectRepo;
    private ProjectAppRepository projectAppRepo;
    private Scanner sc = new Scanner(System.in);

    public ManageProjectAppService(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
    }

    // process application managed by manager
    
    public void processProjectApp(Manager manager) {
        List<Project> projects = projectRepo.getProjectsByManager(manager);

        for (Project project : projects) {
            List<ProjectApp> apps = project.getProjectApps();
            if (apps == null || apps.isEmpty()) {
            	System.out.println("You have no pending applications.");
            	continue;
            }

            for (ProjectApp app : apps) {
                if (app.getStatus() == AppStatus.PENDING) {
                	System.out.println("\n\n================================");
                    System.out.println("Project: " + project.getName());
                    System.out.println("Applicant: " + app.getApplicant().getName());               
                    System.out.println("Flat Type: " + app.getFlatType()); 
                    System.out.print("Approve this application? (y/n): ");
                    String input = sc.nextLine().trim().toLowerCase();

                    if (input.equals("y")) {
                        //approve                             
                            app.setStatus(AppStatus.SUCCESSFUL);
                            System.out.println("Application approved\n");                    
                    } else {
                        app.setStatus(AppStatus.UNSUCCESSFUL);
                        System.out.println("Application rejected\n");
                    }
                }
            }
        }
    }

    public void processWithdrawal(Manager manager) {
        List<Project> projects = projectRepo.getProjectsByManager(manager);

        for (Project project : projects) {
            List<ProjectApp> apps = project.getProjectApps();
            if (apps == null || apps.isEmpty()) {
            	System.out.println("You have no pending withdrawals.");
            	continue;
            }


            Iterator<ProjectApp> iterator = apps.iterator();
            while (iterator.hasNext()) {
                ProjectApp app = iterator.next();

                if (app.applicantWantsToWithdraw()) {
                	System.out.println("\n\n================================");
                    System.out.println("Withdrawal request found:");
                    System.out.println("Applicant: " + app.getApplicant().getName());
                    System.out.println("Project: " + project.getName());
                    System.out.println("Flat Type: " + app.getFlatType());
                    System.out.println("Current Status: " + app.getStatus());
                    System.out.print("Approve this withdrawal request? (y/n): ");
                    String input = sc.nextLine().trim().toLowerCase();

                    if (input.equals("y")) {
                    	// if booked, return flat number to original amount 
                    	if (app.getStatus() == AppStatus.BOOKED) {
                            FlatType flatType = app.getFlatType();
                            if (flatType == FlatType.TWO_ROOM) {
                                project.setNumberOf2Rooms(project.getNumberOf2Rooms() + 1);
                            } else if (flatType == FlatType.THREE_ROOM) {
                                project.setNumberOf3Rooms(project.getNumberOf3Rooms() + 1);
                            }
                        }
                    	app.setStatus(AppStatus.UNSUCCESSFUL);
                        app.setWantToWithdraw(false); //reset withdrawal status
                        
                        // deletion
                    	iterator.remove(); // removed from the list of project apps kept under the project
                		app.getApplicant().setProjectApp(null); // removed from the applicant
                		projectAppRepo.getProjectApps().remove(app); // removed from projectAppRepo
                		
                        
                        System.out.println("Withdrawal approved.\n");
                    } else {
                    	// all withdrawal should be approved, according to Prof Li
                    	// in the hypothetical case of withdrawal being rejected, 
                    	// set wantToWithdraw to false and leave it as it is. 
                    	app.setWantToWithdraw(false);
                        System.out.println("Withdrawal request denied.\n");
                    }
                }
            }
        }
    }
    
    public void toggleVisibility(Manager manager) {
        List<Project> allProjects = projectRepo.getProjectsByManager(manager);

        if (allProjects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        System.out.println("\n=== Your Projects ===");
        for (Project project : allProjects) {
            System.out.println("- " + project.getName() + " (Visibility: " + 
                (project.isVisible() ? "ON" : "OFF") + ")");
        }

        System.out.print("\nEnter the project name to toggle visibility (or press 'enter' to return): ");
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("")) {
            System.out.println("Returning to previous menu...");
            return;
        }

        Project selectedProject = null;
        for (Project p : allProjects) {
            if (p.getName().equalsIgnoreCase(input)) {
                selectedProject = p;
                break;
            }
        }

        if (selectedProject == null) {
            System.out.println("You do not have access to this project or it was not found.");
            return;
        }

        selectedProject.toggleVisibility();
    }


}
