package test;
import entity.*;
import repository.*;


public class RepositorySaveToSerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ManagerRepository managerRepository = new ManagerRepository();
		Manager manager1 = new Manager("Alice", "NRIC", 35, true, "Password#$%");
		managerRepository.getManagers().add(manager1);
		managerRepository.saveToSer();
		

	}

}
