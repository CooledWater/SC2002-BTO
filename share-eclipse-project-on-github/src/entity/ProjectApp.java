package entity;

public class ProjectApp {
    private Applicant applicant;
    private Project project;
    private AppStatus status;
    private FlatType flatType;

    public ProjectApp(Applicant applicant, Project project, AppStatus status, FlatType flatType) {
        this.applicant = applicant;
        this.project = project;
        this.status = status;
        this.flatType = flatType;
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
    
    public void setStatus(AppStatus status) {
        this.status = status;
    }
    
    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    @Override
    public String toString() {
        return "ProjectApp{" + "applicant=" + applicant.getNRIC() + ", project=" + project.getName() + ", status=" + status + '}';
    }
    
}
