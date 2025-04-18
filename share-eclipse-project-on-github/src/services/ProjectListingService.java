package services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	     String openDate; // date format: "DD/MM/YYYY"
	     String closeDate;
	     boolean isVisible;
	     
	     Project newProject = null;
	     

	    Scanner sc = new Scanner(System.in);
	    System.out.println("Creating a new project: ");
	    
	    // check for duplicate project
		System.out.println("Please enter project name: ");
		name = sc.nextLine().trim();
		
		if (projectRepository.searchByName(name) != null) {
			System.out.println("Project already exists. ");
			newProject = projectRepository.searchByName(name);
			return newProject;
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
				openDate = sc.nextLine();
				if (openDate.length() != 10) throw new InputMismatchException();
				break;
				
			} catch (InputMismatchException e) {
				System.out.println("Date format is wrong. ");
			}
		}
		
		// read closeDate
		
		while (true) {
			try {
				System.out.println("Please enter the application closing date of the project (DD/MM/YYYY): ");
				closeDate = sc.nextLine();
				if (closeDate.length() != 10) throw new InputMismatchException();
				
				// check that closing date is after opening date
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date openDateDate = format.parse(openDate);
				Date closeDateDate = format.parse(closeDate);
				if (closeDateDate.compareTo(openDateDate) <= 0) {
					System.out.println("Application closing date must be later than opening date. ");
					continue;
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
		System.out.format("You have successfully created the following project. %n%n" 
						+ "Project name: %s%n"
						+ "Neighbourhood: %s%n"
						+ "Quantity of two-room flats: %d%n"
						+ "Selling price of a two-room flat: SGD %d%n"
						+ "Quantity of three-room flats: %d%n"
						+ "Selling price of a three-room flat: SGD %d%n"
						+ "Application opening date: %s%n"
						+ "Application closing date: %s%n"
						+ "Visibility: project is "
						+ (isVisible? "visible. %n%n" : "not visible. %n%n"), 
						name, neighbourhood, numberOf2Rooms, sellingPrice2Room, numberOf3Rooms, 
						sellingPrice3Room, openDate, closeDate);
		newProject = new Project(name, neighbourhood, numberOf2Rooms,
	    		sellingPrice2Room, numberOf3Rooms, sellingPrice3Room, 
	    		openDate, closeDate, isVisible, manager);
		
//		projectRepository.add(newProject);
		return newProject;
		
	}
	


}
