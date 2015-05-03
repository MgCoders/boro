package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "workType") //para recibir tipos
public class WorkType implements Serializable {

    @Element(required = false)
    String name;

    @Element(required = false)
    String id;

    @Element(required = false)
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

    @Override
    public String toString() {
        return name;
    }
}
