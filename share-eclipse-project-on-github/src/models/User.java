package models;

public class User {
	protected String NRIC;
	protected String password;
	protected boolean maritalStatus;
	protected int age;
	
	public User(String NRIC, String password, boolean maritalStatus, int age) {
		this.NRIC = NRIC;
		this.password = password;
		this.maritalStatus = maritalStatus;
		this.age = age;
	}
	
	public boolean login(String enteredPassword) {
		return this.password.equals(enteredPassword);
	}
	
	public void changePasword(String newPassword) {
		this.password = newPassword;
	}
}
