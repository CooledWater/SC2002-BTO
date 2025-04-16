package test;
import services.*;

import java.text.ParseException;

import entity.*;



public class ProjectListingServiceTest {

	public static void main(String[] args) {
		ProjectListingService projectListingService = new ProjectListingService();
		Manager Alice = new Manager("Alice", "A1234567B", 25, true, "Password");
		

		Project myProject = projectListingService.createNewProjectListing(Alice);
		

	}

}
