package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "field")
public class Field {

    @Attribute
    private String name;

    @Element
    private String value;

    @Element(required = false)
    private String valueId;

    @Element(required = false)
    private Color color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;


    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
