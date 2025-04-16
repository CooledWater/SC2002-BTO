package services;
import java.util.List;

public interface EnquiryServiceInterface {
	
	default void displayEnquiries(List<Enquiry> enquiries) {
        System.out.println("--- Enquiries ---");
        for (Enquiry enquiry : enquiries) {
        	System.out.println("Applicant Name: " + enquiry.getApplicant().getName());
        	System.out.println("Project Name: " + enquiry.getProjectName());
        	System.out.println("Message: " + enquiry.getMessage());
        	if (enquiry.getResponse() != null) {
        		System.out.println("Response: " + enquiry.getResponse());
        	} else {
        		System.out.println("Response: No response yet.");
        	}
        }
    }
	
}


public interface ManagerEnquiryServiceInterface extends EnquiryServiceInterface {
	
	void viewEnquiries(Manager manager, boolean filterByManaging);
}


public interface OfficerEnquiryServiceInterface extends EnquiryServiceInterface {
	
	void viewEnquiries(Officer officer);
}


public interface ApplicantEnquiryServiceInterface extends EnquiryServiceInterface {
	
	void viewEnquiries(Applicant applicant);
}
	
	/* implement this in application class

	private boolean filterByManaging = false; // maybe this in Manager object itself?
	
	public void filterChange() {
		
		if (filterByManaging == false) filterByManaging = true;
		else filterByManaging = false;
	}
	
	*/
