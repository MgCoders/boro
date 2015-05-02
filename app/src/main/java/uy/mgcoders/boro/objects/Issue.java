package uy.mgcoders.boro.objects;

import java.io.Serializable;

/**
 * Created by r on 24/04/15.
 */
public class Issue implements Serializable {

    String id;
    String numberInProject;
    String summary;
    String description;
    String priority;
    String state;
    String projectShortName;
    Color color;

    public Issue(IssueCompact c) {

        //TODO: Esto esta mal, hacerlo de nuevo.

        this.id = c.getId();
        Field f = c.getFields().get(0);
        if (f.getName().equalsIgnoreCase("projectShortName")) this.projectShortName = f.getValue();
        f = c.getFields().get(1);
        if (f.getName().equalsIgnoreCase("numberInProject")) this.numberInProject = f.getValue();
        f = c.getFields().get(2);
        if (f.getName().equalsIgnoreCase("summary")) this.summary = f.getValue();
        f = c.getFields().get(3);
        if (f.getName().equalsIgnoreCase("description")) this.description = f.getValue();
        f = c.getFields().get(13);
        if (f.getName().equalsIgnoreCase("priority")) {
            this.priority = f.getValueId();
            this.color = f.getColor();
        }
        f = c.getFields().get(15);
        if (f.getName().equalsIgnoreCase("state")) this.state = f.getValueId();

    }

    public Issue(String id, String numberInProject, String summary, String description, String priority, String state, String projectShortName) {
        this.id = id;
        this.numberInProject = numberInProject;
        this.summary = summary;
        this.description = description;
        this.priority = priority;
        this.state = state;
        this.projectShortName = projectShortName;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "numberInProject='" + numberInProject + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", state='" + state + '\'' +
                ", projectShortName='" + projectShortName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return projectShortName + "-" + numberInProject;
    }

    public String getNumberInProject() {
        return numberInProject;
    }

    public void setNumberInProject(String numberInProject) {
        this.numberInProject = numberInProject;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
