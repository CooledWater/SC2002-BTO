package services;
import java.util.List;
import java.util.Collections;

public class ApplicantEnquiryService implements ApplicantEnquiryInterface {
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
			// applicant.addEnquiry(applicantEnquiry); not sure if applicant should have enquiry list
			enquiryRepo.addEnquiry(applicantEnquiry);
		}
		else {
			System.out.println("Failed to find applicant.");
		}
	}
	
	
	public List<Enquiry> viewEnquiries(Applicant applicant) {
		Applicant searchApplicant = applicantRepo.searchByNRIC(applicant.getNRIC());
		
		if (searchApplicant != null) {
			return enquiryRepo.searchByApplicant(searchApplicant.getNRIC());
		}
		else {
			System.out.println("Failed to find applicant.");
			return Collections.emptyList();
		}
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
			enquiryRepo.delete(searchEnquiry);
			System.out.println("Enquiry deleted.");
		}
	}
	
}
