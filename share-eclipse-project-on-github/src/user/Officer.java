package user;

import entity.Project;

public class Officer extends Applicant{
	private Project managingProject;
	
	public Officer(String NRIC, String password, boolean maritalStatus, int age) {
		super(NRIC, password, maritalStatus, age);
	}
	
}
