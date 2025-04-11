package repository;

import java.util.*;
import entity.AppStatus;
import entity.ProjectApp;
import entity.Applicant;
import entity.Project;


class ReceiptRepository extends Repository {
    private static final long serialVersionUID = 1L;
    private List<ProjectApp> receipts = new ArrayList<>();

    public void addReceipt(ProjectApp app) {
        if (app.getStatus() == AppStatus.BOOKED) {
            receipts.add(app);
        }
    }

    public void printReceipts() {
        for (ProjectApp app : receipts) {
            Applicant applicant = app.getApplicant();
            Project project = app.getProject();

            System.out.println("=== RECEIPT ===");
            System.out.println("Name: " + applicant.getNRIC()); // Assuming name is NRIC for now
            System.out.println("NRIC: " + applicant.getNRIC());
            System.out.println("Age: " + applicant.getAge());
            System.out.println("Marital Status: " + (applicant.isMarried() ? "Married" : "Single"));
            System.out.println("Flat Type: " + project.getFlatType());
            System.out.println("Project Name: " + project.getName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            System.out.println("Opening Date: " + project.getOpenDate());
            System.out.println("Closing Date: " + project.getCloseDate());
            System.out.println("Project Manager: " + project.getManagerName());
            System.out.println("================\n");
        }
    }
}
