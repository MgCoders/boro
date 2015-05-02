package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "workType")
public class WorkType implements Serializable {

    @Element
    String name;

    @Element
    String id;

    @Element
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
