package entity;
import java.io.*;

public abstract class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4624565131962188794L;
	protected String name;
	protected String NRIC;
	protected String password;
	protected boolean isMarried;
	protected int age;
	
	public User() {
		this.name = null;
		this.NRIC = null;
		this.password = null;
		this.isMarried = false;
		this.age = -1;
	}
	
	public User(String name, String NRIC, int age, boolean isMarried, String password) {
		this.name = name;
		this.NRIC = NRIC;
		this.password = password;
		this.isMarried = isMarried;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNRIC() {
		return NRIC;
	}

	public void setNRIC(String nRIC) {
		NRIC = nRIC;
	}

	
	public boolean isMarried() {
		return isMarried;
	}

	public void setMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPassword() {
		return password;
	}
   
	public void setPassword(String password) {
		this.password = password;
	}
	
	

	

	

   
	
	
}
