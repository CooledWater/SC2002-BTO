package services;
import java.text.SimpleDateFormat;
import java.util.*;

import entity.*;
import repository.*;
import entity.JoinRequest.Status;

public class JoinRequestService {
	private JoinRequestRepository joinRequestRepository;
	private ProjectRepository projectRepository;
	
	public JoinRequestService(JoinRequestRepository joinRequestRepository, ProjectRepository projectRepository) {
		this.joinRequestRepository = joinRequestRepository;
		this.projectRepository = projectRepository;
	}
	
	public void submitJoinRequest(Officer officer, Project project) {
		// the officer should not have applied for this project as an applicant beforehand
		if (officer.getProjectApp() != null && officer.getProjectApp().getProject().equals(project)) {
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because you have already applied to this project as an applicant. ");
			return;
			
		}
		
		// check and make sure that officer can only manage one project at any point of time
		if (officer.getHandlingProj() != null) { // this should stay as officer.getHandlingProj()
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because you are currently handling a project. ");
			return;
		}
		
		// check that officer's other projects (not active ones) will not overlap with the join request project
		List<Project> officerProjects = projectRepository.getProjectsByOfficer(officer);
    	for (Project officerProject: officerProjects) {
    		Date otherOpen = officerProject.getOpenDate();
    		Date otherClose = officerProject.getCloseDate();
    		
    		if (
    				(!project.getOpenDate().before(otherOpen)) && (!project.getOpenDate().after(otherClose)) ||
    				(!project.getCloseDate().before(otherOpen)) && (!project.getCloseDate().after(otherClose))
    			) 
    		{
    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    			String otherOpenString = sdf.format(otherOpen);
    			String otherCloseString = sdf.format(otherClose);
    			System.out.printf("You are forbidden to join this project as an officer, "
    					+ "because you are already handling a project from %s to %s%n. ", otherOpenString, otherCloseString);
    			return;
    		}
    	}
		
		// one project can have max 10 officers
		if (project.getNumberOfOfficers() >= 10) {
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because the 10 officer slots are all occupied. ");
			return;
			
		}
		
		// cannot submit another join request if officer have an approved request or a pending request
		if (officer.getJoinRequest() != null 
				&& (
						(officer.getJoinRequest().getStatus()==Status.APPROVED) ||
						(officer.getJoinRequest().getStatus()==Status.PENDING)
					)
			) {
			System.out.println("You cannot submit a new request, because you currently have an active join request. ");
			return;
			
		}
		
		
		JoinRequest newJoinRequest = new JoinRequest(officer, project); // create new join request
		joinRequestRepository.getJoinRequests().add(newJoinRequest); // add to repo
		project.getManager().getJoinRequests().add(newJoinRequest); // send to manager
		officer.setJoinRequest(newJoinRequest); // save it to profile
		
		System.out.format("Your have submitted a request to join the following project as an officer: %n"
				+ project.getName()
				+ "%nYour request status is now PENDING. Please wait for the manager to review your request. %n%n");
		return;
	}
	
	public Status getJoinRequestStatus(Officer officer) {
		return officer.getJoinRequest().getStatus();
	}
	
	public void showJoinRequests(Manager manager) {
		Iterator<JoinRequest> it = manager.getJoinRequests().iterator();
		while (it.hasNext()) {
			JoinRequest jr = it.next();
			System.out.println(jr.toString());
		}
	}
	
	public void approveJoinRequest(Manager manager, JoinRequest joinRequest) {
		if (!joinRequest.getStatus().equals(Status.PENDING)) {
			return;
		}
		joinRequest.setStatus(Status.APPROVED);
		// update project officer list
		joinRequest.getProject().getOfficers().add(joinRequest.getOfficer());
		joinRequest.getProject().setNumberOfOfficers(joinRequest.getProject().getNumberOfOfficers()+1);
		// update officer profile
		joinRequest.getOfficer().setHandlingProj(joinRequest.getProject());
	}
	
	public void rejectJoinRequest(Manager manager, JoinRequest joinRequest) {
		if (!joinRequest.getStatus().equals(Status.PENDING)) {
			return;
		}
		joinRequest.setStatus(Status.REJECTED);
		// decided not to remove rejected join request
	}	
}
