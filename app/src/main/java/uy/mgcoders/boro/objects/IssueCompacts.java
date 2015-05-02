package uy.mgcoders.boro.objects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by raul on 01/05/15.
 */
@Root(name = "issueCompacts")
public class IssueCompacts {

    @ElementList(inline = true, required = false, entry = "issue")
    List<IssueCompact> issues;

    public List<IssueCompact> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueCompact> issues) {
        this.issues = issues;
    }
}
