package repository;
import java.util.*;
import java.io.*;

import entity.*;

public class ProjectRepository extends Repository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3057705536705508172L;
	private static ProjectRepository instance;
	
	private List<Project> projects;   
    
	public ProjectRepository() {
		this.projects = new ArrayList<Project>(); 
	}    
	
	public static ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
	}
	
	private Object readResolve() throws ObjectStreamException {
		instance = this;
		return instance;
	}

    
	public void importFromCSV(ManagerRepository managerRepo, OfficerRepository officerRepo) {
		String filePath = "csv\\ProjectList.csv";
		Scanner sc; 
		try { 
			sc = new Scanner(new FileReader(filePath));
			
		}catch (FileNotFoundException e ) { 
			System.out.println("ProjectList.csv file is not found. Please ensure that the file is inside csv folder. ");
            return;
		}
		
		
		sc.nextLine();
		while(sc.hasNext()) {
			String line = sc.nextLine();
			String[] parts = line.split(",");
			
			Manager manager = managerRepo.searchByName(parts[10].trim());
			
			// constructor initialize 10 attributes
			Project project = new Project(
	                parts[0].trim(), 
	                parts[1].trim(),
	                Integer.parseInt(parts[3].trim()), 
	                Integer.parseInt(parts[4].trim()), 
	                Integer.parseInt(parts[6].trim()),
	                Integer.parseInt(parts[7].trim()), 
	                parts[8].trim(),
	                parts[9].trim(),
	                true,
	                manager
	            );
			if (manager != null) {
				manager.setManagingProj(project);
			}
			
			 // initialize number of officers
			 int numOfficers = Integer.parseInt(parts[11].trim());
			 project.setNumberOfOfficers(numOfficers);
			 
			 // initialize list of officers
			 if (numOfficers > 1) { 
	                for (int i = 12; i < 12+numOfficers; i++) {
	                	String officerName = parts[i];
	                	if (i == 12) {officerName = officerName.substring(1,officerName.length());}
	                	if (i == 12+numOfficers-1) {officerName = officerName.substring(0,officerName.length()-1);}
	                    Officer officer = officerRepo.searchByName(officerName.trim());
	                    if (officer != null) {
	                        project.getOfficers().add(officer);
	                        officer.setHandlingProj(project);
	                    }                	
	                }
			 }
			 if (numOfficers == 1) {
				 Officer officer = officerRepo.searchByName(parts[12].trim());
                 if (officer != null) {
                     project.getOfficers().add(officer);
                     officer.setHandlingProj(project);
                 } 
			 }
	         projects.add(project);
		}
		sc.close();
	}
	

	
	
	public List<Project> getProjects() {
		return projects;
    }

	
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    
    public Project searchByName(String name) {
        for (Project project : this.getProjects()) {
            if (project.getName().equalsIgnoreCase(name.trim())) {
                return project;
            }
        }
        return null;
    }
    
    
    public List<Project> getVisibleProjects() {
        List<Project> visibleProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.isVisible()) {
                visibleProjects.add(project);
            }
        }
        return visibleProjects;
    }
    
    
    public List<Project> getProjectsByManager(Manager manager) {
        List<Project> result = new ArrayList<>();
        for (Project p : projects) {
            if (p.getManager() != null && p.getManager().equals(manager)) {
                result.add(p);
            }
        }
        return result;
    }	
}
