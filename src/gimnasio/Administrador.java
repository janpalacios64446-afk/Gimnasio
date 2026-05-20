package gimnasio;

import java.util.ArrayList;

public class Administrador extends Usuario {
    private String nivelAcceso;

    public Administrador(int id, String nombre, String email, String telefono, String documento, String contrasena, String nivelAcceso) {
        super(id, nombre, email, telefono, documento, contrasena);
        this.nivelAcceso = nivelAcceso;
    }

    public void gestionarUsuarios(ArrayList<Usuario> usuarios, Usuario usuario, String operacion) {
        if (operacion.equals("eliminar")) {
            usuarios.remove(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " eliminado.");
        } else {
            System.out.println("Operación no implementada en demo.");
        }
    }

    public void gestionarClases(ArrayList<Clase> clases, Clase clase, String operacion) {
        if (operacion.equals("crear")) {
            clases.add(clase);
            clase.crearClase();
        } else if (operacion.equals("eliminar")) {
            clases.remove(clase);
            System.out.println("Clase eliminada.");
        }
    }

    public void generarReportes(String tipo) {
        System.out.println("Generando reporte de " + tipo);
    }

    public String getNivelAcceso() { return nivelAcceso; }
}