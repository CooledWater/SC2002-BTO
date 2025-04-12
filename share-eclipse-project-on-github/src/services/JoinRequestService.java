package services;
import java.util.Iterator;

import entity.*;
import repository.*;
import entity.JoinRequest.Status;

public class JoinRequestService {
	private JoinRequestRepository joinRequestRepository;
	
	public JoinRequestService(JoinRequestRepository joinRequestRepository) {
		this.joinRequestRepository = joinRequestRepository;
	}
	
	public void submitJoinRequest(Officer officer, Project project) {
		// the officer should not have applied for this project as an applicant beforehand
		if (officer.getProjectApp().getProject().equals(project)) {
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because you have already applied to this project as an applicant. ");
			return;
			
		}
		
		// check and make sure that officer can only manage one project at any point of time
		if (officer.getHandlingProj() != null) {
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because you are already handling a project. ");
			return;
		}		
		// one project can have max 10 officers
		if (project.getNumberOfOfficers() >= 10) {
			System.out.println("You are forbidden to join this project as an officer, "
					+ "because the 10 officer slots are all occupied. ");
			return;
			
		}
		
		JoinRequest newJoinRequest = new JoinRequest(officer, project);
		joinRequestRepository.getJoinRequests().add(newJoinRequest);
		project.getManager().getJoinRequests().add(newJoinRequest);
		officer.setJoinRequest(newJoinRequest);
		
		System.out.println("Your have submitted a request to join the following project as an officer: %n"
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
		joinRequest.setStatus(Status.APPROVED);
		// update project officer list
		joinRequest.getProject().getOfficers().add(joinRequest.getOfficer());
		joinRequest.getProject().setNumberOfOfficers(joinRequest.getProject().getNumberOfOfficers()+1);
		// update officer profile
		joinRequest.getOfficer().setHandlingProj(joinRequest.getProject());
	}
	
	
}
