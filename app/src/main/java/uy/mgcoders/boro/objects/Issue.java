package uy.mgcoders.boro.objects;

/**
 * Created by r on 24/04/15.
 */
public class Issue {

    private String id;
    private String nombre;

    public Issue() {
    }
    public Issue(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
