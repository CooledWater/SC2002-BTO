package entity;

public class ProjectApp {
    private Applicant applicant;
    private Project project;
    private AppStatus status;

    public ProjectApp(Applicant applicant, Project project, AppStatus status) {
        this.applicant = applicant;
        this.project = project;
        this.status = status;
    }
    
    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public AppStatus getStatus() {
        return status;
    }
}
