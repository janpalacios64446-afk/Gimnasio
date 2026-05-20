package gimnasio;

public class Asistencia {
    private String fecha;
    private boolean presente;

    public Asistencia(String fecha, boolean presente) {
        this.fecha = fecha;
        this.presente = presente;
    }

    public void registrar() {
        System.out.println("Asistencia registrada el " + fecha + " - " + (presente ? "Presente" : "Ausente"));
    }

    public String getFecha() { return fecha; }
    public boolean isPresente() { return presente; }
}