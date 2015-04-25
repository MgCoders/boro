package uy.mgcoders.boro.util;

/**
 * Created by r on 25/04/15.
 */
public class Query {

    public static final String ASSIGNED_TO_ME = "asignado a: mi #{Sin resolver}";

    private String content;

    public Query(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
