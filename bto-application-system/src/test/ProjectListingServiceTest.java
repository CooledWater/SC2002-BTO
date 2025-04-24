package test;
import services.*;
import repository.*;


import entity.*;



public class ProjectListingServiceTest {

	public static void main(String[] args) {
		ProjectRepository projectRepo = new ProjectRepository();
		ProjectListingService projectListingService = new ProjectListingService(projectRepo);
		Manager Alice = new Manager("Alice", "A1234567B", 25, true, "Password");
		

		// Project myProject = projectListingService.createNewProjectListing(Alice);
		

	}

}
