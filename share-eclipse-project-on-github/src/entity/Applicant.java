package entity;

import java.util.*;

public class Applicant extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2241078442498755535L;
	private List<ProjectApp> projectApps;
	private List<Enquiry> enquiries;
	
	public Applicant() {
		super();
		projectApps = null;
		enquiries = null;
	}
	
	public Applicant(String name, String NRIC, int age, boolean isMarried, String password) {
		super(name, NRIC, age, isMarried, password);
		this.projectApps = new ArrayList<>();
		this.enquiries = new ArrayList<>();
	}

	public List<ProjectApp> getProjectApps() {
		return projectApps;
	}

	public void setProjectApps(List<ProjectApp> projectApps) {
		this.projectApps = projectApps;
	}

	public List<Enquiry> getEnquiries() {
		return enquiries;
	}

	public void setEnquiries(List<Enquiry> enquiries) {
		this.enquiries = enquiries;
	}
	
}
