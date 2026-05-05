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
            
            // Notificar a familia
            NotificadorFamilia.notificarEmergencia(p);
        } else if (p.getTriaje().getCodigo() == CodigoColor.AMARILLO) {
            int espera = calcularTiempoEspera(p);
            PantallaTurnos.mostrarPantallaTurnos(p, espera);
            PantallaTurnos.mostrarTurnosDisponibles();
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

    // Buscar paciente por nombre
    public Paciente buscarPacienteNombre(String nombre) {
        for (Paciente p : colaPacientes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Buscar por número de historia
    public Paciente buscarPacienteHistoria(String numeroHistoria) {
        for (Paciente p : colaPacientes) {
            if (p.getNumeroHistoria().equals(numeroHistoria)) {
                return p;
            }
        }
        return null;
    }

    // Listar pacientes en cola
    public void listarPacientesEnCola() {
        if (colaPacientes.isEmpty()) {
            System.out.println("No hay pacientes en la cola.");
            return;
        }
        System.out.println("\n--- PACIENTES EN COLA DE URGENCIAS ---");
        for (int i = 0; i < colaPacientes.size(); i++) {
            Paciente p = colaPacientes.get(i);
            String codigo = (p.getTriaje() != null) ? p.getTriaje().getCodigo().toString() : "Sin triaje";
            System.out.println((i + 1) + ". " + p.getNombre() + " | Historia: " + p.getNumeroHistoria() + " | Código: " + codigo + " | Estado: " + p.getEstado());
        }
    }

    // Mostrar estado de camas
    public void mostrarEstadoCamas() {
        System.out.println("\n--- ESTADO DE CAMAS Y BOXES ---");
        int libres = 0, ocupadas = 0;
        for (Cama c : camasDisponibles) {
            if (c.estaDisponible()) {
                System.out.println("  " + c.getId() + " (" + c.getTipo() + " - " + c.getEspecialidad() + ") -> LIBRE");
                libres++;
            } else {
                String paciente = c.getPacienteActual() != null ? c.getPacienteActual().getNombre() : "?";
                System.out.println("  " + c.getId() + " (" + c.getTipo() + " - " + c.getEspecialidad() + ") -> OCUPADA por " + paciente);
                ocupadas++;
            }
        }
        System.out.println("Total: " + libres + " libres | " + ocupadas + " ocupadas");
    }

    // Actualizar estado de paciente
    public void actualizarEstadoPaciente(Paciente p, EstadoPaciente nuevoEstado) {
        if (p != null) {
            p.actualizarEstado(nuevoEstado);
            System.out.println("Estado del paciente " + p.getNombre() + " actualizado a: " + nuevoEstado);
        }
    }
}