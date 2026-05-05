package clases;



public class Turno {
    // Atributos según el diagrama
    private int numero;           // identificador del turno (ej. 1, 2, 3...)
    private int tiempoEstimado;   // minutos estimados de espera para este turno
    private String estado;        // "PENDIENTE", "EN_CURSO", "ATENDIDO", "CANCELADO"

    // Constructor
    public Turno(int numero, int tiempoEstimado, String estado) {
        this.numero = numero;
        this.tiempoEstimado = tiempoEstimado;
        this.estado = estado;
    }

    // Getters y setters (necesarios para acceder desde otras clases)
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método toString para mostrar información del turno
    @Override
    public String toString() {
        return "Turno " + numero + " [espera: " + tiempoEstimado + " min] - " + estado;
    }
}
