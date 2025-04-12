package entity;

public class Enquiry {
	private Applicant applicant;
	private String projectName;
	private String message, response;
	private boolean enquiryResponded = false;
	
	public Enquiry(Applicant applicant, Project project, String message) {
		this.applicant = applicant;
		this.projectName = project.getName();
		this.message = message;
	}
	
	// getter and setter methods
	public Applicant getApplicant() {return applicant;}
	
	public String getProjectName() {return projectName;}
	
	public String getMessage() {return message;}
	
	public String getResponse() {return response;}
	
	public boolean getEnquiryResponded() {return enquiryResponded;}
	
	public void setMessage(String newMessage) {this.message = newMessage;}
	
	public void setResponse(String response) {this.response = response;}
	
	public void setEnquiryResponded(boolean newEnquiryResponded) {this.enquiryResponded = newEnquiryResponded;}
	
}