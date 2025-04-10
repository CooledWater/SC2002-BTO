package entity;
import java.io.*;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4624565131962188794L;
	protected String NRIC;
	protected String password;
	protected boolean isMarried;
	protected int age;
	
	public User() {
		this.NRIC = null;
		this.password = null;
		this.isMarried = false;
		this.age = -1;
	}
	
	public User(String NRIC, String password, boolean isMarried, int age) {
		this.NRIC = NRIC;
		this.password = password;
		this.isMarried = isMarried;
		this.age = age;
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
	
	public boolean login(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

	public String getPassword() {
		return password;
	}
   
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

   
	
	
}
