package repository;

import java.util.*;
import java.io.*;

import entity.Applicant;
import entity.Officer;

public class ApplicantRepository extends UserRepository<Applicant> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4017571295614508912L;
	private static ApplicantRepository instance;
	private List<Applicant> applicants;
	
	public ApplicantRepository() {
		this.applicants = new ArrayList<Applicant>();
	}
	
	public static ApplicantRepository getInstance() {
        if (instance == null) {
            instance = new ApplicantRepository();
        }
        return instance;
	}
	
	private Object readResolve() throws ObjectStreamException {
		instance = this;
		return instance;
	}
	
	@Override
	public void importFromCSV() {
		String filePath = "csv\\ApplicantList.csv";
		Scanner sc;
		try {
			sc = new Scanner(new FileReader(filePath));
		} catch (FileNotFoundException e) {
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


	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}


	@Override
	public Applicant searchByNRIC(String NRIC) {
		Applicant result = null;
		for (Applicant applicant : applicants) {
			if (applicant.getNRIC().equals(NRIC)) result = applicant;
		}
		
		return result;
	}
	
	@Override
	public Applicant searchByName(String name) {
	    for (Applicant applicant: this.getApplicants()) {
	        if (applicant.getName().equalsIgnoreCase(name.trim())) {
	            return applicant;
	        }
	    }
	    return null;
	}
}
