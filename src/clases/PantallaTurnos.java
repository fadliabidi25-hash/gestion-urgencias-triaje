package clases;

public class PantallaTurnos {
    
    public static void mostrarPantallaTurnos(Paciente p, int tiempoEspera) {
        if (p.getTriaje() == null) return;
        
        if (p.getTriaje().getCodigo() != CodigoColor.AMARILLO) {
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        PANTALLA DE INFORMACIÓN         ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Paciente: " + p.getNombre() + " " + p.getApellidos());
        System.out.println("  Nº Historia: " + p.getNumeroHistoria());
        System.out.println();
        System.out.println("  ◆ PRIORIDAD: " + p.getTriaje().getCodigo() + " (MODERADO)");
        System.out.println();
        System.out.println("  ⏱ TIEMPO ESTIMADO DE ESPERA: " + tiempoEspera + " minutos");
        System.out.println();
        System.out.println("  📍 UBICACIÓN: Área de espera de urgencias");
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println();
    }
    
    public static void mostrarTurnosDisponibles() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       TURNOS DISPONIBLES PRÓXIMOS     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Turno 1  - Espera: 15 min  [Consulta 1]");
        System.out.println("  Turno 2  - Espera: 25 min  [Consulta 2]");
        System.out.println("  Turno 3  - Espera: 35 min  [Consulta 3]");
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println();
    }
}
