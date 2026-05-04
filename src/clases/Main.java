package clases;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Inicializar sistema y camas
        SistemaUrgencias sistema = new SistemaUrgencias();
        sistema.agregarCama(new Cama("C1", "CONSULTA", "GENERAL"));
        sistema.agregarCama(new Cama("C2", "BOX_REANIMACION", "CARDIOLOGIA"));

        // 2. Registrar paciente
        Paciente p = new Paciente("Carlos", "López", "12345678A", "H-001");
        sistema.registrarPaciente(p);


        // 3. Realizar triaje (enfermero pide constantes y síntomas)
        Enfermero e = new Enfermero("Laura", "García", "87654321B", "E-123");
        sistema.realizarTriaje(p, e);

        // 4. Mostrar tiempo de espera y activar alerta (solo si es ROJO)
        int espera = sistema.calcularTiempoEspera(p);
        System.out.println("Tiempo de espera estimado: " + espera + " minutos");
        sistema.activarAlerta(p);

        // 5. Asignar cama (si ya está en box de reanimación, no asigna otra)
        if (sistema.asignarCama(p)) {
            System.out.println("Cama asignada correctamente.");
        } 

        // 6. Médico: diagnóstico y tratamiento
        Medico m = new Medico("Javier", "Martínez", "11223344C", "M-456", "Cardiología");
        System.out.print("Introduce el diagnóstico: ");
        String diagnostico = sc.nextLine();
        System.out.print("Introduce el tratamiento: ");
        String tratamiento = sc.nextLine();
        m.setDiagnosticoActual(diagnostico);
        m.setTratamientoIndicado(tratamiento);

        // 7. Generar informe (sin modificar el estado del paciente)
        m.generarInforme(p);
      

        // 8. Liberar cama si el paciente la ocupa (sin usar break)
        boolean camaLiberada = false;
        try {
            for (Cama c : sistema.getCamasDisponibles()) {
                if (!camaLiberada && c.getPacienteActual() == p) {
                    c.liberarCama();
                    camaLiberada = true;   // indicamos que ya se liberó
                }
            }
        } catch (IllegalStateException ex) {
            System.err.println("Error al liberar cama: " + ex.getMessage());
        }

       
    }
}