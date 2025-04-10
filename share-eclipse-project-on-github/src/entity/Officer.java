package entity;

public class Officer extends Applicant{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8569435014677556203L;
	private Project handlingProj;
	
	public Officer() {
		super();
		handlingProj = null;
	}
	
    public Officer(String name, String NRIC, int age, boolean isMarried, String password) {
        super(name, NRIC, age, isMarried, password);
        this.handlingProj = null;
    }

	public Project getHandlingProj() {
		return handlingProj;
	}

	public void setHandlingProj(Project handlingProj) {
		this.handlingProj = handlingProj;
	}
	
}
