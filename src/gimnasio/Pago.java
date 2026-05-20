package gimnasio;

import java.time.LocalDate;

public class Pago {
    private double monto;
    private String metodo;
    private LocalDate fecha;
    private String estado;

    public Pago(double monto, String metodo, LocalDate fecha) {
        this.monto = monto;
        this.metodo = metodo;
        this.fecha = fecha;
        this.estado = "Pendiente";
    }

    public void procesarPago() {
        if (validarPago()) {
            this.estado = "Pagado";
            System.out.println("Pago de $" + monto + " por " + metodo + " procesado.");
        } else {
            System.out.println("Error al procesar pago.");
        }
    }

    public boolean validarPago() {
        return monto > 0 && (metodo.equals("Efectivo") || metodo.equals("Tarjeta") || metodo.equals("Transferencia"));
    }

    public double getMonto() { return monto; }
    public String getEstado() { return estado; }
}