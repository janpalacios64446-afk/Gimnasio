package gimnasio;

public class FichaTecnica {
    private float estatura;
    private float peso;
    private int edad;
    private String historiaClinica;

    public FichaTecnica(float estatura, float peso, int edad, String historiaClinica) {
        this.estatura = estatura;
        this.peso = peso;
        this.edad = edad;
        this.historiaClinica = historiaClinica;
    }

    public void actualizar() {
        System.out.println("Estatura: " + estatura + " m | Peso: " + peso + " kg | Edad: " + edad);
        System.out.println("Historia clínica: " + historiaClinica);
    }

    // Getters y setters
    public float getEstatura() { return estatura; }
    public void setEstatura(float estatura) { this.estatura = estatura; }
    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(String historiaClinica) { this.historiaClinica = historiaClinica; }
}