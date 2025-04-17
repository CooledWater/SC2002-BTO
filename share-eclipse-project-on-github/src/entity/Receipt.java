package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Receipt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5030834469499988505L;
	private String applicantNRIC;
	private String applicantName;
	private int age;
	private boolean married;
	private FlatType flatType;
	private String projectName;
	private String neighbourhood;
	private LocalDate bookingDate;
	private boolean withdrawn = false;
	
	public Receipt(String applicantNRIC, String applicantName, int age, boolean married, 
	               FlatType flatType, String projectName, String neighbourhood, LocalDate bookingDate) {
	    this.applicantNRIC = applicantNRIC;
	    this.applicantName = applicantName;
	    this.age = age;
	    this.married = married;
	    this.flatType = flatType;
	    this.projectName = projectName;
	    this.neighbourhood = neighbourhood;
	    this.bookingDate = bookingDate;
	}
	
	public String getApplicantNRIC() { 
		return applicantNRIC; 
	}
	
	public String getApplicantName() { 
		return applicantName; 
	}
	
	public void setWithdrawn(boolean withdrawn) {
		this.withdrawn = withdrawn;
	}
	
	public boolean isWithdrawn() {
		return withdrawn;
	}
	
	@Override
	public String toString() {
	    return "\n=== Booking Receipt ===\n" +
	       "NRIC: " + applicantNRIC + "\n" +
	       "Name: " + applicantName + "\n" +
	       "Age: " + age + "\n" +
	       "Marital Status: " + (married ? "Married" : "Single") + "\n" +
	       "Flat Type: " + flatType + "\n" +
	       "Project: " + projectName + "\n" +
	       "Neighbourhood: " + neighbourhood + "\n" +
	       "Booking Date: " + bookingDate + "\n" +
	       "========================\n";
	}
}