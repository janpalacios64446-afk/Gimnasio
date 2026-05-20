package gimnasio;

public class Recepcionista extends Usuario {
    private String area;

    public Recepcionista(int id, String nombre, String email, String telefono, String documento, String contrasena, String area) {
        super(id, nombre, email, telefono, documento, contrasena);
        this.area = area;
    }

    public void registrarCliente(Cliente cliente) {
        cliente.registrarse();
        System.out.println("Cliente registrado por recepcionista " + this.nombre);
    }

    public void gestionarMembresia(Membresia membresia, String accion) {
        if (accion.equals("activar")) membresia.activar();
        else if (accion.equals("renovar")) membresia.renovar();
        else System.out.println("Acción no válida");
    }

    public void registrarPago(Pago pago) {
        pago.procesarPago();
        System.out.println("Pago registrado por " + this.nombre);
    }

    public String getArea() { return area; }
}