package services;

import entity.Applicant;
import entity.AppStatus;
import entity.Project;
import entity.ProjectApp;
import repository.ApplicantRepository;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportService {
    private ApplicantRepository applicantRepository;

    public ReportService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public void generateFilteredApplicantReport() {
        Scanner sc = new Scanner(System.in);
        Predicate<Applicant> filter = applicant -> {
            ProjectApp app = applicant.getProjectApp();
            return app != null && app.getStatus() == AppStatus.BOOKED;
        };

        System.out.println("\n=== Applicant Report Filters ===");
        System.out.println("Press Enter to skip any filter.\n");

        try {
            System.out.print("Filter by marital status? (single/married): ");
            String maritalStatus = sc.nextLine().trim().toLowerCase();
            if (!maritalStatus.isEmpty()) {
                if (!maritalStatus.equals("single") && !maritalStatus.equals("married")) {
                    throw new IllegalArgumentException("Invalid marital status input.");
                }
                boolean isMarried = maritalStatus.equals("married");
                filter = filter.and(a -> a.isMarried() == isMarried);
            }

            System.out.print("Filter by flat type? (TWO_ROOM/THREE_ROOM): ");
            String flatType = sc.nextLine().trim().toUpperCase();
            if (!flatType.isEmpty()) {
                if (!flatType.equals("TWO_ROOM") && !flatType.equals("THREE_ROOM")) {
                    throw new IllegalArgumentException("Invalid flat type input.");
                }
                filter = filter.and(a -> a.getProjectApp().getFlatType().name().equals(flatType));
            }

            System.out.print("Filter by neighbourhood? (enter name): ");
            String neighbourhood = sc.nextLine().trim();
            if (!neighbourhood.isEmpty()) {
                filter = filter.and(a -> a.getProjectApp().getProject().getNeighbourhood().equalsIgnoreCase(neighbourhood));
            }

            List<Applicant> filteredApplicants = applicantRepository.getApplicants()
                    .stream()
                    .filter(filter)
                    .collect(Collectors.toList());

            if (filteredApplicants.isEmpty()) {
                System.out.println("\nNo applicants matched the selected filters.");
            } else {
                System.out.println("\n=== Filtered Applicant Report ===");
                for (Applicant a : filteredApplicants) {
                    ProjectApp app = a.getProjectApp();
                    Project project = app.getProject();
                    System.out.printf("NRIC: %s, Age: %d, Marital Status: %s, Flat Type: %s, Project Name: %s%n",
                            a.getNRIC(),
                            a.getAge(),
                            a.isMarried() ? "Married" : "Single",
                            app.getFlatType(),
                            project.getName());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred while generating report: " + e.getMessage());
        }
    }
}
