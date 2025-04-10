package entity;

public class Manager extends User{
	private Project managingProj;
	
    public Manager(String NRIC, String password, boolean maritalStatus, int age) {
        super(NRIC, password, maritalStatus, age);
        this.managingProj = null;
    }

	public Project getManagingProj() {
		return managingProj;
	}

	public void setManagingProj(Project managingProj) {
		this.managingProj = managingProj;
	}
}
