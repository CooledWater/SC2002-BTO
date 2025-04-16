package services;
import java.util.*;

import entity.*;
import repository.EnquiryRepository;

public class OfficerEnquiryService implements OfficerEnquiryServiceInterface {
	private final EnquiryRepository enquiryRepo;
	
	
	public OfficerEnquiryService(EnquiryRepository enquiryRepo) {
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public void viewEnquiries(Officer officer) {
		List<Enquiry> enquiryList;
		Project officerHandlingProject = officer.getHandlingProj();
		
		if (officerHandlingProject != null) {
			enquiryList = enquiryRepo.searchByProjectName(officerHandlingProject.getName());
		}
		else {
			System.out.println("Officer currently not handling any projects.");
			enquiryList = Collections.emptyList();
		}
		displayEnquiries(enquiryList);
	}
	
	
	public void replyEnquiry(Officer officer, int enquiryID, String response) {
		Project officerHandlingProject = officer.getHandlingProj();
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (searchEnquiry.getProjectName() != officerHandlingProject.getName()) {
			System.out.println("Officer not handling this project.");
		}
		else {
			searchEnquiry.setResponse(response);
			enquiryRepo.update(searchEnquiry);
			System.out.println("Response added.");
		}
		
	}


}
