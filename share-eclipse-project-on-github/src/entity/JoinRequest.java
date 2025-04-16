package entity;

import java.io.Serializable;

public class JoinRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 396704317769594477L;
	
	public enum Status {PENDING, APPROVED, REJECTED}
	private Officer officer;
	private Project project;
	private Status status;
	
	public JoinRequest(Officer officer, Project project) {
		this.officer = officer;
		this.project = project;
		this.status = Status.PENDING;
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "%n----------------------------------------------------"
				+ "%nProject: " + project.getName()
				+ "%nRequesting officer: " + officer.getName()
				+ "%nRequesting status: " + status
				+ "%n%n";
	}
	
	
}
