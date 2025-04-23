package entity;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

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
    private Date openDate;
    private Date closeDate;
    private Manager manager;
    private int numberOfOfficers;
    private List<Officer> officers;
    
    
    // other attributes
    private boolean isVisible;
    private List<ProjectApp> projectApps;   

    // this constructor takes in 9 arguments, and initializes all 13 attributes
    public Project(String name, String neighbourhood, int numberOf2Rooms,
    		int sellingPrice2Room, int numberOf3Rooms, int sellingPriceOf3Room, 
    		Date openDate, Date closeDate, boolean isVisible) { 
    	this.name = name;
    	this.neighbourhood = neighbourhood;
    	this.numberOf2Rooms = numberOf2Rooms;
    	this.sellingPrice2Room = sellingPrice2Room;
    	this.numberOf3Rooms = numberOf3Rooms;
    	this.sellingPrice3Room = sellingPriceOf3Room;
    	this.openDate = openDate;
    	this.closeDate = closeDate;
    	this.isVisible = isVisible;
    	
    	// null initialization
    	this.manager = null;
    	this.numberOfOfficers = 0;
    	this.officers = new ArrayList<>();
    	this.projectApps = new ArrayList<>();
    }

    
  //method to toggle visibility
  	public void toggleVisibility() {
  	    this.isVisible = !this.isVisible;
  	    System.out.println("Project visibility is now: " + (this.isVisible ? "Visible" : "Hidden"));
  	}
	public boolean isVisible() {
	    LocalDate today = LocalDate.now();
	    LocalDate open = openDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate close = closeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    
	    // Project is visible only if manually set to visible AND today's date is within the open/close window
	    return this.isVisible && !today.isBefore(open) && !today.isAfter(close);
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
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
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
			return "No officer";
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

	public int getSellingPrice2Room() {
		return sellingPrice2Room;
	}


	public void setSellingPrice2Room(int sellingPrice2Room) {
		this.sellingPrice2Room = sellingPrice2Room;
	}


	public int getSellingPrice3Room() {
		return sellingPrice3Room;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String openDateString = sdf.format(this.openDate);
		String closeDateString = sdf.format(this.closeDate);
		
		return String.format(
		  "%nProject Name: " + this.getName()
        + "%nNeighbourhood: " + this.getNeighbourhood()
        + "%nNumber of available 2-room units: " + this.getNumberOf2Rooms()
        + "%nSelling price of 2-room unit: SGD " + this.sellingPrice2Room
        + "%nNumber of available 3-room units: " + this.getNumberOf3Rooms()
        + "%nSelling price of 3-room unit: SGD " + this.sellingPrice3Room
        + "%nApplication Open Date: " + openDateString
        + "%nApplication Close Date: " + closeDateString
        + "%nVisiblility: " + (isVisible? "Visible" : "Hidden")
        + "%nManager: " + this.manager.getName()
		+ "%nOfficers: " + this.getOfficerNameStrings()
		);
	}


	public void setSellingPrice3Room(int sellingPrice3Room) {
		this.sellingPrice3Room = sellingPrice3Room;
	}


}
