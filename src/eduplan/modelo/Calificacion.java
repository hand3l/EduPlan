package eduplan.modelo;

public class Calificacion {
    private int id;
    private Usuario estudiante;  // Cambiado de int estudianteId a Usuario estudiante
    private String materia;
    private double nota;

    public Calificacion(int id, Usuario estudiante, String materia, double nota) {
        this.id = id;
        this.estudiante = estudiante;
        this.materia = materia;
        this.nota = nota;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public String getMateria() {
        return materia;
    }

    public double getNota() {
        return nota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id=" + id +
                ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "N/A") +
                ", materia='" + materia + '\'' +
                ", nota=" + nota +
                '}';
    }
}




