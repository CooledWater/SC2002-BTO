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
		
		
		while (true) {
			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(this);
				fos.close();
				out.close();
				break;
				
			} catch (FileNotFoundException e) {
				new File("save").mkdirs();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				break;
			}
		}
	}
		
	
	// this method does not require overriding, because its the same method body for all repositories
	public Repository importFromSer() throws IOException, ClassNotFoundException {
		String filePath = "save\\" + this.getClass().getSimpleName() + ".ser";
		
		try(FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream in = new ObjectInputStream(fis)){
			
			return (Repository) in.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
