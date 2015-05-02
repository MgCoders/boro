package uy.mgcoders.boro.objects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by raul on 01/05/15.
 */
@Element(name = "author")
public class Author {

    @Attribute
    String login;

    @Attribute(required = false)
    String ringId;

    @Attribute(required = false)
    String url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRingId() {
        return ringId;
    }

    public void setRingId(String ringId) {
        this.ringId = ringId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
