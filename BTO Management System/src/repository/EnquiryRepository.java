package repository;
import java.io.ObjectStreamException;
import java.util.*;
import java.util.stream.Collectors;

import entity.*;

public class EnquiryRepository extends Repository {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7676844806533538365L;
	private static EnquiryRepository instance;
	private List<Enquiry> enquiries;
	
	
	public EnquiryRepository() {
		this.enquiries = new ArrayList<Enquiry>();
	}
	
	public static EnquiryRepository getInstance() {
        if (instance == null) {
            instance = new EnquiryRepository();
        }
        return instance;
	}
	
	private Object readResolve() throws ObjectStreamException {
		instance = this;
		return instance;
	}
	
	public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }
	
	
	public List<Enquiry> getAllEnquiries() {
        return new ArrayList<>(enquiries);
    }
	
	
	public List<Enquiry> searchByApplicant(String applicantNRIC) {
		return enquiries.stream()
				.filter(enquiry -> enquiry.getApplicant().getNRIC().equalsIgnoreCase(applicantNRIC))
	            .collect(Collectors.toList());
	}
	
	
	public List<Enquiry> searchByProjectName(String projectName) {
        return enquiries.stream()
                .filter(enquiry -> enquiry.getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }
	
	
	public Enquiry searchByID(String enquiryID) {
		for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getID().equals(enquiryID)) {
                return enquiries.get(i);
            }
        }
        // if no such enquiry found in repository
        System.out.println("Enquiry with ID " + enquiryID + " not found.");
		return null;
    }

	
	public void update(Enquiry updatedEnquiry) {
        for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getID().equals(updatedEnquiry.getID())) {
                enquiries.set(i, updatedEnquiry);
                return; // end early
            }
        }
        // if no such enquiry found in repository
        System.out.println("Enquiry with ID " + updatedEnquiry.getID() + " not found for update.");
    }
	
	
	public boolean delete(String enquiryID) {
        return enquiries.removeIf(enquiry -> enquiry.getID().equals(enquiryID));
    }
		
}
