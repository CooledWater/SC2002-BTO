package entity;

import java.io.*;

public class Manager extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7867857606017910518L;
	
	private Project managingProj;
	
	public Manager() {
		super();
	}
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
