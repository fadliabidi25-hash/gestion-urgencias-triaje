package clases;

import java.util.Scanner;

public class Main {
    static SistemaUrgencias sistema;
    static Scanner sc;

    public static void main(String[] args) {
        sistema = new SistemaUrgencias();
        sc = new Scanner(System.in);
        
        inicializarCamas();
        cargarDatosIniciales();
        
        boolean activo = true;
        while (activo) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    realizarTriaje();
                    break;
                case 3:
                    buscarPaciente();
                    break;
                case 4:
                    sistema.listarPacientesEnCola();
                    break;
                case 5:
                    sistema.mostrarEstadoCamas();
                    break;
                case 6:
                    atenderPaciente();
                    break;
                case 7:
                    liberarCama();
                    break;
                case 0:
                    activo = false;
                    System.out.println("\nCerrando sistema de urgencias...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    static void cargarDatosIniciales() {
        System.out.println("Cargando datos...\n");
        for (Paciente p : GestorPersistencia.cargarPacientes()) {
            sistema.registrarPaciente(p);
        }
    }

    static void mostrarMenuPrincipal() {
        System.out.println("\n========== SISTEMA URGENCIAS Y TRIAJE ==========");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Realizar triaje");
        System.out.println("3. Buscar paciente");
        System.out.println("4. Ver cola de pacientes");
        System.out.println("5. Ver estado de camas");
        System.out.println("6. Atender paciente");
        System.out.println("7. Liberar cama");
        System.out.println("0. Salir");
        System.out.print("Selecciona opción: ");
    }

    static int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void inicializarCamas() {
        sistema.agregarCama(new Cama("C1", "CONSULTA", "GENERAL"));
        sistema.agregarCama(new Cama("C2", "CONSULTA", "GENERAL"));
        sistema.agregarCama(new Cama("C3", "CONSULTA", "CARDIOLOGIA"));
        sistema.agregarCama(new Cama("BOX1", "BOX_REANIMACION", "UCI"));
        sistema.agregarCama(new Cama("BOX2", "BOX_REANIMACION", "UCI"));
    }

    static void registrarPaciente() {
        System.out.print("\nNombre: ");
        String nombre = sc.nextLine();
        if (!Validador.validarNombre(nombre)) {
            System.out.println("Error: El nombre debe tener al menos 3 caracteres.");
            return;
        }
        
        System.out.print("Apellidos: ");
        String apellidos = sc.nextLine();
        if (!Validador.validarNombre(apellidos)) {
            System.out.println("Error: Los apellidos deben tener al menos 3 caracteres.");
            return;
        }
        
        String dni = "";
        while (!Validador.validarDNI(dni)) {
            System.out.print("DNI (formato: 8 números + 1 letra): ");
            dni = sc.nextLine().toUpperCase();
            if (!Validador.validarDNI(dni)) {
                System.out.println("Error: DNI inválido. Debe ser 8 números seguidos de una letra.");
            }
        }
        
        System.out.print("Número de historia: ");
        String numHistoria = sc.nextLine();
        
        Paciente p = new Paciente(nombre, apellidos, dni, numHistoria);
        sistema.registrarPaciente(p);
        GestorPersistencia.guardarPaciente(p);
        System.out.println("✓ Paciente registrado y guardado correctamente.");
    }

    static void realizarTriaje() {
        System.out.print("\nIntroduce nombre del paciente para triaje: ");
        String nombre = sc.nextLine();
        Paciente p = sistema.buscarPacienteNombre(nombre);
        
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        
        if (p.getTriaje() != null) {
            System.out.println("El paciente ya tiene triaje realizado.");
            return;
        }
        
        Enfermero e = new Enfermero("Enfermero", "Sistema", "000", "E-001");
        sistema.realizarTriaje(p, e);
        
        System.out.println("\nTriaje completado:");
        System.out.println("  Código: " + p.getTriaje().getCodigo());
        System.out.println("  " + p.getTriaje().obtenerResumen());
        
        int espera = sistema.calcularTiempoEspera(p);
        System.out.println("  Tiempo estimado: " + espera + " minutos");
        
        sistema.activarAlerta(p);
    }

    static void buscarPaciente() {
        System.out.print("\nBuscar por (1)Nombre o (2)Número historia? ");
        int tipo = leerOpcion();
        
        Paciente p = null;
        if (tipo == 1) {
            System.out.print("Nombre: ");
            p = sistema.buscarPacienteNombre(sc.nextLine());
        } else if (tipo == 2) {
            System.out.print("Número historia: ");
            p = sistema.buscarPacienteHistoria(sc.nextLine());
        }
        
        if (p != null) {
            System.out.println("\n--- INFORMACIÓN DEL PACIENTE ---");
            System.out.println("Nombre: " + p.getNombre() + " " + p.getApellidos());
            System.out.println("DNI: " + p.getDni());
            System.out.println("Historia: " + p.getNumeroHistoria());
            System.out.println("Estado: " + p.getEstado());
            if (p.getTriaje() != null) {
                System.out.println("Triaje: " + p.getTriaje().obtenerResumen());
            } else {
                System.out.println("Triaje: Sin realizar");
            }
        } else {
            System.out.println("No encontrado.");
        }
    }

    static void atenderPaciente() {
        System.out.print("\nNombre del paciente a atender: ");
        String nombre = sc.nextLine();
        Paciente p = sistema.buscarPacienteNombre(nombre);
        
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        
        if (p.getTriaje() == null) {
            System.out.println("El paciente debe tener triaje realizado primero.");
            return;
        }
        
        // Asignar cama
        if (sistema.asignarCama(p)) {
            System.out.println("Cama asignada.");
        } else {
            System.out.println("No se pudo asignar cama.");
            return;
        }
        
        // Médico genera informe
        Medico m = new Medico("Dr. Sistema", "Hospital", "999", "M-001", "General");
        System.out.print("Diagnóstico: ");
        String diagnostico = sc.nextLine();
        System.out.print("Tratamiento: ");
        String tratamiento = sc.nextLine();
        
        m.setDiagnosticoActual(diagnostico);
        m.setTratamientoIndicado(tratamiento);
        m.generarInforme(p);
        
        // Cambiar estado
        System.out.println("\nActualizar estado (1)GRAVE (2)PENDIENTE_TRASLADO (3)ALTA (0)Mantener: ");
        int estadoOp = leerOpcion();
        EstadoPaciente estadoAnterior = p.getEstado();
        
        switch (estadoOp) {
            case 1:
                m.atenderPaciente(p, EstadoPaciente.GRAVE);
                NotificadorFamilia.notificarActualizacion(p, estadoAnterior.toString(), "GRAVE");
                break;
            case 2:
                m.atenderPaciente(p, EstadoPaciente.PENDIENTE_TRASLADO);
                NotificadorFamilia.notificarActualizacion(p, estadoAnterior.toString(), "PENDIENTE_TRASLADO");
                break;
            case 3:
                m.atenderPaciente(p, EstadoPaciente.ALTA);
                NotificadorFamilia.notificarActualizacion(p, estadoAnterior.toString(), "ALTA");
                break;
        }
    }

    static void liberarCama() {
        System.out.print("\nNombre del paciente: ");
        String nombre = sc.nextLine();
        Paciente p = sistema.buscarPacienteNombre(nombre);
        
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        
        boolean camaLiberada = false;
        try {
            for (Cama c : sistema.getCamasDisponibles()) {
                if (!camaLiberada && c.getPacienteActual() == p) {
                    c.liberarCama();
                    camaLiberada = true;
                }
            }
            if (!camaLiberada) {
                System.out.println("El paciente no está en ninguna cama.");
            }
        } catch (IllegalStateException ex) {
            System.err.println("Error al liberar cama: " + ex.getMessage());
        }
    }
}