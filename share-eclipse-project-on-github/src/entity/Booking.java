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

    public Booking(FlatType flatType, Project project, LocalDate bookingDate) {
        this.flatType = flatType;
        this.project = project;
        this.bookingDate = bookingDate;
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
}
