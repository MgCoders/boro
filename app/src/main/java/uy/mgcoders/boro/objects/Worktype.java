package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "worktype") //para mandar registro de horas
public class Worktype implements Serializable {

    @Element(required = true)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
