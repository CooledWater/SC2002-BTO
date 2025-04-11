package services;
import java.util.List;

public interface EnquiryServiceInterface {
	
	List<Enquiry> viewEnquiries(User user);
}


public interface ManagerEnquiryServiceInterface extends EnquiryServiceInterface {
	
	List<Enquiry> viewEnquiries(Manager manager);
	void replyEnquiry(Manager manager, int enquiryID, String response);
}


public interface OfficerEnquiryServiceInterface extends EnquiryServiceInterface {
	
	List<Enquiry> viewEnquiries(Officer officer);
	void replyEnquiry(Officer officer, int enquiryID, String response);
}


public interface ApplicantEnquiryServiceInterface extends EnquiryServiceInterface {
	
	void submitEnquiry(Applicant applicant, Project project, String message);
	List<Enquiry> viewEnquiries(Applicant applicant);
    void editEnquiry(Applicant applicant, int enquiryID, String newMessage);
    void deleteEnquiry(Applicant applicant, int enquiryID);
}
	
	/* implement this in application class

	private boolean filterByManaging = false;
	
	public void filterChange() {
		
		if (filterByManaging == false) filterByManaging = true;
		else filterByManaging = false;
	}
	
	*/
