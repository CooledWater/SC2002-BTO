package repository;

import java.io.*;

public interface Repository extends Serializable {
	
	/**
	 * 
	 */
	public abstract void saveToSer();
	public abstract void importFromSer();
	
}
