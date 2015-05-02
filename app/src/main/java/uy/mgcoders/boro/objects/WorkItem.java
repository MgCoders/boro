package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "workItem")
public class WorkItem {

    @Element(required = false)
    String id;

    @Element
    Date date;

    @Element
    long duration;

    @Element
    String description;

    @Element(required = false)
    Author author;

    @Element(required = false)
    WorkType workType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }
}
