package user;

public class User {
	protected String NRIC;
	protected String password;
	protected boolean isMarried;
	protected int age;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	
	
}
