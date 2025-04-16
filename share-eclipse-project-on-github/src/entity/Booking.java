package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2344321301092437498L;
	private FlatType flatType;
    private Project project;
    private LocalDate bookingDate;
    private AppStatus status;

    public Booking(FlatType flatType, Project project, LocalDate bookingDate, AppStatus status) {
        this.flatType = flatType;
        this.project = project;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public FlatType getFlatType() {
        return flatType;
    }
    
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "Booking [Flat Type=" + flatType + ", Project=" + project.getName() + "]";
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public AppStatus getStatus() {
    	return status;
    }
   
    public void setStatus(AppStatus status) {
    	this.status = status;
    }
}
