package entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Enquiry implements Serializable {
	// needs serialVersionUID
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -102013416830897527L;
	private String enquiryID;
	private LocalDateTime submissionDate; // For easy access to datetime
	private Applicant applicant;
	private String projectName;
	private String message, response;
	private boolean enquiryResponded = false;
	
	
	public Enquiry(Applicant applicant, Project project, String message) {
		this.enquiryID = generateTimestampID();
		this.submissionDate = LocalDateTime.now();
		this.applicant = applicant;
		this.projectName = project.getName();
		this.message = message;
	}
	
	
	private String generateTimestampID() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // Format: yyyyMMddHHmmssSSS includes milliseconds
        return now.format(formatter);
    }
	
	
	// getter and setter methods
	public String getID() {return enquiryID;}
	
	public LocalDateTime getSubmissionDate() {return submissionDate;}
	
	public Applicant getApplicant() {return applicant;}
	
	public String getProjectName() {return projectName;}
	
	public String getMessage() {return message;}
	
	public String getResponse() {return response;}
	
	public boolean getEnquiryResponded() {return enquiryResponded;}
	
	public void setMessage(String newMessage) {this.message = newMessage;}
	
	public void setResponse(String response) {this.response = response;}
	
	public void setEnquiryResponded(boolean newEnquiryResponded) {this.enquiryResponded = newEnquiryResponded;}
	
}