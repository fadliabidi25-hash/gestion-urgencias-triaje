package clases;

import java.util.Date;

public class Traslado {
    private Date fechaHora;
    private String destino;
    private String motivo;
    private String estado;

    public Traslado(String destino, String motivo) {
        this.fechaHora = new Date();
        this.destino = destino;
        this.motivo = motivo;
        this.estado = "PENDIENTE";
    }

    public void coordinar() {
        this.estado = "EN CURSO";
        System.out.println("\n[TRASLADO] Coordinando traslado a " + destino + ".");
        System.out.println("Motivo: " + motivo + " [Estado: " + estado + "]");
    }
}
