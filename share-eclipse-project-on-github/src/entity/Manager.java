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
    
    public Manager() {}

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
}
