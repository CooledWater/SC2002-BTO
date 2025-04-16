package test;

import repository.*;
import entity.*;

public class ProjectImportFromCSVTest {

    public static void main(String[] args) {
        ManagerRepository managerRepo = new ManagerRepository();
        managerRepo.importFromCSV();
        
        OfficerRepository officerRepo = new OfficerRepository();
        officerRepo.importFromCSV();
        
        ProjectRepository projectRepo = new ProjectRepository();
        projectRepo.setManagerRepository(managerRepo);
        projectRepo.setOfficerRepository(officerRepo);
        projectRepo.importFromCSV();
        
        System.out.println("=== Projects Loaded ===");
        for (Project project : projectRepo.getProjects()) {
            System.out.println("\nProject: " + project.getName());
            System.out.println("Neighborhood: " + project.getNeighbourhood());
            System.out.println("Manager: " + project.getManager().getName());
            
            System.out.println("Officers (" + project.getOfficers().size() + "):");
            for (Officer officer : project.getOfficers()) {
                System.out.println(" - " + officer.getName());
            }
      
            System.out.println("Application Period: " + project.getOpenDate() + 
                             " to " + project.getCloseDate());
        }
        
        System.out.println("\n=== Search Tests ===");
        Project foundProject = projectRepo.searchByName("Acacia Breeze");
        if (foundProject != null) {
            System.out.println("Found project: " + foundProject.getName());
        } else {
            System.out.println("Project not found");
        }
    }
}