package entity;

import java.util.*;

public class Applicant extends User{
	private List<ProjectApp> projectApps;
	private List<Enquiry> enquiries;
	
	public Applicant(String NRIC, String password, boolean maritalStatus, int age) {
		super(NRIC, password, maritalStatus, age);
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
