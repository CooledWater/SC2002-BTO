package entity;

public class Officer extends Applicant{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8569435014677556203L;
	private Project handlingProj;
	private JoinRequest joinRequest;
	
	public Officer() {
		super();
		this.handlingProj = null;
		this.joinRequest = null;
		
	}
	
    public Officer(String name, String NRIC, int age, boolean isMarried, String password) {
        super(name, NRIC, age, isMarried, password);
        this.handlingProj = null;
        this.joinRequest = null;
    }

	public Project getHandlingProj() {
		return handlingProj;
	}

	public void setHandlingProj(Project handlingProj) {
		this.handlingProj = handlingProj;
	}

	public JoinRequest getJoinRequest() {
		return joinRequest;
	}

	public void setJoinRequest(JoinRequest joinRequest) {
		this.joinRequest = joinRequest;
	}
	
}
