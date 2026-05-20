package gimnasio;

import java.time.LocalDate;

public class Membresia {
    private String tipo;
    private double precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    public Membresia(String tipo, double precio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.tipo = tipo;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = true;
    }

    public void activar() {
        this.activa = true;
        System.out.println("Membresía activada hasta " + fechaFin);
    }

    public void renovar() {
        if (tipo.equals("Mensual")) fechaFin = fechaFin.plusMonths(1);
        else if (tipo.equals("Trimestral")) fechaFin = fechaFin.plusMonths(3);
        else if (tipo.equals("Anual")) fechaFin = fechaFin.plusYears(1);
        System.out.println("Membresía renovada. Nueva fecha fin: " + fechaFin);
    }

    public boolean isActiva() { return activa; }
    public String getTipo() { return tipo; }
    public double getPrecio() { return precio; }
    public LocalDate getFechaFin() { return fechaFin; }
}