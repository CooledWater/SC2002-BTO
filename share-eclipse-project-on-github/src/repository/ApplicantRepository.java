package repository;
import java.util.*;
import java.io.*;

import entity.Applicant;
import entity.Manager;

public class ApplicantRepository extends UserRepository<Applicant> {

	private static final long serialVersionUID = -4139573877991643327L; // to be edited
	private List<Applicant> applicants;
	
	public ApplicantRepository() {
		this.applicants = new ArrayList<Applicant>();
	}
	
	public void importFromCSV() {
		String filePath = "csv\\ApplicantList.csv";
		Scanner sc;
		try {
			sc = new Scanner(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ApplicantList.csv file is not found. Please ensure that the file is inside csv folder. ");
			return;
		}
		
		sc.nextLine(); // skip the csv list heading
		while(sc.hasNext()) {
			Applicant newApplicant = new Applicant();
			String line = sc.nextLine();
			String[] parts = line.split(",");
			newApplicant.setName(parts[0]);
			newApplicant.setNRIC(parts[1]);
			newApplicant.setAge(Integer.parseInt(parts[2]));
			newApplicant.setMarried(parts[3].equals("Married")? true : false);
			newApplicant.setPassword(parts[4]);
			this.applicants.add(newApplicant);
			
		}
		
		sc.close();
	}
	
	
	
	public List<Applicant> getApplicants() {
		return applicants;
	}


	@Override
	public Applicant searchByNRIC(String NRIC) {
		Applicant result = null;
		for (Applicant applicant : applicants) {
			if (applicant.getNRIC().equals(NRIC)) result = applicant;
		}
		
		return result;
	}
	
}
