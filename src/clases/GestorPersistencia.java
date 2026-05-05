package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    
    private static final String ARCHIVO_PACIENTES = "datos/pacientes.txt";
    private static final String ARCHIVO_MEDICOS = "datos/medicos.txt";
    private static final String SEPARADOR = "|";
    
    static {
        // Crear directorio si no existe
        File dir = new File("datos");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    // Guardar paciente en archivo
    public static void guardarPaciente(Paciente p) {
        try (FileWriter fw = new FileWriter(ARCHIVO_PACIENTES, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            String linea = p.getNombre() + SEPARADOR + p.getApellidos() + SEPARADOR + 
                          p.getDni() + SEPARADOR + p.getNumeroHistoria();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar paciente: " + e.getMessage());
        }
    }
    
    // Cargar pacientes desde archivo
    public static List<Paciente> cargarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        File archivo = new File(ARCHIVO_PACIENTES);
        
        if (!archivo.exists()) {
            System.out.println("Archivo de pacientes no encontrado. Creando nuevo...");
            return pacientes;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 4) {
                    Paciente p = new Paciente(datos[0], datos[1], datos[2], datos[3]);
                    pacientes.add(p);
                }
            }
            System.out.println("Se cargaron " + pacientes.size() + " pacientes del archivo.");
        } catch (IOException e) {
            System.err.println("Error al cargar pacientes: " + e.getMessage());
        }
        
        return pacientes;
    }
    
    // Guardar médico en archivo
    public static void guardarMedico(Medico m) {
        try (FileWriter fw = new FileWriter(ARCHIVO_MEDICOS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            String linea = m.getNombre() + SEPARADOR + m.getApellidos() + SEPARADOR + 
                          m.getDni() + SEPARADOR + m.getNumColegiado() + SEPARADOR + 
                          m.getEspecialidad();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar médico: " + e.getMessage());
        }
    }
    
    // Cargar médicos desde archivo
    public static List<Medico> cargarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        File archivo = new File(ARCHIVO_MEDICOS);
        
        if (!archivo.exists()) {
            return medicos;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 5) {
                    Medico m = new Medico(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    medicos.add(m);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar médicos: " + e.getMessage());
        }
        
        return medicos;
    }
}
