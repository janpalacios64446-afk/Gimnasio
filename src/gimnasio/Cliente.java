package gimnasio;

public class Cliente extends Usuario {
    private FichaTecnica ficha;
    private Membresia membresia;

    // Constructor: ya no recibe "estado", se calcula automáticamente
    public Cliente(int id, String nombre, String email, String telefono, String documento, String contrasena) {
        super(id, nombre, email, telefono, documento, contrasena);
        this.ficha = null;
        this.membresia = null;
    }

    // Método que calcula si el cliente está activo (tiene membresía activa)
    public boolean isActivo() {
        return membresia != null && membresia.isActiva();
    }

    // Métodos del diagrama
    public void registrarse() {
        System.out.println("Cliente " + this.nombre + " registrado. Estado: " + (isActivo() ? "Activo" : "Inactivo"));
    }

    public void actualizarDatos(String nuevoNombre, String nuevoTelefono) {
        this.nombre = nuevoNombre;
        this.telefono = nuevoTelefono;
        System.out.println("Datos actualizados.");
    }

    public void reservarClase(Clase clase, String fecha) {
        if (isActivo()) {
            System.out.println("Reservando clase " + clase.getNombre() + " para " + fecha);
        } else {
            System.out.println("No puedes reservar clases. Tu membresía no está activa.");
        }
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.cancelarReserva();
    }

    // Getters y setters
    public void setFicha(FichaTecnica ficha) { this.ficha = ficha; }
    public FichaTecnica getFicha() { return ficha; }
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }
    public Membresia getMembresia() { return membresia; }
}