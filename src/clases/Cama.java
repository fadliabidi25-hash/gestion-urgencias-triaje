package clases;

public class Cama {
    private String id;
    private String tipo;
    private String especialidad;
    private boolean ocupada;
    private Paciente pacienteActual;

//Constructor

 public Cama(String id, String tipo, String especialidad) {
        this.id = id;
        this.tipo = tipo;
        this.especialidad = especialidad;
        this.ocupada = false;
        this.pacienteActual = null;
    }

    
//Métodos básicos de la clase:

   public void asignarPaciente(Paciente p) {
 if (p == null) {
        throw new IllegalArgumentException("El paciente no puede ser nulo");
    }
    if (this.ocupada) {
        throw new IllegalStateException("La cama " + id + " ya está ocupada");
    }
    // Si todo va bien, se asigna
    this.pacienteActual = p;
    this.ocupada = true;
    System.out.println("Paciente " + p.getNombre() + " asignado a cama " + id);
 }
    public void liberarCama() { 
if (this.ocupada) {
            System.out.println("Cama " + id + " liberada. Paciente " + 
                               pacienteActual.getNombre() + " ha salido.");
            this.pacienteActual = null;
            this.ocupada = false;
        }
}
    public boolean estaDisponible() { 
return !ocupada; 
}
//Metodos para lo de los boxes:

  public boolean esBoxReanimacion() {
        return this.tipo.equals("BOX_REANIMACION");
    }


//getters y setters
 public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public boolean isOcupada() { return ocupada; }
    // setOcupada no se expone por seguridad; se maneja con asignar/liberar

    public Paciente getPacienteActual() { return pacienteActual; }
    // setPacienteActual se omite por la misma razón



    @Override
    public String toString() {
        return "Cama " + id + " (" + tipo + ") - " + especialidad + 
               (ocupada ? " OCUPADA por " + pacienteActual.getNombre() : " LIBRE");
    }
}

