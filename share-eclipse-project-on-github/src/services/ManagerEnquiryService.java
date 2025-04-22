package services;
import java.util.*;

import entity.*;
import repository.EnquiryRepository;

public class ManagerEnquiryService implements ManagerEnquiryServiceInterface {
	private final EnquiryRepository enquiryRepo;
	
	
	public ManagerEnquiryService(EnquiryRepository enquiryRepo) {
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public void viewEnquiries(Manager manager, boolean filterByManaging) {
		List<Enquiry> enquiryList;
		
		if (filterByManaging) {
			enquiryList = enquiryRepo.searchByProjectName(manager.getManagingProj().getName());
		}
		else {
			enquiryList = enquiryRepo.getAllEnquiries();
		}
		displayEnquiries(enquiryList);
	}

		
	public void replyEnquiry(Manager manager, String enquiryID, String response) {
		Project managerManagingProject = manager.getManagingProj();
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		if (managerManagingProject == null) {
			System.out.println("Manager currently not handling any projects.");
		}
		else if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (!searchEnquiry.getProjectName().equals(managerManagingProject.getName())) {
			System.out.println("Manager not handling this project.");
		}
		else {
			searchEnquiry.setResponse(response);
			searchEnquiry.setEnquiryResponded(true);
			enquiryRepo.update(searchEnquiry);
			System.out.println("Response added.");
		}
		
	}
}