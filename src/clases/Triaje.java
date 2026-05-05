package clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Triaje {
    
    // ATRIBUTOS
    private Date fechaHora;
    private CodigoColor codigoColor;
    private String presionArterial;
    private int frecuenciaCardiaca;
    private float temperatura;
    private String sintomas;
    private String especialidadRequerida;
    
    // CONSTRUCTOR
    public Triaje() {
        this.fechaHora = new Date();  // Fecha y hora actual
        this.sintomas = "";
    }
    
/**ESTE CONSTRUCTOR VACÍO LO Q HACE ES GARANTIZAR LA CREACIÓN DEL TRIAJE INCLUSO SIN ASIGNARLE DATOS. pONE LA HORA EXACTA EN EL MOMENTO DE CREACIÓN Y LLAMA AL MÉTODO AGREGAR SÍNTOMA PARA Q NO QUEDE NULL EL ATRIBUTO SINTOMAS Y EVITAR LA EXCEPCIÓN NULLPOINTEREXCEPTION
*/
    // CONSTRUCTOR CON PARÁMETROS
    public Triaje(CodigoColor codigoColor, String presionArterial, 
                  int frecuenciaCardiaca, float temperatura) {
        this();  //invoca al constructor vacio para q asigne fecha y sintomas directamente(asi nos ahorramos codigo)
        this.codigoColor = codigoColor;
        this.presionArterial = presionArterial;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.temperatura = temperatura;
    }


    
    // MÉTODOS PRINCIPALES
    public boolean esCritico() {
        return codigoColor == CodigoColor.ROJO;
    }
    

    
    public CodigoColor asignarPrioridad() {
        // Lógica para asignar código según constantes vitales
        if (frecuenciaCardiaca > 140 || temperatura > 39.5) {
            return CodigoColor.ROJO;
        } else if (frecuenciaCardiaca > 100 || temperatura > 38.0) {
            return CodigoColor.AMARILLO;
        } else if (sintomas.contains("dolor leve")) {
            return CodigoColor.VERDE;
        } else {
            return CodigoColor.BLANCO;
        }
    }
public void agregarSintoma(String sintoma) {
        if (this.sintomas.isEmpty()) {
            this.sintomas = sintoma;
        } else {
            this.sintomas += ", " + sintoma;
        }
    }
    
    // GETTERS Y SETTERS
    public Date getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public CodigoColor getCodigo() {
        return codigoColor;
    }
    
    public void setCodigo(CodigoColor codigoColor) {
        this.codigoColor = codigoColor;
    }
    
    public String getPresionArterial() {
        return presionArterial;
    }
    
    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }
    
    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }
    
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }
    
    public float getTemperatura() {
        return temperatura;
    }
    
    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }
    
    public String getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
   
    
    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }
    
    public void setEspecialidadRequerida(String especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }
    
    // método para resumen legible
    public String obtenerResumen() {
        String sintList = sintomas.isEmpty() ? "Sin síntomas" : sintomas;
        return "Código: " + codigoColor + " | FC: " + frecuenciaCardiaca + 
               " | Temp: " + temperatura + "°C | Síntomas: " + sintList;
    }
    
    // metosdo toString
    @Override
    public String toString() {
        return "Triaje{" +
                "fechaHora=" + fechaHora +
                ", codigoColor=" + codigoColor +
                ", frecuenciaCardiaca=" + frecuenciaCardiaca +
                ", temperatura=" + temperatura +
                ", sintomas=" + sintomas +
                "}";
    }
}

