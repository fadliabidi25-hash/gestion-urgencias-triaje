package clases;


// MÉDICO 
public class Medico extends Persona {
    // Atributos según diagrama
    private String numColegiado;
    private String especialidad;

    // Atributos adicionales necesarios para generar el informe
    private String diagnosticoActual;
    private String tratamientoIndicado;

    // Constructores
    public Medico() {}

    public Medico(String dni, String nombre, String apellidos,
                  String numColegiado, String especialidad) {
        super(dni, nombre, apellidos);
        this.numColegiado = numColegiado;
        this.especialidad = especialidad;
    }

    // --- Métodos del diagrama ---

    /**
     * Atiende al paciente (marcar como atendido, cambiar estado, etc.)
     * En esta implementación solo actualiza el estado a modo de ejemplo.
     */
    public void atenderPaciente(Paciente p) {
        // Simplemente marcamos que está siendo atendido
        p.actualizarEstado(EstadoPaciente.ESTABLE); // podría cambiarse según criterio médico
       System.out.println("Médico " + nombre + " atendiendo a " + p.getNombre() +
                           ", estado: " + p.getEstado());
    }

    /**
     * Genera el informe de urgencias del paciente y lo devuelve como String.
     * Simula la integración con la historia clínica mediante un mensaje en consola.
     */
    public String generarInforme(Paciente p) {
        // Incluye datos del paciente, su triaje y el diagnóstico/tratamiento realizados
        String codigoColor = (p.getTriaje() != null) ? p.getTriaje().getCodigo().toString() : "No asignado";
// Esa linea de codigo lo q hace es comprobar si el triaje es null, si no esnull saca el color del triaje.
        String informe = "INFORME DE URGENCIAS\n" +
                         "Paciente: " + p.getNombre() + "\n" +
                         "Nº Historia: " + p.getNumeroHistoria() + "\n" +
                         "Triaje: " + codigoColor + "\n" +
                         "Diagnóstico: " + (diagnosticoActual != null ? diagnosticoActual : "No especificado") + "\n" +
                         "Tratamiento: " + (tratamientoIndicado != null ? tratamientoIndicado : "Ninguno");

        // Simula la actualización de la historia clínica
        System.out.println("[HISTORIA CLÍNICA ACTUALIZADA] " + informe);

        return informe;
    }

    // --- Getters y setters ---

    public String getNumColegiado() { 
return numColegiado;
 }
    public void setNumColegiado(String numColegiado) { 
this.numColegiado = numColegiado; 
}
    public String getEspecialidad() { 
return especialidad; 
}
    public void setEspecialidad(String especialidad) { 
this.especialidad = especialidad; 
}
    public String getDiagnosticoActual() { 
return diagnosticoActual; 
}
    public void setDiagnosticoActual(String diagnosticoActual) { 
this.diagnosticoActual = diagnosticoActual; 
}
    public String getTratamientoIndicado() { 
return tratamientoIndicado; 
}
    public void setTratamientoIndicado(String tratamientoIndicado) { 
this.tratamientoIndicado = tratamientoIndicado; 
}
}

