package services;

import entity.*;
import repository.ProjectAppRepository;

import java.util.List;

public class ProjectApplicationService {

    private ProjectAppRepository projectAppRepo;

    public ProjectApplicationService(ProjectAppRepository projectAppRepo) {
        this.projectAppRepo = projectAppRepo;
    }

    
    public void applyProject(Applicant applicant, Project project, FlatType flatType) {
        // checks if they already applied
    	List<ProjectApp> apps = projectAppRepo.getProjectApps(); 
    	
        boolean hasApplied = apps.stream().anyMatch(app -> app.getApplicant().equals(applicant)); 

        if (hasApplied) {
            System.out.println("You have already applied for this project.");
            return;
        }

        ProjectApp newApp = new ProjectApp(applicant, project, AppStatus.PENDING, flatType);
        projectAppRepo.add(newApp); 

        System.out.println("You have successfully applied to project: " + project.getName()
                + " with flat type: " + flatType.name() + ". Status: PENDING");
    }

    //withdrawal method 
    public void requestWithdrawal(Applicant applicant) {
    	List<ProjectApp> apps = projectAppRepo.getProjectApps(); 
    	
    	for (ProjectApp app : apps) { 
    		if(app.getApplicant().equals(applicant)) {
    			if (app.getStatus() == AppStatus.UNSUCCESSFUL) {
    				System.out.println("Your application has already been marked unsuccessful and cannot be withdrawn.");
    				return;
				}
    			
				if (app.applicantWantsToWithdraw()) {
				    System.out.println("You have already requested a withdrawal.");
				    return;
				}
				
				app.requestWithdrawal();
				projectAppRepo.saveToSer();
				System.out.println("Withdrawal request submitted for project: " + app.getProject().getName());
				return;
    		}
    	}
    }
    
    
    public void viewMyProjectApp(Applicant applicant) {
        List<ProjectApp> apps = projectAppRepo.getProjectApps();

        for (ProjectApp app : apps) {
            if (app.getApplicant().equals(applicant)) {
                System.out.println("Your project application:");
                System.out.printf("Project: %s | Flat Type: %s | Status: %s%n",
                        app.getProject().getName(),
                        app.getFlatType().name(),
                        app.getStatus().name());

                if (app.applicantWantsToWithdraw()) {
                    System.out.println("Note: You have requested to withdraw this application.");
                }
                return;
            }
        }

        System.out.println("You have no project applications.");
    }

}