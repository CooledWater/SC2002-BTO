package models;

import enums.FlatType;
import applications.ProjectApp;
import java.util.*;

public class Project {
    private boolean isVisible;
    private String name;
    private String neighbourhood;
    private FlatType flatType;
    private String openDate;
    private String closeDate;
    private String managerName;
    private List<HDBOfficer> officersAssigned = new ArrayList<>();
    private List<ProjectApp> projectApps = new ArrayList<>();

    public Project(String name, String neighbourhood, FlatType flatType, String openDate, String closeDate, String managerName) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.flatType = flatType;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerName = managerName;
        this.isVisible = true;
    }

    public void addOfficer(HDBOfficer officer) {
        officersAssigned.add(officer);
    }
    
    public String getName() {
    	return name;
    }
}
