package services;
import java.util.*;

import entity.*;
import repository.ApplicantRepository;
import repository.EnquiryRepository;

public class ApplicantEnquiryService implements ApplicantEnquiryServiceInterface {
	private final ApplicantRepository applicantRepo;
	private final EnquiryRepository enquiryRepo;
	
	public ApplicantEnquiryService(ApplicantRepository applicantRepo, EnquiryRepository enquiryRepo) {
		this.applicantRepo = applicantRepo;
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public void submitEnquiry(Applicant applicant, Project project, String message) {
		// checking that applicant exists
		Applicant searchApplicant = applicantRepo.searchByNRIC(applicant.getNRIC());
		
		if (searchApplicant != null) {
			Enquiry applicantEnquiry = new Enquiry(searchAapplicant, project, message);
			enquiryRepo.addEnquiry(applicantEnquiry);
		}
		else {
			System.out.println("Failed to find applicant.");
		}
	}
	
	
	public void viewEnquiries(Applicant applicant) {
		List<Enquiry> enquiryList;
		Applicant searchApplicant = applicantRepo.searchByNRIC(applicant.getNRIC());
		
		if (searchApplicant != null) {
			enquiryList = enquiryRepo.searchByApplicant(searchApplicant.getNRIC());
		}
		else {
			System.out.println("Failed to find applicant.");
			enquiryList = Collections.emptyList();
		}
		displayEnquiries(enquiryList);
	}
	
	
	public void editEnquiry(Applicant applicant, int enquiryID, String newMessage) {
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		// checks that enquiry exists and that the applicant who created the enquiry is the one that is editing
		if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (searchEnquiry.getApplicant().getNRIC() != applicant.getNRIC()) {
			System.out.println("You do not have permission to edit this enquiry.");
		}
		else {
			searchEnquiry.setMessage(newMessage);
			enquiryRepo.update(searchEnquiry);
			System.out.println("Enquiry edited.");
		}
	}
	
	
	public void deleteEnquiry(Applicant applicant, int enquiryID) {
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (searchEnquiry.getApplicant().getNRIC() != applicant.getNRIC()) {
			System.out.println("You do not have permission to edit this enquiry.");
		}
		else {
			enquiryRepo.delete(searchEnquiry.getID());
			System.out.println("Enquiry deleted.");
		}
	}
	
}
