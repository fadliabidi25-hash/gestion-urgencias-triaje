package clases;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Triaje {
    
    // ATRIBUTOS
    private Date fechaHora;
    private CodigoColor codigo;
    private String presionArterial;
    private int frecuenciaCardiaca;
    private float temperatura;
    private List<String> sintomas;
    private String especialidadRequerida;
    
    // CONSTRUCTOR
    public Triaje() {
        this.fechaHora = new Date();  // Fecha y hora actual
        this.sintomas = new ArrayList<>();
    }
    
/**ESTE CONSTRUCTOR VACÍO LO Q HACE ES GARANTIZAR LA CREACIÓN DEL TRIAJE INCLUSO SIN ASIGNARLE DATOS. pONE LA HORA EXACTA EN EL MOMENTO DE CREACIÓN Y LLAMA AL MÉTODO AGREGAR SÍNTOMA PARA Q NO QUEDE NULL EL ATRIBUTO SINTOMAS Y EVITAR LA EXCEPCIÓN NULLPOINTEREXCEPTION
*/
    // CONSTRUCTOR CON PARÁMETROS
    public Triaje(CodigoColor codigo, String presionArterial, 
                  int frecuenciaCardiaca, float temperatura) {
        this();  //invoca al constructor vacio para q asigne fecha y sintomas directamente(asi nos ahorramos codigo)
        this.codigo = codigo;
        this.presionArterial = presionArterial;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.temperatura = temperatura;
    }


    
    // MÉTODOS PRINCIPALES
    public boolean esCritico() {
        return codigo == CodigoColor.ROJO;
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
        this.sintomas.add(sintoma);
    }
    
    // GETTERS Y SETTERS
    public Date getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public CodigoColor getCodigo() {
        return codigo;
    }
    
    public void setCodigo(CodigoColor codigo) {
        this.codigo = codigo;
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
    
    public List<String> getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }
   
    
    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }
    
    public void setEspecialidadRequerida(String especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }
    
    // metosdo toString
    @Override
    public String toString() {
        return "Triaje{" +
                "fechaHora=" + fechaHora +
                ", codigo=" + codigo +
                ", frecuenciaCardiaca=" + frecuenciaCardiaca +
                ", temperatura=" + temperatura +
                ", sintomas=" + sintomas +
                "}";
    }
}

