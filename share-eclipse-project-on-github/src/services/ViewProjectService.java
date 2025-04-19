package services;

import entity.*;
import repository.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ViewProjectService {
	// Attributes: list of all projects from repo
	//			   map to save filters even if user change menu
    private List<Project> allProjects;
    private Map<String, String> savedFilters;

    public ViewProjectService(List<Project> allProjects) {
        this.allProjects = allProjects;
        this.savedFilters = new HashMap<>();
    }

    public void viewProjectsAsManager(Manager manager) {
        showFilterMenu();
        List<Project> filtered = applyFilters(allProjects);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Show only my projects? (y/n): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("y")) {
                filtered = filtered.stream()
                        .filter(p -> p.getManager().equals(manager))
                        .collect(Collectors.toList());
                break;
            } else if (input.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }

        printProjects(filtered);
    }

    public void viewProjectsAsOfficer(Officer officer) {
        List<Project> handledProjects = allProjects.stream()
                .filter(p -> p.getOfficers().contains(officer))
                .collect(Collectors.toList());
        showFilterMenu();
        List<Project> filtered = applyFilters(handledProjects);
        printProjects(filtered);
    }

    public void viewProjectsAsApplicant(Applicant applicant) {
        showFilterMenu();
        List<Project> visibleProjects = allProjects.stream()
                .filter(Project::isVisible)
                .filter(p -> applicant.isMarried() || p.getNumberOf2Rooms() > 0)
                .collect(Collectors.toList());

        List<Project> filtered = applyFilters(visibleProjects);
        printProjects(filtered);
    }

    private void showFilterMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Filter by neighbourhood (leave blank for no filter): ");
        String neighbourhood = sc.nextLine().trim();
        savedFilters.put("neighbourhood", neighbourhood);
        
        while (true) {
            System.out.println("Filter by flat type?");
            System.out.println("Enter 2 to view 2 room flats.");
            System.out.println("Enter 3 to view 3 room flats.");
            System.out.println("Enter 0 to view all flats.");
            System.out.print("Enter choice: ");
            String flatChoice = sc.nextLine().trim();

            if (flatChoice.equals("2") || flatChoice.equals("3") || flatChoice.equals("0")) {
                savedFilters.put("flatType", flatChoice);
                break;
            } else {
                System.out.println("Invalid input. Please enter 2, 3 or 0.");
            }
        }
    }

    private List<Project> applyFilters(List<Project> projects) {
        String neighbourhood = savedFilters.get("neighbourhood");
        String flatChoice = savedFilters.get("flatType");

        if (neighbourhood != null && !neighbourhood.isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getNeighbourhood().equalsIgnoreCase(neighbourhood))
                    .collect(Collectors.toList());
        }

        if (flatChoice != null) {
            switch (flatChoice) {
                case "2":
                    projects = projects.stream()
                            .filter(p -> p.getNumberOf2Rooms() > 0)
                            .collect(Collectors.toList());
                    break;
                case "3":
                    projects = projects.stream()
                            .filter(p -> p.getNumberOf3Rooms() > 0)
                            .collect(Collectors.toList());
                    break;
                case "0":
                default:
                    break;
            }
        }

        Comparator<Project> comparator;

        if ("0".equals(flatChoice)) {
            comparator = Comparator
                .comparingInt(Project::getNumberOf2Rooms)
                .reversed()
                .thenComparing(Project::getName);
        } else if ("1".equals(flatChoice)) {
            comparator = Comparator
                .comparingInt(Project::getNumberOf3Rooms)
                .reversed()
                .thenComparing(Project::getName);
        } else {
            comparator = Comparator.comparing(Project::getName);
        }

        projects.sort(comparator);



        return projects;
    }

    private void printProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects found with the current filters.");
            return;
        }

        System.out.println("\n=== Project List ===");
        for (Project p : projects) {
            System.out.println("Project Name: " + p.getName());
            System.out.println("Neighbourhood: " + p.getNeighbourhood());
            System.out.println("2-Room Units: " + p.getNumberOf2Rooms());
            System.out.println("3-Room Units: " + p.getNumberOf3Rooms());
            System.out.println("Open Date: " + p.getOpenDate());
            System.out.println("Close Date: " + p.getCloseDate());
            System.out.println("Visible: " + p.isVisible());
            System.out.println("-----------------------------");
        }
    }

    public Project getProjectByName(String projName) {
        return allProjects.stream()
                .filter(p -> p.getName().equalsIgnoreCase(projName))
                .findFirst()
                .orElse(null);
    }
}
