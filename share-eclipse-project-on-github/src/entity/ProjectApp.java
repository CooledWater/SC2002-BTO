package entity;

import user.Applicant;

public class ProjectApp {
    private Applicant applicant;
    private Project project;
    private AppStatus status;

    public ProjectApp(Applicant applicant, Project project, AppStatus status) {
        this.applicant = applicant;
        this.project = project;
        this.status = status;
    }
}
