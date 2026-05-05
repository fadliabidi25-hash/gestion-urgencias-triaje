package clases;

public class NotificadorFamilia {
    
    public static void notificarEmergencia(Paciente p) {
        if (p.getTriaje() == null || p.getTriaje().getCodigo() != CodigoColor.ROJO) {
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📱 NOTIFICACIÓN A FAMILIA DEL PACIENTE");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("[SMS ENVIADO]");
        System.out.println("  De: Hospital Nuevo Obispo Polanco");
        System.out.println("  Para: Contacto de emergencia");
        System.out.println();
        System.out.println("  \"El paciente " + p.getNombre() + " " + p.getApellidos() + " ha sido");
        System.out.println("   ingresado en URGENCIAS en CONDICIÓN CRÍTICA.");
        System.out.println("   Se han iniciado protocolos de atención inmediata.");
        System.out.println("   Por favor, contacte con el hospital.\"");
        System.out.println();
        System.out.println("[CORREO ELECTRÓNICO ENVIADO]");
        System.out.println("  De: contacto@hospital.es");
        System.out.println("  Asunto: ALERTA - Ingreso de emergencia");
        System.out.println();
        System.out.println("=".repeat(50) + "\n");
    }
    
    public static void notificarActualizacion(Paciente p, String estadoAnterior, String estadoNuevo) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("📢 ACTUALIZACIÓN DE ESTADO - FAMILIA");
        System.out.println("-".repeat(50));
        System.out.println();
        System.out.println("[NOTIFICACIÓN]");
        System.out.println("  Paciente: " + p.getNombre() + " " + p.getApellidos());
        System.out.println("  Estado anterior: " + estadoAnterior);
        System.out.println("  Estado nuevo: " + estadoNuevo);
        System.out.println();
        System.out.println("-".repeat(50) + "\n");
    }
}
