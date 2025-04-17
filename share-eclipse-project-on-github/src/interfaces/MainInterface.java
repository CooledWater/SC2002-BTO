package interfaces;

public class MainInterface {

	public static void main(String[] args) {
        ApplicantMainMenu applicantMainMenu = new ApplicantMainMenu();
        applicantMainMenu.login(); // user logs in
        applicantMainMenu.applicantMenu(); // user navigates applicant options
	}

}
