package entity;

import java.util.*;
import java.io.*;

public class Project implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5671995836305073390L;
	// attributes defined in csv file
    private String name;
    private String neighbourhood;
    private int numberOf2Rooms;
    private int numberOf3Rooms;
    private int sellingPrice2Room;
    private int sellingPrice3Room;
    private String openDate;
    private String closeDate;
    private Manager manager;
    private int numberOfOfficers;
    private List<Officer> officers;
    
    
    // other attributes
    private boolean isVisible;
    private List<ProjectApp> projectApps;
    
//    // this constructor can be used while importing from CSV
//    public Project(String name, String neighbourhood, int numberOf2Rooms,
//    		int sellingPrice2Room, int numberOf3Rooms, int sellingPriceOf3Room, 
//    		String openDate, String closeDate, Manager manager, int numberOfOfficers,
//    		List<Officer> officers) { 
//    	this.name = name;
//    	this.neighbourhood = neighbourhood;
//    	this.numberOf2Rooms = numberOf2Rooms;
//    	this.sellingPrice2Room = sellingPrice2Room;
//    	this.numberOf3Rooms = numberOf3Rooms;
//    	this.sellingPrice3Room = sellingPriceOf3Room;
//    	this.openDate = openDate;
//    	this.closeDate = closeDate;
//    	this.manager = manager;
//    	this.numberOfOfficers = numberOfOfficers;
//    	this.officers = officers;
//    	
//    	this.isVisible = true;
//    	this.projectApps = new ArrayList<>();
//    }
    

    // this constructor takes in 10 arguments, and initializes all 13 attributes
    public Project(String name, String neighbourhood, int numberOf2Rooms,
    		int sellingPrice2Room, int numberOf3Rooms, int sellingPriceOf3Room, 
    		String openDate, String closeDate, boolean isVisible, Manager manager) { 
    	this.name = name;
    	this.neighbourhood = neighbourhood;
    	this.numberOf2Rooms = numberOf2Rooms;
    	this.sellingPrice2Room = sellingPrice2Room;
    	this.numberOf3Rooms = numberOf3Rooms;
    	this.sellingPrice3Room = sellingPriceOf3Room;
    	this.openDate = openDate;
    	this.closeDate = closeDate;
    	this.isVisible = isVisible;
    	this.manager = manager;
    	
    	// null initialization
    	this.numberOfOfficers = 0;
    	this.officers = new ArrayList<>();
    	this.projectApps = new ArrayList<>();
    }


	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(User user, boolean isVisible) {
		if (user instanceof Manager) { // check user identity
			this.isVisible = isVisible;
		} else {
			System.out.println("Unauthorized to change visibility. ");
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNeighbourhood() {
		return neighbourhood;
	}
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	public int getNumberOf2Rooms() {
		return numberOf2Rooms;
	}
	public void setNumberOf2Rooms(int numberOf2Rooms) {
		this.numberOf2Rooms = numberOf2Rooms;
	}
	public int getNumberOf3Rooms() {
		return numberOf3Rooms;
	}
	public void setNumberOf3Rooms(int numberof3Rooms) {
		this.numberOf3Rooms = numberof3Rooms;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public List<Officer> getOfficers() {
		return officers;
	}
	
	private String getOfficerNameStrings() {
		String result = "";
		if (officers.size() == 0) {
			return result;
		}
		for (Officer officer: officers) {
			result += (officer.getName() + ", ");
		}
		result = result.substring(0, result.length()-2);
		
		return result;
	}
	public void setOfficers(List<Officer> officers) {
		this.officers = officers;
	}
	public int getNumberOfOfficers() {
		return numberOfOfficers;
	}
	public void setNumberOfOfficers(int numberOfOfficers) {
		this.numberOfOfficers = numberOfOfficers;
	}
	public List<ProjectApp> getProjectApps() {
		return projectApps;
	}
	public void setProjectApps(List<ProjectApp> projectApps) {
		this.projectApps = projectApps;
	}
	//methods to handle no. of 2 and 3 rooms
	public void decrementTwoRoom() {
	    if (this.numberOf2Rooms > 0) {
	        this.numberOf2Rooms--;
	    } else {
	        System.out.println("No two-room flats remaining.");
	    }
	}
	public void decrementThreeRoom() {
	    if (this.numberOf3Rooms > 0) {
	        this.numberOf3Rooms--;
	    } else {
	        System.out.println("No three-room flats remaining.");
	    }
	}
	
	//method to add projectapp
	public void addProjectApp(ProjectApp projectApp) {
	    this.projectApps.add(projectApp);
	}
	//method to toggle visibility
	public void toggleVisibility() {
	    this.isVisible = !this.isVisible;
	    System.out.println("Project visibility is now: " + (this.isVisible ? "Visible" : "Hidden"));
	}

	@Override
	public String toString() {
		return String.format(
		  "Project Name: " + this.getName()
        + "%nNeighbourhood: " + this.getNeighbourhood()
        + "%nNumber of available 2-Room Units: " + this.getNumberOf2Rooms()
        + "%nNumber of available 3-Room Units: " + this.getNumberOf3Rooms()
        + "%nOpen Date: " + this.getOpenDate()
        + "%nClose Date: " + this.getCloseDate()
        + "%nVisible: " + this.isVisible()
		+ "%nOfficers: " + this.getOfficerNameStrings()
		);
	}


}
