package services;
import java.util.*;

import entity.*;
import repository.ApplicantRepository;
import repository.OfficerRepository;
import repository.EnquiryRepository;

public class ApplicantEnquiryService implements ApplicantEnquiryServiceInterface {
	private final ApplicantRepository applicantRepo;
	private final OfficerRepository officerRepo;
	private final EnquiryRepository enquiryRepo;
	
	public ApplicantEnquiryService(ApplicantRepository applicantRepo, OfficerRepository officerRepo, EnquiryRepository enquiryRepo) {
		this.applicantRepo = applicantRepo;
		this.officerRepo = officerRepo;
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public void submitEnquiry(Applicant applicant, Project project, String message) {
		// checking that applicant exists
		Applicant searchApplicant = applicantRepo.searchByNRIC(applicant.getNRIC());
		if (searchApplicant == null) {
			searchApplicant = officerRepo.searchByNRIC(applicant.getNRIC());
		}
		if (searchApplicant != null) {
			Enquiry applicantEnquiry = new Enquiry(searchApplicant, project, message);
			enquiryRepo.addEnquiry(applicantEnquiry);
		}
		else {
			System.out.println("Failed to find applicant.");
		}
	}
	
	
	public boolean viewEnquiries(Applicant applicant) {
		List<Enquiry> enquiryList;
		Applicant searchApplicant = applicantRepo.searchByNRIC(applicant.getNRIC());
		if (searchApplicant == null) {
			searchApplicant = officerRepo.searchByNRIC(applicant.getNRIC());
		}
		if (searchApplicant != null) {
			enquiryList = enquiryRepo.searchByApplicant(searchApplicant.getNRIC());
		}
		else {
			System.out.println("Failed to find applicant.");
			enquiryList = Collections.emptyList();
		}
		displayEnquiries(enquiryList);
		
		if (enquiryList.isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	public void editEnquiry(Applicant applicant, String enquiryID, String newMessage) {
		Enquiry searchEnquiry = null;
		try {
			searchEnquiry = enquiryRepo.searchByID(enquiryID);
		} catch (NoSuchElementException e) {
			System.out.println("Enquiry not found.");
			return;
		}
		
		// checks that enquiry exists and that the applicant who created the enquiry is the one that is editing
		if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (!searchEnquiry.getApplicant().getNRIC().equals(applicant.getNRIC())) {
			System.out.println("You do not have permission to edit this enquiry.");
		}
		else {
			searchEnquiry.setMessage(newMessage);
			enquiryRepo.update(searchEnquiry);
			System.out.println("Enquiry edited.");
		}
	}
	
	
	public void deleteEnquiry(Applicant applicant, String enquiryID) {
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		} else {
			if (!searchEnquiry.getApplicant().getNRIC().equals(applicant.getNRIC())) {
				System.out.println("You do not have permission to delete this enquiry.");
			} else {
				enquiryRepo.delete(searchEnquiry.getID());
				System.out.println("Enquiry deleted.");
			}
		}
	}
	
}
