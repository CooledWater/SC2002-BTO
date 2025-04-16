package services;

import entity.*;
import repository.ProjectAppRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectApplicationService {

    private ProjectAppRepository projectAppRepo;

    public ProjectApplicationService(ProjectAppRepository projectAppRepo) {
        this.projectAppRepo = projectAppRepo;
    }

    
    public void applyProject(Applicant applicant, Project project, FlatType flatType) {
        // checks if they already applied
        boolean hasApplied = projectAppRepo.getProjectApps().stream()
                .anyMatch(app -> app.getApplicant().equals(applicant) && app.getProject().equals(project));

        if (hasApplied) {
            System.out.println("You have already applied for this project.");
            return;
        }

        ProjectApp app = new ProjectApp(applicant, project, AppStatus.PENDING, flatType);
        projectAppRepo.add(app);

        System.out.println("You have successfully applied to project: " + project.getName()
                + " with flat type: " + flatType.name() + ". Status: PENDING");
    }

    public void withdrawProject(Applicant applicant) {
        List<ProjectApp> apps = projectAppRepo.getProjectApps();
        boolean found = false;
        //withdrawing project application
        for (ProjectApp app : apps) {
            if (app.getApplicant().equals(applicant) && app.getStatus() == AppStatus.PENDING) {
                app.setStatus(AppStatus.UNSUCCESSFUL);
                found = true;
            }
        }

        if (found) {
            projectAppRepo.saveToSer();
            System.out.println("Your application has been withdrawn.");
        } else {
            System.out.println("No PENDING application found to withdraw.");
        }
    }

    
    public List<ProjectApp> viewMyProjectApp(Applicant applicant) {
        List<ProjectApp> myApps = projectAppRepo.getProjectApps().stream()
                .filter(app -> app.getApplicant().equals(applicant))
                .collect(Collectors.toList());
        
        if (myApps.isEmpty()) {
            System.out.println("You have no project applications.");
        } else {
            System.out.println("Your project applications:");
            for (ProjectApp app : myApps) {
                System.out.printf("Project: %s | Flat Type: %s | Status: %s%n",
                        app.getProject().getName(),
                        app.getFlatType().name(),
                        app.getStatus().name());
            }
        }

        return myApps;
    }

    public void applyProject(Officer officer, Applicant applicant, Project project, FlatType flatType) {
        System.out.println("Officer " + officer.getName() + " is applying on behalf of applicant: "
                + applicant.getName());
        applyProject(applicant, project, flatType);
    }
}