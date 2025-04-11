package services;
import java.util.List;

public class OfficerEnquiryService implements OfficerEnquiryInterface {
	private final OfficerRepository officerRepo;
	private final EnquiryRepository enquiryRepo;
	
	
	public OfficerEnquiryService(OfficerRepository officerRepo, EnquiryRepository enquiryRepo) {
		this.officerRepo = officerRepo;
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public List<Enquiry> viewEnquiries(Officer officer) {		
		Project officerHandlingProject = officer.getHandlingProj();
		
		if (officerHandlingProject != null) {
			return enquiryRepo.searchByProjectName(officerHandlingProject.getName());
		}
		else {
			System.out.println("Officer currently not handling any projects.");
			return Collections.emptyList();
		}
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
