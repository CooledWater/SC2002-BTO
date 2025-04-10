package entity;

public class Officer extends Applicant{
	private Project handlingProj;
	
	public Officer(String NRIC, String password, boolean maritalStatus, int age) {
		super(NRIC, password, maritalStatus, age);
		this.handlingProj = null;
	}

	public Project getHandlingProj() {
		return handlingProj;
	}

	public void setHandlingProj(Project handlingProj) {
		this.handlingProj = handlingProj;
	}
	
}
