package repository;

import java.io.*;

public abstract class Repository implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2660117034435542277L;

	
	// this method does not require overriding, because its the same method body for all repositories
	public void saveToSer() {
		String filePath = "save\\" + this.getClass().getSimpleName() + ".ser";
		
		new File("save").mkdirs();
		
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this);
			fos.close();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}; 
		
	
	// this method does not require overriding
	public Repository importFromSer() throws IOException, ClassNotFoundException {
		String filePath = "save\\" + this.getClass().getSimpleName() + ".ser";
		
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream in = new ObjectInputStream(fis);
		return (Repository) in.readObject();
		
			
			
		
		
	}; 
	
}
