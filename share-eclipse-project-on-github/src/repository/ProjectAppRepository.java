package repository;

import entity.*; 
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ProjectAppRepository extends Repository {

    /**
	 * 
	 */
	private static final long serialVersionUID = 323802748759648113L;
	private static ProjectAppRepository instance;
	private List<ProjectApp> projectApps;

    public ProjectAppRepository() {
        this.projectApps = new ArrayList<>();
    }
    
	public static ProjectAppRepository getInstance() {
        if (instance == null) {
            instance = new ProjectAppRepository();
        }
        return instance;
	}
	private Object readResolve() throws ObjectStreamException {
		instance = this;
		return instance;
	}

    public void add(ProjectApp projectApp) {
        this.projectApps.add(projectApp);
    }

    public List<ProjectApp> getProjectApps() {
        return projectApps;
    }

    public void setProjectApps(List<ProjectApp> projectApps) {
        this.projectApps = projectApps;
    }
}