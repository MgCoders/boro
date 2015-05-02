package uy.mgcoders.boro.objects;

import java.io.Serializable;
import java.util.Map;

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
    String assignee;

    public Issue(IssueCompact c) {

        //TODO: Esto esta mal, hacerlo de nuevo.
        Map<String, Field> map = c.getAttrMap();
        this.id = c.getId();
        Field f = map.get("projectShortName".toUpperCase());
        if (f != null) this.projectShortName = f.getValue();
        f = map.get("numberInProject".toUpperCase());
        this.numberInProject = f.getValue();
        f = map.get("summary".toUpperCase());
        if (f != null) this.summary = f.getValue();
        f = map.get("description".toUpperCase());
        if (f != null) this.description = f.getValue();
        f = map.get("priority".toUpperCase());
        if (f != null) {
            this.priority = f.getValueId();
            this.color = f.getColor();
        }
        f = map.get("state".toUpperCase());
        if (f != null) this.state = f.getValueId();
        f = map.get("Assignee".toUpperCase());
        if (f != null) this.assignee = f.getValue();

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getAsignee() {
        return assignee;
    }

    public void setAsignee(String asignee) {
        this.assignee = asignee;
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
