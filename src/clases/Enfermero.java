package clases;


import java.util.Scanner;

public class Enfermero extends Persona {
    private String numColegiado;

    public Enfermero() {}

    public Enfermero(String nombre, String apellidos, String dni, String numColegiado) {
        super(nombre, apellidos, dni);
        this.numColegiado = numColegiado;
    }

    public Triaje realizarTriaje(Paciente p) {
        Scanner sc = new Scanner(System.in);
        Triaje triaje = new Triaje();

        System.out.println("--- Realizando triaje de " + p.getNombre() + " ---");

        String presion = obtenerPresionArterial(sc);
        int frecuencia = obtenerFrecuenciaCardiaca(sc);
        float temperatura = obtenerTemperatura(sc);
        String[] sintomas = obtenerSintomas(sc);

        triaje.setPresionArterial(presion);
        triaje.setFrecuenciaCardiaca(frecuencia);
        triaje.setTemperatura(temperatura);

        for (String s : sintomas) {
            triaje.agregarSintoma(s);
        }

        triaje.setCodigo(triaje.asignarPrioridad());
        p.setTriaje(triaje);

        System.out.println("Triaje completado. Código asignado: " + triaje.getCodigo() + "\n");
        return triaje;
    }

    private String obtenerPresionArterial(Scanner sc) {
        System.out.print("Introduce presión arterial (ej. 120/80): ");
        return sc.nextLine();
    }

    private int obtenerFrecuenciaCardiaca(Scanner sc) {
        System.out.print("Introduce frecuencia cardíaca (ppm): ");
        int valor = Integer.parseInt(sc.nextLine());
        return valor;
    }

    private float obtenerTemperatura(Scanner sc) {
        System.out.print("Introduce temperatura corporal (ej. 36.5): ");
        float valor = Float.parseFloat(sc.nextLine());
        return valor;
    }

    private String[] obtenerSintomas(Scanner sc) {
        System.out.print("¿Cuántos síntomas quieres registrar? ");
        int cantidad = Integer.parseInt(sc.nextLine());
        String[] sintomas = new String[cantidad];

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Síntoma " + (i + 1) + ": ");
            sintomas[i] = sc.nextLine();
        }
        return sintomas;
    }

    public String getNumColegiado() { return numColegiado; }
    public void setNumColegiado(String numColegiado) { this.numColegiado = numColegiado; }
}

