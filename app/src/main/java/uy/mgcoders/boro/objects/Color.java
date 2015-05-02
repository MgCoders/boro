package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "color")
public class Color implements Serializable {

    @Element
    private String bg;

    @Element
    private String fg;

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getFg() {
        return fg;
    }

    public void setFg(String fg) {
        this.fg = fg;
    }
}
