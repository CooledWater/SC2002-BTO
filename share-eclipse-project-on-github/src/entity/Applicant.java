package entity;

import java.util.*;

public class Applicant extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2241078442498755535L;
	private ProjectApp projectApp;

	
	public Applicant() {
		super();
		projectApp = null;
	}
	
	public Applicant(String name, String NRIC, int age, boolean isMarried, String password) {
		super(name, NRIC, age, isMarried, password);

		this.projectApp = null;
	}

	public ProjectApp getProjectApp() {
		return projectApp;
	}

	public void setProjectApp(ProjectApp projectApp) {
		this.projectApp = projectApp;
	}
}
