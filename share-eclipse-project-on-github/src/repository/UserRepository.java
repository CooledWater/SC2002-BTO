package repository;

public abstract class UserRepository<T> extends RepositoryWithCSV {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5229202620662024987L;

	public abstract void importFromCSV();
	
	public abstract T searchByNRIC(String NRIC);

}
