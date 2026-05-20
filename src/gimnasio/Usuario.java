package gimnasio;

public class Usuario {
    protected int id;
    protected String nombre;
    protected String email;
    protected String telefono;
    protected String documento;
    protected String contrasena;

    public Usuario(int id, String nombre, String email, String telefono, String documento, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.contrasena = contrasena;
    }

    public boolean iniciarSesion(String email, String pass) {
        return this.email.equals(email) && this.contrasena.equals(pass);
    }

    public void cerrarSesion() {
        System.out.println(this.nombre + " cerró sesión.");
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDocumento() { return documento; }
    public String getContrasena() { return contrasena; }
}