package entity;

import java.util.*;

public class Applicant extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2241078442498755535L;
	private ProjectApp projectApp;
	private Booking booking;

	
	public Applicant() {
		super();
		projectApp = null;
		booking = null;
	}
	
	public Applicant(String name, String NRIC, int age, boolean isMarried, String password) {
		super(name, NRIC, age, isMarried, password);

		this.booking = null;
	}

	public ProjectApp getProjectApp() {
		return projectApp;
	}

	public void setProjectApp(ProjectApp projectApp) {
		this.projectApp = projectApp;
	}

	public Booking getBooking() {
	    return booking;
	}
	public void setBooking(Booking booking) {
	    this.booking = booking;
	}
}
