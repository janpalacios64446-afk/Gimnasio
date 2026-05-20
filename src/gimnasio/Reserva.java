package gimnasio;

public class Reserva {
    private String fecha;
    private String estado;

    public Reserva(String fecha) {
        this.fecha = fecha;
        this.estado = "Confirmada";
    }

    public void crearReserva() {
        System.out.println("Reserva creada para el " + fecha + " - Estado: " + estado);
    }

    public void cancelarReserva() {
        if (estado.equals("Confirmada")) {
            this.estado = "Cancelada";
            System.out.println("Reserva cancelada.");
        } else {
            System.out.println("La reserva ya estaba cancelada.");
        }
    }

    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
}