package clases;

public class EquipoMedico extends Recurso {
    private String nombre;
    private boolean estadoOperativo;

    public EquipoMedico(String id, String ubicacion, String nombre) {
        super(id, ubicacion);
        this.nombre = nombre;
        this.estadoOperativo = true;
    }

    public String getNombre() { return nombre; }
    public boolean isEstadoOperativo() { return estadoOperativo; }
    public void setEstadoOperativo(boolean estado) { this.estadoOperativo = estado; }
}
