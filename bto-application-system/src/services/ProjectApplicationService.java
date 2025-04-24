package services;

import entity.*;
import repository.ProjectAppRepository;
import repository.ProjectRepository;
import java.util.List;

public class ProjectApplicationService {

    private ProjectAppRepository projectAppRepo;
    private ProjectRepository projectRepo;

    public ProjectApplicationService(ProjectAppRepository projectAppRepo, ProjectRepository projectRepo) {
        this.projectAppRepo = projectAppRepo;
        this.projectRepo = projectRepo;
    }

    
    public void applyProject(Applicant applicant, Project project, FlatType flatType) {
    	// check if this applicant is simultaneously an officer who is managing this project
    	if (applicant instanceof Officer) {
    		Officer thisOfficer = (Officer) applicant; // downcasting
    		
    		List<Project> officerProjects = projectRepo.getProjectsByOfficer(thisOfficer); // replaced getHandlingProj with getting from repo
    		for (Project p : officerProjects) {
    			if (p == project) {
        			System.out.println("Since you are an officer of this project, you cannot apply for it. ");
        			return;
    			}
    		}
    	}
    	
        // checks if they already applied
    	List<ProjectApp> apps = projectAppRepo.getProjectApps(); 
    	
        boolean hasApplied = apps.stream().anyMatch(app -> app.getApplicant().equals(applicant)); 

        if (hasApplied) {
            System.out.println("You have already applied for this project.");
            return;
        }
        
        // if applicant is single 
        if (applicant.isMarried() == false) {
        	if (flatType == FlatType.THREE_ROOM) {
	        	System.out.println("Since you are single, you cannot apply for a 3-room flat. ");
	        	return;
        	}
        	if (applicant.getAge() < 35) {
        		System.out.println("Since you are single and below 35 years old, you cannot apply for BTO. "); 
        		return;
        	}
        } else {
        	// married and below 21 LMAO
        	if (applicant.getAge() < 21) {
        		System.out.println("Since you are below 21 years old, you cannot apply for BTO. ");
        		return;
        	}
        }
       
        ProjectApp newApp = new ProjectApp(applicant, project, AppStatus.PENDING, flatType);
        projectAppRepo.add(newApp); 
        applicant.setProjectApp(newApp);
        project.addProjectApp(newApp);

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
				
				app.setWantToWithdraw(true);
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