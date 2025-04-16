package repository;
import entity.*;
import java.util.*;
public class JoinRequestRepository extends Repository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1624373385498439691L;
	private List<JoinRequest> joinRequests;
	
	public JoinRequestRepository() {
		this.joinRequests = new ArrayList<>();
	}

	public List<JoinRequest> getJoinRequests() {
		return joinRequests;
	}

	public void setJoinRequests(List<JoinRequest> joinRequests) {
		this.joinRequests = joinRequests;
	}
	

}
