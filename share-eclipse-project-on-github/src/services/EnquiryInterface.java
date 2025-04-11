package services;
import java.util.List;

public interface EnquiryInterface {
	
	List<Enquiry> viewEnquiries(User user);
}


public interface ManagerEnquiryInterface extends EnquiryInterface {
	
	List<Enquiry> viewEnquiries(Manager manager);
	void replyEnquiry(Manager manager, int enquiryID, String response);
}


public interface OfficerEnquiryInterface extends EnquiryInterface {
	
	List<Enquiry> viewEnquiries(Officer officer);
	void replyEnquiry(Officer officer, int enquiryID, String response);
}


public interface ApplicantEnquiryInterface extends EnquiryInterface {
	
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
