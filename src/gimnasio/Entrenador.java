package gimnasio;

public class Entrenador extends Usuario {
    private String especialidad;

    public Entrenador(int id, String nombre, String email, String telefono, String documento, String contrasena, String especialidad) {
        super(id, nombre, email, telefono, documento, contrasena);
        this.especialidad = especialidad;
    }

    public void verHorario() {
        System.out.println("Horario del entrenador " + this.nombre);
    }

    public void marcarAsistencia(Cliente cliente, Clase clase, String fecha) {
        Asistencia a = new Asistencia(fecha, true);
        a.registrar();
        System.out.println("Asistencia de " + cliente.getNombre() + " a " + clase.getNombre() + " marcada.");
    }

    public void consultarFichaTecnica(Cliente cliente) {
        if (cliente.getFicha() != null) {
            cliente.getFicha().actualizar();
        } else {
            System.out.println("El cliente no tiene ficha técnica.");
        }
    }

    public String getEspecialidad() { return especialidad; }
}