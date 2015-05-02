package uy.mgcoders.boro.objects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raul on 01/05/15.
 */
@Root(name = "workItemTypes")
public class WorkItemTypes implements Serializable {

    @ElementList(inline = true)
    List<WorkType> workTypes;

    public List<WorkType> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkType> workTypes) {
        this.workTypes = workTypes;
    }
}
