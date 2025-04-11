package services;
import java.util.List;

public class ManagerEnquiryService implements ManagerEnquiryServiceInterface {
	private final ManagerRepository managerRepo;
	private final EnquiryRepository enquiryRepo;
	
	
	public ManagerEnquiryService(ManagerRepository managerRepo, EnquiryRepository enquiryRepo) {
		this.managerRepo = managerRepo;
		this.enquiryRepo = enquiryRepo;
	}
	
	
	public List<Enquiry> viewEnquiries(Manager manager, boolean filterByManaging) {
		
		if (filterByManaging) {
			return enquiryRepo.searchByManager(manager);
		}
		else {
			return enquiryRepo.getAllEnquiries();
		}
	}

		
	public void replyEnquiry(Manager manager, int enquiryID, String response) {
		Project managerManagingProject = manager.getManagingProj();
		Enquiry searchEnquiry = enquiryRepo.searchByID(enquiryID);
		
		if (managerManagingProject == null) {
			System.out.println("Manager currently not handling any projects.");
		}
		else if (searchEnquiry == null) {
			System.out.println("Enquiry not found.");
		}
		else if (searchEnquiry.getProjectName() != managerManagingProject.getName()) {
			System.out.println("Manager not handling this project.");
		}
		else {
			searchEnquiry.setResponse(response);
			enquiryRepo.update(searchEnquiry);
			System.out.println("Response added.");
		}
		
	}
}