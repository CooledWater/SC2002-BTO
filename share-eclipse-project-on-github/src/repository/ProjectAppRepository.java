package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectAppRepository extends Repository {

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 323802748759648113L;
	private List<ProjectApp> projectApps;

    public ProjectAppRepository() {
        this.projectApps = new ArrayList<>();
    }

    public void add(ProjectApp projectApp) {
        this.projectApps.add(projectApp);
        this.saveToSer(); // Optional: Auto-save after each add
    }

    public List<ProjectApp> getProjectApps() {
        return projectApps;
    }

    public void setProjectApps(List<ProjectApp> projectApps) {
        this.projectApps = projectApps;
    }
}