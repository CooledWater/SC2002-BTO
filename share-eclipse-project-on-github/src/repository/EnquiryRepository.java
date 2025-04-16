package services;
import java.util.*;
import java.util.stream.Collectors;
import entity.*;

public class EnquiryRepository extends Repository {
	
	private static final long serialVersionUID = -2660117034435542277L; // to be updated
	private List<Enquiry> enquiries;
	
	
	public EnquiryRepository() {
		this.enquiries = new ArrayList<Enquiry>();
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
	
	
	public Optional<Enquiry> searchByID(int enquiryID) { // this method needs to be edited with timestamp ID, no ID attribute or getID() for enquiry class atm
        return enquiries.stream()
                .filter(enquiry -> enquiry.getID().equals(enquiryID))
                .findFirst();
    }

	
	public void update(Enquiry updatedEnquiry) { // this method also needs to be edited w ID
        for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getID().equals(updatedEnquiry.getID())) {
                enquiries.set(i, updatedEnquiry);
            }
        }
    }
	
	
	public boolean delete(int enquiryID) {
        return enquiries.removeIf(enquiry -> enquiry.getID().equals(enquiryID));
    }
	
}
