package repository;

import entity.AppStatus;
import entity.ProjectApp;
import entity.Applicant;

public class ReceiptRepository extends Repository {
    private static final long serialVersionUID = -2825934112690495137L;

    public void generateReceipt(Applicant applicant) {
        ProjectApp app = applicant.getProjectApp();

        if (app != null && app.getStatus() == AppStatus.BOOKED) {
            System.out.println("\n=== Booking Receipt ===");
            System.out.println("Applicant Name: " + applicant.getNRIC());
            System.out.println("Age: " + applicant.getAge());
            System.out.println("Marital Status: " + (applicant.isMarried() ? "Married" : "Single"));
            System.out.println("Flat Type Booked: " + applicant.getBooking().getFlatType());
            System.out.println("Project Name: " + app.getProject().getName());
        } else {
            System.out.println("No booked project found for applicant: " + applicant.getNRIC());
        }
    }
}
