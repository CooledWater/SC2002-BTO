package repository;
import entity.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
public class OfficerRepository extends UserRepository<Officer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6513950936385511860L;
	private List<Officer> officers;
	
	public OfficerRepository() {
		this.officers = new ArrayList<>();
	}
	
	@Override
	public void importFromCSV() {
		String filePath = "csv\\OfficerList.csv";
		Scanner sc;
		try {
			sc = new Scanner(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("OfficerList.csv file is not found. Please ensure that the file is inside csv folder. ");
			return;
		}
		
		sc.nextLine(); // skip the csv list heading
		while(sc.hasNext()) {
			Officer newOfficer = new Officer();
			String line = sc.nextLine();
			String[] parts = line.split(",");
			newOfficer.setName(parts[0]);
			newOfficer.setNRIC(parts[1]);
			newOfficer.setAge(Integer.parseInt(parts[2]));
			newOfficer.setMarried(parts[3].equals("Married")? true : false);
			newOfficer.setPassword(parts[4]);
			this.officers.add(newOfficer);
		}
		sc.close();
	}

	public List<Officer> getOfficers() {
		return officers;
	}

	public void setOfficers(List<Officer> officers) {
		this.officers = officers;
	}

	@Override
	public Officer searchByNRIC(String NRIC) {
		Officer result = null;
		for (Officer officer : this.getOfficers()) {
			if (officer.getNRIC().equals(NRIC)) result = officer;
		}
		
		return result;
	}
}
