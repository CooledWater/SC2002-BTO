package repository;

import entity.AppStatus;
import entity.ProjectApp;
import entity.Applicant;

class ReceiptRepository extends Repository {
    private static final long serialVersionUID = 1L;
    
    public void generateReceipt(Applicant applicant) {
        for (ProjectApp app : applicant.getProjectApps()) {
            if (app.getStatus() == AppStatus.BOOKED) {
                System.out.println("\n=== Booking Receipt ===");
                System.out.println("Applicant Name: " + applicant.getNRIC());
                System.out.println("Age: " + applicant.getAge());
                System.out.println("Marital Status: " + (applicant.isMarried() ? "Married" : "Single"));
                System.out.println("Flat Type Booked: " + app.getProject().getFlatType());
                System.out.println("Project Name: " + app.getProject().getName());
            }
        }
    }
}
