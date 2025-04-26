package repository;
import entity.*;

import java.io.ObjectStreamException;
import java.util.*;
public class JoinRequestRepository extends Repository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1624373385498439691L;
	private static JoinRequestRepository instance;
	private List<JoinRequest> joinRequests;
	
	public JoinRequestRepository() {
		this.joinRequests = new ArrayList<>();
	}
	
	public static JoinRequestRepository getInstance() {
        if (instance == null) {
            instance = new JoinRequestRepository();
        }
        return instance;
	}
	
	private Object readResolve() throws ObjectStreamException {
		instance = this;
		return instance;
	}

	public List<JoinRequest> getJoinRequests() {
		return joinRequests;
	}

	public void setJoinRequests(List<JoinRequest> joinRequests) {
		this.joinRequests = joinRequests;
	}
	

}
