package uy.mgcoders.boro.objects;

/**
 * Created by r on 24/04/15.
 */
public class Issue {

    String numberInProject;
    String summary;
    String description;
    String priority;
    String state;
    String projectShortName;

    public Issue(String numberInProject, String summary, String description, String priority, String state, String projectShortName) {
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
