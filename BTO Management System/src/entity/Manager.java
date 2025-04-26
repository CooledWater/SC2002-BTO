package entity;

import java.io.*;
import java.util.*;

public class Manager extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7867857606017910518L;
	
	private Project managingProj;
	private List<JoinRequest> joinRequests;
	
    public Manager(String name, String NRIC, int age, boolean isMarried, String password) {
        super(name, NRIC, age, isMarried, password);
        this.managingProj = null;
        this.joinRequests = new ArrayList<>();
    }
    
    public Manager() {
    	super();
    	this.managingProj = null;
        this.joinRequests = new ArrayList<>();
    	
    }

	public Project getManagingProj() {
		return managingProj;
	}

	public void setManagingProj(Project managingProj) {
		this.managingProj = managingProj;
	}

	public List<JoinRequest> getJoinRequests() {
		return joinRequests;
	}

	public void setJoinRequests(List<JoinRequest> joinRequests) {
		this.joinRequests = joinRequests;
	}
	
	public String toString() {
		return String.format("%nManager name: " + this.name
						    +"%nAge: " + this.age
						    +"%nMarital Status: " + (this.isMarried? "Married" : "Single")
						    +"%nManaging Project: " + (this.getManagingProj() == null? "None" : this.getManagingProj().getName())
				);
	}
}
