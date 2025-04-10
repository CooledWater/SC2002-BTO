package repository;
import entity.*;
import java.util.*;
public class OfficerRepository extends RepositoryWithCSV {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6513950936385511860L;
	private List<Officer> officers;
	
	public OfficerRepository() {
		this.officers = new ArrayList<>();
	}
	
	public void importFromCSV() {
		;
	}
}
