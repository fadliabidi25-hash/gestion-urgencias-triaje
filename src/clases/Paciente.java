package clases;


import java.util.Date;

// PACIENTE
public class Paciente extends Persona {
    // Atributos según diagrama
    private String numeroHistoria;
    private EstadoPaciente estado;
    private Triaje triaje;

    // Constructor básico
    public Paciente() {
        super();
        this.estado = EstadoPaciente.ESTABLE;   // por defecto estable
    }

    // Constructor con datos personales y número de historia
    public Paciente(String dni, String nombre, String apellidos, String numeroHistoria) {
        super(dni, nombre, apellidos);
        this.numeroHistoria = numeroHistoria;
        this.estado = EstadoPaciente.ESTABLE;  //el enfermero pone q es estable para q no quede en null, luego si se considera q es otro estado se cambia con actualizarEstado
    }

    // --- Métodos 

    /**
     * Simula la consulta del historial previo del paciente
     * (usado por el enfermero durante el triaje)
     */
    public String getHistorial() {
        return "Paciente: " + nombre +
               "\nNº Historia: " + numeroHistoria +
               "\nAlergias: Ninguna conocida" +
               "\nAntecedentes: No registrados";
    }

    /**
     * Actualiza el estado del paciente (ESTABLE, GRAVE, etc.)
     */
    public void actualizarEstado(EstadoPaciente e) {
        this.estado = e;
    }

    // --- Getters y setters ---

    public String getNumeroHistoria() {
        return numeroHistoria;
    }

    public void setNumeroHistoria(String numeroHistoria) {
        this.numeroHistoria = numeroHistoria;
    }

    public EstadoPaciente getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaciente estado) {
        this.estado = estado;
    }

    public Triaje getTriaje() {
        return triaje;
    }

    public void setTriaje(Triaje triaje) {
        this.triaje = triaje;
    }

    @Override
    public String toString() {
        return "Paciente [número=" + numeroHistoria + ", nombre=" + nombre + "]";
    }
}

