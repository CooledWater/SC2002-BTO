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
		managingProj = null;
	}
	
    public Manager(String name, String NRIC, int age, boolean isMarried, String password) {
        super(name, NRIC, age, isMarried, password);
        this.managingProj = null;
    }

	public Project getManagingProj() {
		return managingProj;
	}

	public void setManagingProj(Project managingProj) {
		this.managingProj = managingProj;
	}
}
