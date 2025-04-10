package test;
import repository.*;
import entity.*;

public class ManagerImportFromCSVTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ManagerRepository managerRepository = new ManagerRepository();
		managerRepository.importFromCSV();
		for (Manager manager : managerRepository.getManagers()) {
			System.out.println(manager.getName());
		}

	}

}
