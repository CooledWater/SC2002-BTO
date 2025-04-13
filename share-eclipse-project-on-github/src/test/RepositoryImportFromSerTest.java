package test;

import repository.ManagerRepository;
import java.io.*;

public class RepositoryImportFromSerTest {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		ManagerRepository managerRepository = new ManagerRepository();
		managerRepository = (ManagerRepository) managerRepository.importFromSer();
		System.out.println(managerRepository.getManagers().getFirst().getManagingProj().getName());

	}

}
