package clases;


import java.util.Scanner;

public class Enfermero extends Persona {
    private String numColegiado;

    public Enfermero() {}

    public Enfermero(String dni, String nombre, String apellidos, String numColegiado) {
        super(dni, nombre, apellidos);
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
        String valor = "";
        boolean valido = false;
        while (!valido) {
            System.out.print("Introduce presión arterial (ej. 120/80): ");
            valor = sc.nextLine();
            
            String[] partes = valor.split("/");
            if (partes.length == 2) {
                try {
                    int sistolica = Integer.parseInt(partes[0].trim());
                    int diastolica = Integer.parseInt(partes[1].trim());
                    
                    if (sistolica >= 70 && sistolica <= 250 && diastolica >= 40 && diastolica <= 150) {
                        valido = true;
                    } else {
                        System.out.println("Error: Valores fuera de rango (Sistólica: 70-250, Diastólica: 40-150).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Deben ser números enteros separados por '/'.");
                }
            } else {
                System.out.println("Error: El formato debe ser SISTOLICA/DIASTOLICA (ej. 120/80).");
            }
        }
        return valor;
    }

    private int obtenerFrecuenciaCardiaca(Scanner sc) {
        return (int) obtenerValorNumerico(sc, "Introduce frecuencia cardíaca (ppm): ", 1, 299, "Error: La frecuencia cardíaca debe estar entre 1 y 299 ppm.");
    }

    private float obtenerTemperatura(Scanner sc) {
        return obtenerValorNumerico(sc, "Introduce temperatura corporal (ej. 36.5): ", 35.0f, 42.0f, "Error: La temperatura debe estar entre 35°C y 42°C.");
    }

    private float obtenerValorNumerico(Scanner sc, String mensaje, float min, float max, String msjError) {
        float valor = -1;
        while (valor < 0) {
            try {
                System.out.print(mensaje);
                valor = Float.parseFloat(sc.nextLine());
                if (valor < min || valor > max) {
                    System.out.println(msjError);
                    valor = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ser un número válido.");
                valor = -1;
            }
        }
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

