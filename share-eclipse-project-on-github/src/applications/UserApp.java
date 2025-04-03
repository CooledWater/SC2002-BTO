package applications;

import models.Applicant;
import models.Project;
import enums.FlatType;

public class UserApp {
    public static void main(String[] args) {
        Applicant applicant = new Applicant("S1234567A", "password123", false, 30);
        Project project = new Project("Tampines Green", "Tampines", FlatType.THREE_ROOM, "2025-01-01", "2025-06-01", "Mr. Tan");
        applicant.applyProject(project);
        System.out.println("Application submitted for project: " + project.getName());
    }
}
