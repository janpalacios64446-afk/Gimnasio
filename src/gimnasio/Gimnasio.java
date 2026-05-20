package gimnasio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gimnasio {
    // Listas
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Entrenador> entrenadores = new ArrayList<>();
    private static ArrayList<Recepcionista> recepcionistas = new ArrayList<>();
    private static ArrayList<Administrador> administradores = new ArrayList<>();
    private static ArrayList<Clase> clases = new ArrayList<>();
    private static ArrayList<Reserva> reservas = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static int nextId = 1;
    private static Usuario usuarioActual = null;

    // Claves maestras por rol
    private static final String CLAVE_ENTRENADOR = "Entr3nador#2026";
    private static final String CLAVE_RECEPCIONISTA = "Recepc1on#2026";
    private static final String CLAVE_ADMIN = "Adm1nMaster#2026";

    // ================= VALIDACIONES =================
    private static boolean validarEmail(String email) {
        // No espacios, debe tener @ y punto, no empezar/terminar con punto, no dos puntos seguidos
        if (email == null || email.contains(" ") || !email.contains("@") || !email.contains(".")) return false;
        if (email.startsWith(".") || email.endsWith(".")) return false;
        if (email.contains("..")) return false;
        String localPart = email.split("@")[0];
        if (localPart.startsWith(".") || localPart.endsWith(".")) return false;
        return true;
    }

    private static boolean validarNombre(String nombre) {
        // Solo letras y espacios (al menos una letra), sin espacios al inicio/final
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    private static boolean validarTelefono(String telefono) {
        if (telefono == null) return false;
        if (telefono.startsWith("+")) telefono = telefono.substring(1);
        return telefono.matches("\\d+");
    }

    private static boolean validarDocumento(String doc) {
        return doc != null && doc.matches("\\d+");
    }

    private static double validarDouble(String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Double.parseDouble(sc.nextLine());
                if (valor > 0) return valor;
                System.out.println("Debe ser positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    private static int validarEntero(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(sc.nextLine());
                if (valor > 0) return valor;
                System.out.println("Debe ser positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Número entero inválido.");
            }
        }
    }

    private static String validarEmailPrompt() {
        String email;
        do {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (!validarEmail(email)) System.out.println("Email inválido (sin espacios, formato correcto, sin puntos seguidos).");
        } while (!validarEmail(email));
        return email;
    }

    private static String validarNombrePrompt() {
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = sc.nextLine().trim();
            if (!validarNombre(nombre)) System.out.println("Nombre inválido (solo letras y espacios).");
        } while (!validarNombre(nombre));
        return nombre;
    }

    private static String validarTelefonoPrompt() {
        String tel;
        do {
            System.out.print("Teléfono (solo números, + opcional al inicio): ");
            tel = sc.nextLine();
            if (!validarTelefono(tel)) System.out.println("Teléfono inválido.");
        } while (!validarTelefono(tel));
        return tel;
    }

    private static String validarDocumentoPrompt() {
        String doc;
        do {
            System.out.print("Documento (solo números): ");
            doc = sc.nextLine();
            if (!validarDocumento(doc)) System.out.println("Documento inválido.");
        } while (!validarDocumento(doc));
        return doc;
    }

    // ================= PRECIOS MEMBRESÍAS =================
    private static void mostrarPrecios() {
        System.out.println("\n--- PRECIOS MEMBRESÍAS ---");
        System.out.println("1. Mensual       $45.000");
        System.out.println("2. Trimestral    $120.000");
        System.out.println("3. Anual         $400.000");
        System.out.println("--------------------------");
    }

    // ================= CREAR MEMBRESÍA (con o sin activación) =================
    // retorna la membresía creada; si metodoPago es "Efectivo", queda inactiva; si es otro, se activa tras pago simulado
    private static Membresia crearMembresia(boolean activarInmediato) {
        mostrarPrecios();
        System.out.print("Tipo (Mensual/Trimestral/Anual): ");
        String tipo = sc.nextLine();
        double precio;
        LocalDate inicio = LocalDate.now();
        LocalDate fin;
        if (tipo.equalsIgnoreCase("Mensual")) {
            precio = 45000;
            fin = inicio.plusMonths(1);
        } else if (tipo.equalsIgnoreCase("Trimestral")) {
            precio = 120000;
            fin = inicio.plusMonths(3);
        } else if (tipo.equalsIgnoreCase("Anual")) {
            precio = 400000;
            fin = inicio.plusYears(1);
        } else {
            System.out.println("Tipo inválido.");
            return null;
        }
        Membresia mem = new Membresia(tipo, precio, inicio, fin);
        if (activarInmediato) {
            mem.activar();
            System.out.println("Membresía activada.");
        } else {
            System.out.println("Membresía creada (pendiente de pago).");
        }
        return mem;
    }

    // ================= MÉTODOS PRINCIPALES =================
    public static void main(String[] args) {
        int opcion;
        do {
            if (usuarioActual == null) {
                System.out.println("\n===== SISTEMA DE GIMNASIO =====");
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Registrar cuenta");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    opcion = -1;
                }
                switch (opcion) {
                    case 1: iniciarSesion(); break;
                    case 2: registrarCuenta(); break;
                    case 0: System.out.println("Saliendo..."); break;
                    default: System.out.println("Opción inválida");
                }
            } else {
                if (usuarioActual instanceof Cliente) mostrarMenuCliente();
                else if (usuarioActual instanceof Entrenador) mostrarMenuEntrenador();
                else if (usuarioActual instanceof Recepcionista) mostrarMenuRecepcionista();
                else if (usuarioActual instanceof Administrador) mostrarMenuAdministrador();
                opcion = -1;
            }
        } while (opcion != 0);
    }

    // ================= AUTENTICACIÓN =================
    private static void iniciarSesion() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        for (Cliente c : clientes) {
            if (c.getEmail().equals(email) && c.getContrasena().equals(pass)) {
                usuarioActual = c;
                System.out.println("Bienvenido " + c.getNombre() + " (Cliente)");
                if (!c.isActivo()) System.out.println("*** Tu membresía no está activa. Acude a recepción para pagar. ***");
                return;
            }
        }
        for (Entrenador e : entrenadores) {
            if (e.getEmail().equals(email) && e.getContrasena().equals(pass)) {
                usuarioActual = e;
                System.out.println("Bienvenido entrenador " + e.getNombre());
                return;
            }
        }
        for (Recepcionista r : recepcionistas) {
            if (r.getEmail().equals(email) && r.getContrasena().equals(pass)) {
                usuarioActual = r;
                System.out.println("Bienvenido recepcionista " + r.getNombre());
                return;
            }
        }
        for (Administrador a : administradores) {
            if (a.getEmail().equals(email) && a.getContrasena().equals(pass)) {
                usuarioActual = a;
                System.out.println("Bienvenido administrador " + a.getNombre());
                return;
            }
        }
        System.out.println("Email o contraseña incorrectos.");
    }

    private static void registrarCuenta() {
        System.out.println("\n--- Registro de nueva cuenta ---");
        System.out.println("Tipos:");
        System.out.println("1. Cliente");
        System.out.println("2. Entrenador (requiere clave)");
        System.out.println("3. Recepcionista (requiere clave)");
        System.out.println("4. Administrador (requiere clave)");
        System.out.print("Opción: ");
        int tipo;
        try {
            tipo = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            tipo = 0;
        }

        String nombre = validarNombrePrompt();
        String email = validarEmailPrompt();
        String telefono = validarTelefonoPrompt();
        String documento = validarDocumentoPrompt();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();

        // Validar clave maestra según rol
        if (tipo == 2) {
            System.out.print("Clave maestra de entrenador: ");
            if (!sc.nextLine().equals(CLAVE_ENTRENADOR)) {
                System.out.println("Clave incorrecta.");
                return;
            }
        } else if (tipo == 3) {
            System.out.print("Clave maestra de recepcionista: ");
            if (!sc.nextLine().equals(CLAVE_RECEPCIONISTA)) {
                System.out.println("Clave incorrecta.");
                return;
            }
        } else if (tipo == 4) {
            System.out.print("Clave maestra de administrador: ");
            if (!sc.nextLine().equals(CLAVE_ADMIN)) {
                System.out.println("Clave incorrecta.");
                return;
            }
        }

        switch (tipo) {
            case 1:
                Cliente cli = new Cliente(nextId++, nombre, email, telefono, documento, contrasena);
                clientes.add(cli);
                System.out.println("Cliente registrado con ID " + cli.getId() + ". Estado: inactivo (sin membresía)");
                System.out.print("¿Desea comprar membresía ahora? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    System.out.print("Método de pago (Efectivo/Tarjeta/Transferencia): ");
                    String metodo = sc.nextLine();
                    boolean activar = !metodo.equalsIgnoreCase("Efectivo");
                    Membresia mem = crearMembresia(activar);
                    if (mem != null) {
                        cli.setMembresia(mem);
                        if (!activar) System.out.println("Membresía registrada. Debe pagar en sede para activarla.");
                    }
                }
                System.out.print("¿Desea agregar ficha técnica? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    float est = (float) validarDouble("Estatura (m): ");
                    float pes = (float) validarDouble("Peso (kg): ");
                    int edad = validarEntero("Edad: ");
                    System.out.print("Historial médico: ");
                    String hist = sc.nextLine();
                    cli.setFicha(new FichaTecnica(est, pes, edad, hist));
                }
                break;
            case 2:
                System.out.print("Especialidad: ");
                String esp = sc.nextLine();
                Entrenador ent = new Entrenador(nextId++, nombre, email, telefono, documento, contrasena, esp);
                entrenadores.add(ent);
                System.out.println("Entrenador registrado.");
                break;
            case 3:
                System.out.print("Área: ");
                String area = sc.nextLine();
                Recepcionista rec = new Recepcionista(nextId++, nombre, email, telefono, documento, contrasena, area);
                recepcionistas.add(rec);
                System.out.println("Recepcionista registrado.");
                break;
            case 4:
                System.out.print("Nivel de acceso: ");
                String nivel = sc.nextLine();
                Administrador adm = new Administrador(nextId++, nombre, email, telefono, documento, contrasena, nivel);
                administradores.add(adm);
                System.out.println("Administrador registrado.");
                break;
            default: System.out.println("Tipo no válido");
        }
    }

    // ================= MENÚ CLIENTE =================
    private static void mostrarMenuCliente() {
        int opcion;
        Cliente cli = (Cliente) usuarioActual;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("Bienvenido " + cli.getNombre());
            System.out.println("Estado: " + (cli.isActivo() ? "ACTIVO" : "INACTIVO (sin membresía activa)"));
            System.out.println("1. Ver ficha técnica");
            System.out.println("2. Actualizar mis datos");
            System.out.println("3. Reservar clase");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Ver clases disponibles");
            System.out.println("6. Ver membresía");
            System.out.println("7. Comprar/renovar membresía");
            System.out.println("8. Cerrar sesión");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    if (cli.getFicha() != null) cli.getFicha().actualizar();
                    else System.out.println("No tiene ficha técnica.");
                    break;
                case 2:
                    System.out.print("Nuevo nombre: ");
                    String nnom = validarNombrePrompt();
                    String ntel = validarTelefonoPrompt();
                    cli.actualizarDatos(nnom, ntel);
                    break;
                case 3:
                    if (!cli.isActivo()) {
                        System.out.println("No puede reservar clases sin membresía activa. Pague en recepción.");
                    } else {
                        reservarClase(cli);
                    }
                    break;
                case 4:
                    cancelarReserva(cli);
                    break;
                case 5:
                    listarClases();
                    break;
                case 6:
                    if (cli.getMembresia() != null) {
                        Membresia m = cli.getMembresia();
                        System.out.println("Tipo: " + m.getTipo() + " | Precio: $" + m.getPrecio() + " | Vigencia: " + m.getFechaFin());
                        System.out.println("Estado: " + (m.isActiva() ? "Activa" : "Pendiente de pago en sede"));
                    } else {
                        System.out.println("No tiene membresía.");
                    }
                    break;
                case 7:
                    comprarMembresia(cli);
                    break;
                case 8:
                    usuarioActual.cerrarSesion();
                    usuarioActual = null;
                    break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 8);
    }

    private static void comprarMembresia(Cliente cli) {
        System.out.print("Método de pago (Efectivo/Tarjeta/Transferencia): ");
        String metodo = sc.nextLine();
        boolean activar = !metodo.equalsIgnoreCase("Efectivo");
        Membresia mem = crearMembresia(activar);
        if (mem != null) {
            cli.setMembresia(mem);
            if (!activar) System.out.println("Membresía registrada. Debe pagar en una sede para activarla.");
        }
    }

    // ================= MENÚ ENTRENADOR =================
    private static void mostrarMenuEntrenador() {
        int opcion;
        Entrenador ent = (Entrenador) usuarioActual;
        do {
            System.out.println("\n--- MENÚ ENTRENADOR ---");
            System.out.println("Bienvenido " + ent.getNombre());
            System.out.println("1. Ver horario");
            System.out.println("2. Marcar asistencia");
            System.out.println("3. Consultar ficha de cliente");
            System.out.println("4. Ver clases");
            System.out.println("5. Cerrar sesión");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            switch (opcion) {
                case 1: ent.verHorario(); break;
                case 2: marcarAsistencia(ent); break;
                case 3: consultarFichaCliente(); break;
                case 4: listarClases(); break;
                case 5:
                    usuarioActual.cerrarSesion();
                    usuarioActual = null;
                    break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    // ================= MENÚ RECEPCIONISTA =================
    private static void mostrarMenuRecepcionista() {
        int opcion;
        Recepcionista rec = (Recepcionista) usuarioActual;
        do {
            System.out.println("\n--- MENÚ RECEPCIONISTA ---");
            System.out.println("Bienvenido " + rec.getNombre());
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Activar membresía pendiente (pago en efectivo)");
            System.out.println("3. Registrar pago en efectivo y activar membresía");
            System.out.println("4. Ver clientes");
            System.out.println("5. Ver clases");
            System.out.println("6. Cerrar sesión");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1: registrarClientePorRecepcion(rec); break;
                case 2: activarMembresiaPendiente(); break;
                case 3: pagarEfectivoYAactivar(); break;
                case 4: listarClientes(); break;
                case 5: listarClases(); break;
                case 6:
                    usuarioActual.cerrarSesion();
                    usuarioActual = null;
                    break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 6);
    }

    private static void activarMembresiaPendiente() {
        listarClientes();
        System.out.print("ID del cliente: ");
        int id = Integer.parseInt(sc.nextLine());
        Cliente cli = buscarCliente(id);
        if (cli == null) return;
        if (cli.getMembresia() == null) {
            System.out.println("El cliente no tiene membresía. Cree una primero.");
            return;
        }
        if (cli.getMembresia().isActiva()) {
            System.out.println("La membresía ya está activa.");
            return;
        }
        System.out.print("Confirmar pago en efectivo recibido? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            cli.getMembresia().activar();
            System.out.println("Membresía activada. El cliente ya puede usar el gimnasio.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void pagarEfectivoYAactivar() {
        listarClientes();
        System.out.print("ID del cliente: ");
        int id = Integer.parseInt(sc.nextLine());
        Cliente cli = buscarCliente(id);
        if (cli == null) return;
        System.out.print("Tipo de membresía a comprar (Mensual/Trimestral/Anual): ");
        String tipo = sc.nextLine();
        double precio;
        LocalDate inicio = LocalDate.now();
        LocalDate fin;
        if (tipo.equalsIgnoreCase("Mensual")) {
            precio = 45000;
            fin = inicio.plusMonths(1);
        } else if (tipo.equalsIgnoreCase("Trimestral")) {
            precio = 120000;
            fin = inicio.plusMonths(3);
        } else if (tipo.equalsIgnoreCase("Anual")) {
            precio = 400000;
            fin = inicio.plusYears(1);
        } else {
            System.out.println("Tipo inválido.");
            return;
        }
        Membresia mem = new Membresia(tipo, precio, inicio, fin);
        mem.activar(); // el recepcionista la activa de inmediato porque ya pagó
        cli.setMembresia(mem);
        System.out.println("Membresía activada. Cliente ya puede acceder.");
    }

    private static void registrarClientePorRecepcion(Recepcionista rec) {
        System.out.println("--- Registro rápido de cliente ---");
        String nombre = validarNombrePrompt();
        String email = validarEmailPrompt();
        String telefono = validarTelefonoPrompt();
        String documento = validarDocumentoPrompt();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();
        Cliente c = new Cliente(nextId++, nombre, email, telefono, documento, pass);
        clientes.add(c);
        rec.registrarCliente(c);
        System.out.println("Cliente registrado. ID: " + c.getId());
        System.out.print("¿Registrar membresía ahora? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            pagarEfectivoYAactivar(); // reutiliza el método anterior, pero usando c ya
            // Como el método anterior pide ID, mejor lo adapto: llamaremos a un método interno
            // Para simplificar, hare una llamada directa: 
        }
    }

    // ================= MENÚ ADMINISTRADOR =================
    private static void mostrarMenuAdministrador() {
        int opcion;
        Administrador admin = (Administrador) usuarioActual;
        do {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("Bienvenido " + admin.getNombre());
            System.out.println("1. Gestionar usuarios (eliminar)");
            System.out.println("2. Gestionar clases (crear/eliminar)");
            System.out.println("3. Generar reportes");
            System.out.println("4. Ver todos los usuarios");
            System.out.println("5. Ver clases");
            System.out.println("6. Cerrar sesión");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            switch (opcion) {
                case 1: gestionarUsuarios(admin); break;
                case 2: gestionarClases(admin); break;
                case 3:
                    System.out.print("Tipo reporte (ingresos/usuarios): ");
                    String tipo = sc.nextLine();
                    admin.generarReportes(tipo);
                    break;
                case 4: listarTodosUsuarios(); break;
                case 5: listarClases(); break;
                case 6:
                    usuarioActual.cerrarSesion();
                    usuarioActual = null;
                    break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 6);
    }

    // ================= MÉTODOS COMUNES =================
    private static void listarClientes() {
        if (clientes.isEmpty()) { System.out.println("No hay clientes."); return; }
        for (Cliente c : clientes) {
            String estado = c.isActivo() ? "Activo" : "Inactivo";
            System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " | " + c.getEmail() + " | " + estado);
        }
    }

    private static void listarClases() {
        if (clases.isEmpty()) { System.out.println("No hay clases."); return; }
        for (Clase c : clases) {
            System.out.print("Clase: " + c.getNombre() + " | Cupo: " + c.getCupoMaximo());
            if (c.getHorario() != null) { System.out.print(" | Horario: "); c.getHorario().crearHorario(); }
            else System.out.println();
        }
    }

    private static void listarTodosUsuarios() {
        System.out.println("\n--- CLIENTES ---"); listarClientes();
        System.out.println("\n--- ENTRENADORES ---");
        for (Entrenador e : entrenadores) System.out.println("ID: " + e.getId() + " | " + e.getNombre());
        System.out.println("\n--- RECEPCIONISTAS ---");
        for (Recepcionista r : recepcionistas) System.out.println("ID: " + r.getId() + " | " + r.getNombre());
        System.out.println("\n--- ADMINISTRADORES ---");
        for (Administrador a : administradores) System.out.println("ID: " + a.getId() + " | " + a.getNombre());
    }

    private static void reservarClase(Cliente cli) {
        if (clases.isEmpty()) { System.out.println("No hay clases."); return; }
        listarClases();
        System.out.print("Nombre de la clase: ");
        String nom = sc.nextLine();
        Clase clase = buscarClase(nom);
        if (clase == null) return;
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        try { LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE); }
        catch (DateTimeParseException e) { System.out.println("Fecha inválida"); return; }
        Reserva r = new Reserva(fecha);
        r.crearReserva();
        reservas.add(r);
        cli.reservarClase(clase, fecha);
        System.out.println("Reserva realizada.");
    }

    private static void cancelarReserva(Cliente cli) {
        System.out.print("Fecha de reserva a cancelar (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        for (Reserva r : reservas) {
            if (r.getFecha().equals(fecha) && r.getEstado().equals("Confirmada")) {
                cli.cancelarReserva(r);
                return;
            }
        }
        System.out.println("Reserva no encontrada.");
    }

    private static void marcarAsistencia(Entrenador ent) {
        listarClientes();
        System.out.print("ID cliente: ");
        int id = Integer.parseInt(sc.nextLine());
        Cliente cli = buscarCliente(id);
        if (cli == null) return;
        listarClases();
        System.out.print("Nombre clase: ");
        String nom = sc.nextLine();
        Clase clase = buscarClase(nom);
        if (clase == null) return;
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        ent.marcarAsistencia(cli, clase, fecha);
    }

    private static void consultarFichaCliente() {
        listarClientes();
        System.out.print("ID cliente: ");
        int id = Integer.parseInt(sc.nextLine());
        Cliente cli = buscarCliente(id);
        if (cli == null) return;
        ((Entrenador)usuarioActual).consultarFichaTecnica(cli);
    }

    private static void gestionarUsuarios(Administrador admin) {
        listarTodosUsuarios();
        System.out.print("ID a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = false;
        for (Cliente c : clientes) if (c.getId() == id) { admin.gestionarUsuarios(new ArrayList<>(), c, "eliminar"); clientes.remove(c); ok = true; break; }
        if (!ok) for (Entrenador e : entrenadores) if (e.getId() == id) { admin.gestionarUsuarios(new ArrayList<>(), e, "eliminar"); entrenadores.remove(e); ok = true; break; }
        if (!ok) for (Recepcionista r : recepcionistas) if (r.getId() == id) { admin.gestionarUsuarios(new ArrayList<>(), r, "eliminar"); recepcionistas.remove(r); ok = true; break; }
        if (!ok) for (Administrador a : administradores) if (a.getId() == id) { admin.gestionarUsuarios(new ArrayList<>(), a, "eliminar"); administradores.remove(a); ok = true; break; }
        if (!ok) System.out.println("ID no encontrado.");
    }

    private static void gestionarClases(Administrador admin) {
        System.out.println("1. Crear clase\n2. Eliminar clase");
        System.out.print("Opción: ");
        int op = Integer.parseInt(sc.nextLine());
        if (op == 1) {
            System.out.print("Nombre: ");
            String nom = sc.nextLine();
            int cupo = validarEntero("Cupo máximo: ");
            Clase c = new Clase(nom, cupo);
            System.out.print("Día: ");
            String dia = sc.nextLine();
            System.out.print("Hora inicio (HH:MM): ");
            LocalTime hi = LocalTime.parse(sc.nextLine());
            System.out.print("Hora fin (HH:MM): ");
            LocalTime hf = LocalTime.parse(sc.nextLine());
            c.setHorario(new Horario(hi, hf, dia));
            admin.gestionarClases(clases, c, "crear");
            clases.add(c);
        } else if (op == 2) {
            listarClases();
            System.out.print("Nombre clase a eliminar: ");
            String nom = sc.nextLine();
            Clase c = buscarClase(nom);
            if (c != null) {
                admin.gestionarClases(clases, c, "eliminar");
                clases.remove(c);
            }
        } else System.out.println("Opción inválida");
    }

    private static Cliente buscarCliente(int id) {
        for (Cliente c : clientes) if (c.getId() == id) return c;
        System.out.println("Cliente no encontrado.");
        return null;
    }

    private static Clase buscarClase(String nombre) {
        for (Clase c : clases) if (c.getNombre().equalsIgnoreCase(nombre)) return c;
        System.out.println("Clase no encontrada.");
        return null;
    }
}