package clases;

public abstract class Recurso {
    protected String id;
    protected boolean disponible;
    protected String ubicacion;

    public Recurso(String id, String ubicacion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.disponible = true;
    }

    public boolean reservar() {
        if (disponible) {
            disponible = false;
            return true;
        }
        return false;
    }

    public void liberar() {
        this.disponible = true;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public String getId() { return id; }
    public String getUbicacion() { return ubicacion; }
}
