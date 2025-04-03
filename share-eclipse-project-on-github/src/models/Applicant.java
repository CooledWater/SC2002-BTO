package models;

import java.util.*;
import applications.ProjectApp;
import enums.AppStatus;

public class Applicant extends User{
	private List<ProjectApp> projectApps = new ArrayList<>();
	
	public Applicant(String NRIC, String password, boolean maritalStatus, int age) {
		super(NRIC, password, maritalStatus, age);
	}
	
	public void applyProject(Project project) {
		projectApps.add(new ProjectApp(this, project, AppStatus.PENDING));
	}
}
