package services;

import entity.Applicant;
import entity.AppStatus;
import entity.ProjectApp;
import entity.FlatType;
import repository.ApplicantRepository;

import java.util.Scanner;
import java.util.function.Predicate;

public class ReportService {
    private ApplicantRepository applicantRepository;

    public ReportService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public void generateFilteredApplicantReport() {
        Scanner sc = new Scanner(System.in);
        Predicate<Applicant> filter = a -> a.getProjectApp() != null && a.getProjectApp().getStatus() == AppStatus.BOOKED;

        System.out.println("=== Filter Options ===");

        // Marital Status Filter
        while (true) {
            try {
                System.out.println("Filter by marital status?");
                System.out.println("Enter 0 for filtering by single");
                System.out.println("Enter 1 for filtering by married");
                System.out.println("Enter 2 for no filtering. ");
                System.out.print("Enter choice: ");
                int maritalChoice = Integer.parseInt(sc.nextLine());

                if (maritalChoice == 0) {
                    filter = filter.and(a -> !a.isMarried());
                    break;
                } else if (maritalChoice == 1) {
                    filter = filter.and(Applicant::isMarried);
                    break;
                } else if (maritalChoice == 2) {
                    break; // no filtering
                } else {
                    System.out.println("Invalid input. Please enter 0, 1, or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Flat Type Filter
        while (true) {
            try {
                System.out.println("Filter by flat type?");
                System.out.println("0 - TWO_ROOM");
                System.out.println("1 - THREE_ROOM");
                System.out.println("2 - No filtering");
                System.out.print("Enter choice: ");
                int flatChoice = Integer.parseInt(sc.nextLine());

                if (flatChoice == 0) {
                    filter = filter.and(a -> a.getProjectApp().getFlatType() == FlatType.TWO_ROOM);
                    break;
                } else if (flatChoice == 1) {
                    filter = filter.and(a -> a.getProjectApp().getFlatType() == FlatType.THREE_ROOM);
                    break;
                } else if (flatChoice == 2) {
                    break;
                } else {
                    System.out.println("Please enter 0, 1, or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Neighbourhood Filter
        while (true) {
            System.out.print("Do you want to filter by neighbourhood? (y/n): ");
            String neighInput = sc.nextLine().trim().toLowerCase();
            if (neighInput.equals("y")) {
                System.out.print("Enter neighbourhood: ");
                String neighbourhood = sc.nextLine().trim();
                filter = filter.and(a -> a.getProjectApp().getProject().getNeighbourhood().equalsIgnoreCase(neighbourhood));
                break;
            } else if (neighInput.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }

        // Apply the filter and print the report
        System.out.println("\n=== Filtered Applicant Report ===");
        boolean anyFound = false;
        for (Applicant applicant : applicantRepository.getApplicants()) {
            if (filter.test(applicant)) {
                ProjectApp app = applicant.getProjectApp();
                System.out.println("NRIC: " + applicant.getNRIC());
                System.out.println("Age: " + applicant.getAge());
                System.out.println("Marital Status: " + (applicant.isMarried() ? "Married" : "Single"));
                System.out.println("Flat Type: " + 
                	    (app.getFlatType() != null ? app.getFlatType() : "N/A"));
                System.out.println("Project Name: " + app.getProject().getName());
                System.out.println("Neighbourhood: " + app.getProject().getNeighbourhood());
                System.out.println("-----------------------------------");
                anyFound = true;
            }
        }

        if (!anyFound) {
            System.out.println("No applicants found matching the filters.");
        }
    }
}

