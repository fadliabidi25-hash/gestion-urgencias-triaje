package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaUrgencias {

    private List<Paciente> colaPacientes;
    private List<Cama> camasDisponibles;
    private List<Turno> turnosActivos;
    private List<EquipoMedico> recursosDisponibles;

    public SistemaUrgencias() {
        this.colaPacientes = new ArrayList<>();
        this.camasDisponibles = new ArrayList<>();
        this.turnosActivos = new ArrayList<>();
        this.recursosDisponibles = new ArrayList<>();
    }

    // ------------------------------------------------------------
    // Registro y validaciones
    // ------------------------------------------------------------
    public void registrarPaciente(Paciente p) {
        if (p != null) {
            colaPacientes.add(p);
            System.out.println("Paciente " + p.getNombre() + " registrado en la cola.");
        }
    }

    public boolean existePacienteConDNI(String dni) {
        for (Paciente p : colaPacientes) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                return true;
            }
        }
        return false;
    }

    public String generarSiguienteNumeroHistoria() {
        int max = 0;
        for (Paciente p : colaPacientes) {
            String num = p.getNumeroHistoria();
            if (num != null && num.startsWith("H-")) {
                try {
                    int valor = Integer.parseInt(num.substring(2));
                    if (valor > max) {
                        max = valor;
                    }
                } catch (NumberFormatException e) {
                    // Si no se puede convertir, se ignora
                }
            }
        }
        return String.format("H-%03d", max + 1);
    }

    // ------------------------------------------------------------
    // Triaje
    // ------------------------------------------------------------
    public void realizarTriaje(Paciente p, Enfermero e) {
        if (p != null && e != null) {
            Triaje t = e.realizarTriaje(p);
        }
    }

    // ------------------------------------------------------------
    // Asignación de camas
    // ------------------------------------------------------------
    public boolean asignarCama(Paciente p) {
        if (p == null) return false;
        if (p.getTriaje() == null) {
            System.out.println("Aviso: " + p.getNombre() + " no tiene triaje. Cama no asignada.");
            return false;
        }

        if (estaPacienteEnCama(p)) {
            System.out.println("El paciente " + p.getNombre() + " ya está en una cama. No se asigna otra.");
            return false;
        }

        final String especialidad = (p.getTriaje().getEspecialidadRequerida() == null ||
                                     p.getTriaje().getEspecialidadRequerida().isEmpty()) ?
                                     "GENERAL" : p.getTriaje().getEspecialidadRequerida();

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
                    System.out.println("Cama " + c.getId() + " (GENERAL) asignada a " + p.getNombre());
                    return true;
                }
            }
        }

        System.out.println("No hay camas disponibles para " + especialidad + " ni GENERAL.");
        return false;
    }

    // ------------------------------------------------------------
    // Tiempos de espera y alertas
    // ------------------------------------------------------------
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
            System.out.println("Paciente: " + p.getNombre());
            System.out.println("Nº Historia: " + p.getNumeroHistoria());
            System.out.println("Síntomas: " + p.getTriaje().getSintomas());
            System.out.println("==========================================\n");

            asignarBoxReanimacion(p);
            enviarNotificacion("Alerta: Paciente crítico " + p.getNombre() + " en URGENCIAS.");
        } else if (p.getTriaje().getCodigo() == CodigoColor.AMARILLO) {
            int espera = calcularTiempoEspera(p);
            enviarNotificacion("Paciente " + p.getNombre() + " asignado a turno amarillo. Espera estimada: " + espera + " minutos.");
        }
    }

    public void enviarNotificacion(String mensaje) {
        System.out.println("[Notificación] " + mensaje);
    }

    // ------------------------------------------------------------
    // Gestión auxiliar de camas y equipos
    // ------------------------------------------------------------
    public void agregarCama(Cama c) {
        if (c != null) camasDisponibles.add(c);
    }

    public void agregarEquipo(EquipoMedico e) {
        if (e != null) recursosDisponibles.add(e);
    }

    public boolean estaPacienteEnCama(Paciente p) {
        for (Cama c : camasDisponibles) {
            if (c.getPacienteActual() == p) return true;
        }
        return false;
    }

    // ------------------------------------------------------------
    // Búsqueda y listados
    // ------------------------------------------------------------
    public Paciente buscarPacienteNombre(String nombre) {
        for (Paciente p : colaPacientes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public Paciente buscarPacienteHistoria(String numeroHistoria) {
        for (Paciente p : colaPacientes) {
            if (p.getNumeroHistoria().equals(numeroHistoria)) {
                return p;
            }
        }
        return null;
    }

    public void listarPacientesEnCola() {
        if (colaPacientes.isEmpty()) {
            System.out.println("No hay pacientes en la cola.");
            return;
        }
        System.out.println("\n--- PACIENTES EN COLA DE URGENCIAS ---");
        for (int i = 0; i < colaPacientes.size(); i++) {
            Paciente p = colaPacientes.get(i);
            String codigo = (p.getTriaje() != null) ? p.getTriaje().getCodigo().toString() : "Sin triaje";
            System.out.println((i + 1) + ". " + p.getNombre() +
                    " | Historia: " + p.getNumeroHistoria() +
                    " | Código: " + codigo +
                    " | Estado: " + p.getEstado());
        }
    }

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
        System.out.println("Total camas: " + libres + " libres | " + ocupadas + " ocupadas");

        System.out.println("\n--- EQUIPOS MÉDICOS ---");
        for (EquipoMedico eq : recursosDisponibles) {
            System.out.println("  " + eq.getId() + " - " + eq.getNombre() + " (" + eq.getUbicacion() + ") -> " +
                    (eq.estaDisponible() ? "DISPONIBLE" : "EN USO"));
        }
    }

    // ------------------------------------------------------------
    // Estado del paciente
    // ------------------------------------------------------------
    public void actualizarEstadoPaciente(Paciente p, EstadoPaciente nuevoEstado) {
        if (p != null) {
            p.actualizarEstado(nuevoEstado);
            System.out.println("Estado del paciente " + p.getNombre() + " actualizado a: " + nuevoEstado);
        }
    }

    // ------------------------------------------------------------
    // Asignación de box de reanimación (privado)
    // ------------------------------------------------------------
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

    // ------------------------------------------------------------
    // Persistencia de pacientes (estilo tradicional)
    // ------------------------------------------------------------
    public void guardarPacientes() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("pacientes.txt"));
            for (Paciente p : colaPacientes) {
                bw.write(p.getDni() + ";" + p.getNombre() + ";" + p.getApellidos() + ";" + p.getNumeroHistoria());
                bw.newLine();
            }
            System.out.println("Datos guardados en pacientes.txt");
        } catch (IOException e) {
            System.out.println("Error guardando: " + e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar pacientes.txt");
                }
            }
        }
    }

    public void cargarPacientes() {
        File f = new File("pacientes.txt");
        if (!f.exists()) return;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length == 4) {
                    Paciente p = new Paciente(d[0], d[1], d[2], d[3]);
                    registrarPaciente(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar pacientes.txt");
                }
            }
        }
    }

    // ------------------------------------------------------------
    // Persistencia de camas (estilo tradicional, sin try-with-resources)
    // ------------------------------------------------------------
    public void guardarCamas() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("camas.txt"));
            for (Cama c : camasDisponibles) {
                String ocupanteDni = "";
                if (!c.estaDisponible() && c.getPacienteActual() != null) {
                    ocupanteDni = c.getPacienteActual().getDni();
                }
                bw.write(c.getId() + ";" + c.getTipo() + ";" + c.getEspecialidad() + ";" +
                         c.getUbicacion() + ";" + c.estaDisponible() + ";" + ocupanteDni);
                bw.newLine();
            }
            System.out.println("Datos de camas guardados en camas.txt");
        } catch (IOException e) {
            System.out.println("Error guardando camas: " + e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar camas.txt");
                }
            }
        }
    }

    public void cargarCamas() {
        File f = new File("camas.txt");
        if (!f.exists()) return;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 5) {
                    Cama c = new Cama(d[0], d[1], d[2], d[3]); // id, tipo, especialidad, ubicacion
                    boolean disponible = Boolean.parseBoolean(d[4]);
                    if (!disponible && d.length >= 6 && !d[5].isEmpty()) {
                        Paciente ocupante = buscarPacientePorDni(d[5]);
                        if (ocupante != null) {
                            c.asignarPaciente(ocupante);
                        }
                    }
                    camasDisponibles.add(c);
                }
            }
            System.out.println("Camas cargadas desde camas.txt");
        } catch (IOException e) {
            System.out.println("Error cargando camas: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar camas.txt");
                }
            }
        }
    }

    public Paciente buscarPacientePorDni(String dni) {
        for (Paciente p : colaPacientes) {
            if (p.getDni().equalsIgnoreCase(dni)) return p;
        }
        return null;
    }

    // ------------------------------------------------------------
    // Getters de listas
    // ------------------------------------------------------------
    public List<Paciente> getColaPacientes() { return colaPacientes; }
    public List<Cama> getCamasDisponibles() { return camasDisponibles; }
    public List<Turno> getTurnosActivos() { return turnosActivos; }
}