package repository;

import java.io.*;

public abstract class Repository implements Serializable {
	
	/**
	 * 
	 */
	public void saveToSer(); // this method does not require overriding, because its the same method body for all repositories
	public static Repository importFromSer(); // this static method does not require overriding
	
}
