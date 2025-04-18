package interfaces;

import java.util.*;
import entity.*;
import services.*;

// consider UserInterface
public interface OfficerInterface extends ApplicantInterface {
	
    // --- Applicant Capabilities (as Officer possesses all applicant's capabilities) ---

    List<Project> viewAvailableProjects(Officer officer);

    boolean applyForProject(Officer officer, Project project);
    // will check if project is being handled by officer

    String viewAppliedProjectStatus(Officer officer);

    boolean bookFlat(Officer officer, Project project, String flatType); // Booking through their own action

    boolean requestWithdrawal(Officer officer, Project project);

    boolean submitEnquiry(Officer officer, Project project, String message);

    List<Enquiry> viewMyEnquiries(Officer officer);

    boolean editEnquiry(Officer officer, String enquiryId, String newMessage);

    boolean deleteEnquiry(Officer officer, String enquiryId);

    // --- HDB Officer Specific Capabilities ---

    /**
     * Registers an officer to join a project, subject to approval.
     * @return true if the registration request was successfully created, false otherwise.
    boolean registerAsOfficer(Officer officer, Project project);

    /**
     * Views the registration status to be an HDB Officer for a project.
    String viewOfficerRegistrationStatus(Officer officer, Project project);
    Do we need a list of projects which the officer registered for (not accepted)

    /**
     * Views the details of the project the officer is handling.
     * @return The details of the project, null if officer not handling
    Project viewHandlingProjectDetails(Officer officer);

    /**
     * Views enquiries regarding the project the officer is handling.
    List<Enquiry> viewProjectEnquiries(Officer officer);

    /**
     * Replies to a specific enquiry regarding the project the officer is handling.
     * @return True if the reply was successful.
    boolean replyToEnquiry(Officer officer, String enquiryId, String response);

	/**
     * Updates an applicant's project status to "Booked".
     * @return True if the status update was successful.
     * Should trigger bottom three methods in some other class
    boolean updateApplicationStatusToBooked(Officer officer, Applicant applicant);

	 /**
     * Generates a receipt of the applicants with their flat booking details for a project.
     * @return A string containing the receipt details. maybe void?
    String generateBookingReceipt(Officer officer, Applicant applicant);



    /**
     * Updates the number of remaining flats for a specific flat type in a project.
     * @param project The project to update.
     * @param flatType The type of flat.
     * @param remainingFlats The new number of remaining flats.
     * @return True if the update was successful.
    boolean updateRemainingFlats(Officer officer, Project project, String flatType, int remainingFlats);

    /**
     * Retrieves an applicant's BTO application details using their NRIC.
     * @param applicantNric The NRIC of the applicant.
     * @return Information about the applicant's application.
     */
    String retrieveApplicantApplication(Officer officer, String applicantNric);

    

    /**
     * Updates an applicant's profile with the type of flat chosen under a project.
     * @param applicantNric The NRIC of the applicant.
     * @param project The project.
     * @param flatType The type of flat booked.
     * @return True if the profile update was successful.
     */
    boolean updateApplicantFlatType(Officer officer, String applicantNric, Project project, String flatType);



    // --- Filtering ---

    List<Project> filterProjects(Officer officer, java.util.Map<String, String> filters);

    void setFilterSettings(Officer officer, java.util.Map<String, String> filters);

    java.util.Map<String, String> getFilterSettings(Officer officer);

    // --- Utility ---

    void displayProjects(List<Project> projects); // use printprojects() in viewProjectService

    void displayEnquiries(List<Enquiry> enquiries); // use default method in enquiryserviceinterface
}