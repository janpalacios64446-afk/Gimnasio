package gimnasio;

public class Clase {
    private String nombre;
    private int cupoMaximo;
    private Horario horario;

    public Clase(String nombre, int cupoMaximo) {
        this.nombre = nombre;
        this.cupoMaximo = cupoMaximo;
    }

    public void crearClase() {
        System.out.println("Clase " + nombre + " creada con cupo máximo " + cupoMaximo);
    }

    public void actualizarCupo(int nuevoCupo) {
        this.cupoMaximo = nuevoCupo;
        System.out.println("Cupo actualizado a " + cupoMaximo);
    }

    public void setHorario(Horario horario) { this.horario = horario; }
    public Horario getHorario() { return horario; }
    public String getNombre() { return nombre; }
    public int getCupoMaximo() { return cupoMaximo; }
}