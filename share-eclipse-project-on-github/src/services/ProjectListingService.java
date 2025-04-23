package services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import entity.*;
import repository.*;

public class ProjectListingService {
    private final ProjectRepository projectRepository;
	
    public ProjectListingService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    };
	
	public Project createNewProjectListing(Manager manager) {
				
		// initialize attributes
		 String name;
	     String neighbourhood;
	     int numberOf2Rooms;
	     int numberOf3Rooms;
	     int sellingPrice2Room;
	     int sellingPrice3Room;
	     String openDateString; // date format: "DD/MM/YYYY"
	     String closeDateString;
	     Date openDate;
	     Date closeDate;
	     boolean isVisible;
	     
	     Project newProject = null;
	     

	    Scanner sc = new Scanner(System.in);
	    System.out.println("Creating a new project...");
	    
	    // check for duplicate project
		System.out.println("Please enter project name: ");
		name = sc.nextLine().trim();
		
		if (projectRepository.searchByName(name) != null) {
			System.out.println("Project already exists. ");
			return null;
		}
		
		
		System.out.println("Please enter the neighbourhood: ");
		neighbourhood = sc.nextLine();
		
		
		while (true) {
			try {
				System.out.println("Please enter the quantity of two-room flats (must be an integer): ");
				numberOf2Rooms = sc.nextInt();
				if (numberOf2Rooms < 0) throw new ArithmeticException();
				sc.nextLine();
				break;
			}
			catch (InputMismatchException ime) {
				System.out.println("Invalid input. ");
				sc.nextLine();
			}
			catch (ArithmeticException ae) {
				System.out.println("Quantity cannot be less than 0. ");
				sc.nextLine();
			}
		}
		
		while (true) {
			try {
				System.out.println("Please enter the selling price of a two-room flat (must enter an integer): ");
				sellingPrice2Room = sc.nextInt();
				if (sellingPrice2Room < 0) throw new ArithmeticException();
				sc.nextLine();
				break;
			}
			catch (InputMismatchException ime) {
				System.out.println("Invalid input. ");
				sc.nextLine();
			}
			catch (ArithmeticException ae) {
				System.out.println("Selling price cannot be less than 0. ");
				sc.nextLine();
			}
		}
		
		while (true) {
			try {
				System.out.println("Please enter the quantity of three-room flats (must be an integer): ");
				numberOf3Rooms = sc.nextInt();
				if (numberOf3Rooms < 0) throw new ArithmeticException();
				sc.nextLine();
				break;
			}
			catch (InputMismatchException ime) {
				System.out.println("Invalid input. ");
				sc.nextLine();
			}
			catch (ArithmeticException ae) {
				System.out.println("Quantity cannot be less than 0. ");
				sc.nextLine();
			}
		}
		
		
		
		while (true) {
			try {
				System.out.println("Please enter the selling price of a three-room flat (must enter an integer): ");
				sellingPrice3Room = sc.nextInt();
				if (sellingPrice3Room < 0) throw new ArithmeticException();
				sc.nextLine();
				break;
			}
			catch (InputMismatchException ime) {
				System.out.println("Invalid input. ");
			}
			catch (ArithmeticException ae) {
				System.out.println("Selling price cannot be less than 0. ");
			}
			sc.nextLine();
		}
		
		// read openDate
		while (true) {
			try {
				System.out.println("Please enter the application opening date of the project (DD/MM/YYYY): ");
				openDateString = sc.nextLine();
				if (openDateString.length() != 10) throw new InputMismatchException();
				break;
				
			} catch (InputMismatchException e) {
				System.out.println("Date format is wrong. ");
			}
		}
		
		// read closeDate
		
		while (true) {
			try {
				System.out.println("Please enter the application closing date of the project (DD/MM/YYYY): ");
				
				// reading closeDate string
				closeDateString = sc.nextLine();
				if (closeDateString.length() != 10) throw new InputMismatchException();
				
				// check that closing date is after opening date
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				openDate = format.parse(openDateString);
				closeDate = format.parse(closeDateString);
				if (!closeDate.after(openDate)) {
					System.out.println("Application closing date must be later than opening date. ");
					continue;
				}
				
				// check that dates do not overlap with other projects
				List<Project> managerProjects = projectRepository.getProjectsByManager(manager);
		    	for (Project project: managerProjects) {
		    		Date otherOpen = project.getOpenDate();
		    		Date otherClose = project.getCloseDate();
		    		
		    		if (
		    				(!openDate.before(otherOpen)) && (!openDate.after(otherClose)) ||
		    				(!closeDate.before(otherOpen)) && (!closeDate.after(otherClose))
		    			) 
		    		{
		    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    			String otherOpenString = sdf.format(otherOpen);
		    			String otherCloseString = sdf.format(otherClose);
		    			System.out.printf("You are already handling a project from %s to %s%n", otherOpenString, otherCloseString);
		    			return null;
		    		}
		    	}
				break;
				
			} catch (InputMismatchException e) {
				System.out.println("Date format is wrong. ");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// set visibility
		while (true) {
			try {
				System.out.format("Do you wish this project to be visible? %n"
						+ "To make it visible, enter 1 %n"
						+ "To make it not visible, enter 0 %n"
						+ "Please enter: ");
				isVisible = (sc.nextInt() == 1);
				sc.nextLine();
				break;
			} catch (InputMismatchException e){
				System.out.format("Input format is wrong. Please enter 0 or 1. %n%n");
				sc.nextLine();
			}
		}
		// add this Project to ProjectRepository
		newProject = new Project(name, neighbourhood, numberOf2Rooms,
	    		sellingPrice2Room, numberOf3Rooms, sellingPrice3Room, 
	    		openDate, closeDate, isVisible);
		newProject.setManager(manager);
		projectRepository.getProjects().add(newProject);
		
		// decide whether this project should be the active project
		LocalDate localDate = LocalDate.now();
	    	Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date today = Date.from(instant);
		
		if (
				(!today.before(openDate)) && (!today.after(closeDate))
			) {
			// active
			manager.setManagingProj(newProject);
		}
		return newProject;
		
	}
	


}
