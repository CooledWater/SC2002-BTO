package test;
import entity.Manager;
import entity.Officer;
import repository.*;

public class OfficerImportFromCSVTest {

	public static void main(String[] args) {		
		OfficerRepository officerRepository = new OfficerRepository();
		officerRepository.importFromCSV();
		for (Officer officer : officerRepository.getOfficers()) {
			System.out.println(officer.getName());
		}
	}

}
