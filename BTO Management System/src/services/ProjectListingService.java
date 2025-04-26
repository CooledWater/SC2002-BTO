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
	
	public void createNewProjectListing(Manager manager) {
				
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
			System.out.println("Project creation failed. ");
			return;
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
		    			System.out.println("Project creation failed. ");
		    			return;
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
		System.out.format("%n================================="
				+ "%nNew project is created: "
				+ newProject.toString()
				+ "%n%n");
		return;
	}
	
	public void editProjectListing(Manager manager, Scanner sc) throws ParseException {
		System.out.println("Enter the name of the project which you wish to edit: ");
		String projectName = sc.nextLine().trim();
		Project project = projectRepository.searchByName(projectName);
		if (project == null) {
			System.out.println("Project cannot be found. Edit failed. ");
			return;
		}
		if (project.getManager() != manager) {
			System.out.println("You are not authorized. Edit failed. ");
			return;
		}
		
		// print menu
		System.out.println("=================Edit Project Details=================");
		System.out.println("1. Change project name");
		System.out.println("2. Change neighbourhood");
		System.out.println("3. Change the selling price of 2-room flats");
		System.out.println("4. Change the selling price of 3-room flats");
		System.out.println("5. Change application closing date");
		System.out.println("Enter an integer to choose which detail you wish to edit: ");
		int choice = -1;
		
		while (true) {
	    		try {
	            choice = Integer.parseInt(sc.nextLine());
	            if ((0 <= choice) && (choice <= 5)) {break;}
	            else {System.out.println("Invalid input. Please try again. ");}
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please try again. ");
	        }
		}
		
		switch (choice) {
		case 0: 
			System.out.println("Cancelled edit. ");
			break;
		case 1: 
			System.out.println("Please enter new project name: ");
			String name = sc.nextLine().trim();
			project.setName(name);
			break;
		case 2: 
			System.out.println("Please enter new neighbourhood: ");
			String neighbourhood = sc.nextLine().trim();
			project.setNeighbourhood(neighbourhood);
			break;
		case 3: 
			System.out.println("Please enter new price of two-room flats: ");
			int price2 = Integer.parseInt(sc.nextLine());
			project.setSellingPrice2Room(price2);
			break;
		case 4: 
			System.out.println("Please enter new price of three-room flats: ");
			int price3 = Integer.parseInt(sc.nextLine());
			project.setSellingPrice3Room(price3);
			break;
		case 5: 
			System.out.println("Please enter the new application closing date: ");
			String closeDateString = sc.nextLine().trim();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date closeDate = format.parse(closeDateString);
			project.setCloseDate(closeDate);
			break;
		default:
			System.out.println("invalid choice. Edit failed. ");
			return;
		}
		System.out.println("Edit successful. ");
		return;
	}
	
	public void deleteProjectListing(Manager manager, Scanner sc) {
		System.out.println("Enter the name of the project which you wish to delete: ");
		String projectName = sc.nextLine().trim();
		Project project = projectRepository.searchByName(projectName);
		if (project == null) {
			System.out.println("Project cannot be found. Deletion failed. ");
			return;
		}
		if (project.getManager() != manager) {
			System.out.println("You are not authorized. Deletion failed. ");
			return;
		}
		if (project.getProjectApps().size() != 0) {
			System.out.println("There are already people who applied for this project. Deletion failed. ");
			return;
		}
		if (project.getOfficers().size() != 0) {
			System.out.println("There are already officers in charge of this project. Deletion failed. ");
			return;
		}
		
		projectRepository.getProjects().remove(project);
		if (manager.getManagingProj() == project) { // should be kept as .getHandlingProj(), to remove managingProj upon deletion
			manager.setManagingProj(null);
		}
		System.out.println("Project was successfully deleted. ");
		return;
		
	}

}
