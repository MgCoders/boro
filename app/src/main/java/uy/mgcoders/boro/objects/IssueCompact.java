package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "issue")
public class IssueCompact {

    @Attribute
    private String id;

    @ElementList(inline = true)
    private List<Field> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Map<String, Field> getAttrMap() {
        Map<String, Field> map = new HashMap<>();
        for (Field f : fields) map.put(f.getName().toUpperCase(), f);
        return map;
    }
}
