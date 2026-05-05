package clases;

public class Cama extends Recurso {
    private String tipo;
    private String especialidad;
    private Paciente pacienteActual;

//Constructor

 public Cama(String id, String tipo, String especialidad, String ubicacion) {
        super(id, ubicacion);
        this.tipo = tipo;
        this.especialidad = especialidad;
        this.pacienteActual = null;
    }

    
//Métodos básicos de la clase:

   public void asignarPaciente(Paciente p) {
    if (p == null) {
        throw new IllegalArgumentException("El paciente no puede ser nulo");
    }
    if (!estaDisponible()) {
        throw new IllegalStateException("La cama " + id + " ya está ocupada");
    }
    // Si todo va bien, se asigna
    reservar();
    this.pacienteActual = p;
    System.out.println("Paciente " + p.getNombre() + " asignado a cama " + id);
 }
    public void liberarCama() { 
        if (!estaDisponible()) {
            System.out.println("Cama " + id + " liberada. Paciente " + 
                               pacienteActual.getNombre() + " ha salido.");
            this.pacienteActual = null;
            liberar();
        }
    }
//Metodos para lo de los boxes:

  public boolean esBoxReanimacion() {
        return this.tipo.equals("BOX_REANIMACION");
    }


//getters y setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Paciente getPacienteActual() { return pacienteActual; }
    // setPacienteActual se omite por la misma razón

    @Override
    public String toString() {
        return "Cama " + id + " (" + tipo + ") - " + especialidad + 
               (!estaDisponible() ? " OCUPADA por " + pacienteActual.getNombre() : " LIBRE");
    }
}

