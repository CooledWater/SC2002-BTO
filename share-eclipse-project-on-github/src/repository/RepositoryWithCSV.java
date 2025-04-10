package repository;

public abstract class RepositoryWithCSV extends Repository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3518304812503558228L;


	
	public abstract void importFromCSV(); // need to be overridden by subclasses
	

}
