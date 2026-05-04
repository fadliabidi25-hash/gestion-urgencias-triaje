package clases;

import java.util.ArrayList;
import java.util.List;

public class SistemaUrgencias {

    private List<Paciente> colaPacientes;
    private List<Cama> camasDisponibles;
    private List<Turno> turnosActivos;

    public SistemaUrgencias() {
        this.colaPacientes = new ArrayList<>();
        this.camasDisponibles = new ArrayList<>();
        this.turnosActivos = new ArrayList<>();
    }

    public void registrarPaciente(Paciente p) {
        if (p != null) {
            colaPacientes.add(p);
            System.out.println("Paciente " + p.getNombre() + " registrado en la cola.");
        }
    }

    public void realizarTriaje(Paciente p, Enfermero e) {
        if (p != null && e != null) {
            Triaje t = e.realizarTriaje(p);
            
        }
    }

    public boolean asignarCama(Paciente p) {
        if (p == null) return false;
        if (p.getTriaje() == null) {
            System.out.println("Aviso: " + p.getNombre() + " no tiene triaje. Cama no asignada.");
            return false;
        }

        // Verificar si el paciente ya está en alguna cama
        if (estaPacienteEnCama(p)) {
            System.out.println("El paciente " + p.getNombre() + " ya está en una cama. No se asigna otra.");
            return false;
        }

        String especialidad = p.getTriaje().getEspecialidadRequerida();
        if (especialidad == null || especialidad.isEmpty()) {
            especialidad = "GENERAL";
        }

        // 1. Buscar cama de especialidad exacta
        for (Cama c : camasDisponibles) {
            if (c.estaDisponible() && c.getEspecialidad().equalsIgnoreCase(especialidad)) {
                c.asignarPaciente(p);
                System.out.println("Cama " + c.getId() + " (" + c.getEspecialidad() + ") asignada a " + p.getNombre());
                return true;
            }
        }

        // 2. Buscar cama GENERAL (solo si la especialidad requerida no es GENERAL)
        if (!especialidad.equalsIgnoreCase("GENERAL")) {
            for (Cama c : camasDisponibles) {
                if (c.estaDisponible() && c.getEspecialidad().equalsIgnoreCase("GENERAL")) {
                    c.asignarPaciente(p);
                    System.out.println("Cama " + c.getId() + " (GENERAL) asignada a " + p.getNombre() +
                                       " (especialidad requerida: " + especialidad + ")");
                    return true;
                }
            }
        }

        System.out.println("No hay camas disponibles para " + especialidad);
        return false;
    }

    public int calcularTiempoEspera(Paciente p) {
        if (p == null || p.getTriaje() == null) return 60;
        switch (p.getTriaje().getCodigo()) {
            case ROJO:    return 0;
            case AMARILLO:return 30;
            case VERDE:   return 120;
            case BLANCO:  return 240;
            default:      return 60;
        }
    }

    public void activarAlerta(Paciente p) {
        if (p == null || p.getTriaje() == null) return;

        if (p.getTriaje().getCodigo() == CodigoColor.ROJO) {
            System.out.println("\n==========================================");
            System.out.println("  ¡¡ALERTA ROJA - PACIENTE CRÍTICO!!");
            System.out.println("==========================================");
            System.out.println("Paciente: " + p.getNombre() + " " + p.getApellidos());
            System.out.println("Nº Historia: " + p.getNumeroHistoria());
            System.out.println("Síntomas: " + p.getTriaje().getSintomas());
            System.out.println("==========================================\n");

            asignarBoxReanimacion(p);
        }
    }

    public void enviarNotification(String mensaje) {
        System.out.println("[Notificación] " + mensaje);
    }

    // Métodos auxiliares
    public void agregarCama(Cama c) {
        if (c != null) camasDisponibles.add(c);
    }

    public boolean estaPacienteEnCama(Paciente p) {
        for (Cama c : camasDisponibles) {
            if (c.getPacienteActual() == p) return true;
        }
        return false;
    }

    public List<Paciente> getColaPacientes() { return colaPacientes; }
    public List<Cama> getCamasDisponibles() { return camasDisponibles; }
    public List<Turno> getTurnosActivos() { return turnosActivos; }

    // Asigna box de reanimación (privado)
    private void asignarBoxReanimacion(Paciente p) {
        for (Cama c : camasDisponibles) {
            if (c.getTipo().equalsIgnoreCase("BOX_REANIMACION") && c.estaDisponible()) {
                c.asignarPaciente(p);
                System.out.println("Paciente " + p.getNombre() + " asignado a BOX REANIMACIÓN " + c.getId());
                return;
            }
        }
        System.out.println("¡ATENCIÓN! No hay boxes de reanimación libres.");
    }
}