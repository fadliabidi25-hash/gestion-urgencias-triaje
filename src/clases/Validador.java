package clases;

import java.util.regex.Pattern;

public class Validador {
    
    // Validar DNI: 8 números + 1 letra
    public static boolean validarDNI(String dni) {
        if (dni == null || dni.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]{8}[A-Z]$");
        return pattern.matcher(dni.toUpperCase()).matches();
    }
    
    // Validar frecuencia cardíaca: no negativa
    public static boolean validarFrecuenciaCardiaca(int frecuencia) {
        return frecuencia > 0 && frecuencia < 300;
    }
    
    // Validar temperatura: rango normal
    public static boolean validarTemperatura(float temperatura) {
        return temperatura > 35 && temperatura < 42;
    }
    
    // Validar presión arterial (formato básico)
    public static boolean validarPresionArterial(String presion) {
        if (presion == null || presion.isEmpty()) {
            return false;
        }
        return presion.matches("[0-9]{2,3}/[0-9]{2,3}");
    }
    
    // Validar nombre
    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() >= 3;
    }
}
