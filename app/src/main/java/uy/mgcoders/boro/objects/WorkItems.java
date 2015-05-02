package uy.mgcoders.boro.objects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by raul on 01/05/15.
 */
@Root(name = "workItems")
public class WorkItems {

    @ElementList(inline = true)
    List<WorkItem> workItems;

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }
}
