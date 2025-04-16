package services;
import java.util.Scanner;
import repository.*; 
import entity.*;

import java.util.*;

public class ManageProjectAppService {
    private ProjectRepository projectRepo;
    private Scanner sc = new Scanner(System.in);

    public ManageProjectAppService(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
    }

    // process application managed by manager
    
    public void processProjectApp(Manager manager) {
        List<Project> projects = projectRepo.getProjectsByManager(manager);

        for (Project project : projects) {
            List<ProjectApp> apps = project.getProjectApps();
            if (apps == null || apps.isEmpty()) continue;

            for (ProjectApp app : apps) {
                if (app.getStatus() == AppStatus.PENDING) {
                    System.out.println("Applicant: " + app.getApplicant().getName());               
                    System.out.println("Project: " + project.getName());
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
            if (apps == null || apps.isEmpty()) continue;

            Iterator<ProjectApp> iterator = apps.iterator();
            while (iterator.hasNext()) {
                ProjectApp app = iterator.next();
                if (app.getStatus() == AppStatus.SUCCESSFUL) {
                    System.out.println("Applicant: " + app.getApplicant().getName());
                    System.out.println("Flat Type: " + app.getFlatType());
                    System.out.println("Project: " + project.getName());
                    System.out.print("Approve the application withdrawal? (y/n): ");
                    String input = sc.nextLine().trim().toLowerCase();

                    if (input.equals("y")) {
                    	//add flat number 
                        FlatType flatType = app.getFlatType();
                        if (flatType == FlatType.TWO_ROOM) {
                            project.setNumberOf2Rooms(project.getNumberOf2Rooms() + 1);
                        } else if (flatType == FlatType.THREE_ROOM) {
                            project.setNumberOf3Rooms(project.getNumberOf3Rooms() + 1);
                        }

                        app.setStatus(AppStatus.UNSUCCESSFUL);
                        iterator.remove();
                        System.out.println("Application withdrawn\n");
                    }
                }
            }
        }
    }
}
