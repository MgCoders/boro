package uy.mgcoders.boro.objects;

/**
 * Created by r on 24/04/15.
 */
public class Issue {

    private String nombre;

    public Issue(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
