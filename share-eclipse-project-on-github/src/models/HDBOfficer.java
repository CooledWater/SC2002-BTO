package models;

public class HDBOfficer extends Applicant{
	private Project managingProject;
	
	public HDBOfficer(String NRIC, String password, boolean maritalStatus, int age) {
		super(NRIC, password, maritalStatus, age);
	}
	
	public void joinProject(Project project) {
		if(this.managingProject == null) {
			this.managingProject = project;
		}else {
			System.out.println("Officer can only manage 1 project at a time");
		}
	}
}
