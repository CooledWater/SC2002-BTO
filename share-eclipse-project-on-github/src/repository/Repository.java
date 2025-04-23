package repository;

import java.io.*;

public abstract class Repository implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2660117034435542277L;

	
	// this method does not require overriding, because its the same method body for all repositories
	public void saveToSer(ObjectOutputStream out) throws IOException {
		out.writeObject(this);
	}
		
	
	// this method does not require overriding, because its the same method body for all repositories
	public Repository importFromSer(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return (Repository) in.readObject();
	}
}
