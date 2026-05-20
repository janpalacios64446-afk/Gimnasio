package gimnasio;

import java.time.LocalTime;

public class Horario {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String dia;

    public Horario(LocalTime horaInicio, LocalTime horaFin, String dia) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
    }

    public void crearHorario() {
        System.out.println("Horario creado: " + dia + " de " + horaInicio + " a " + horaFin);
    }

    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public String getDia() { return dia; }
}