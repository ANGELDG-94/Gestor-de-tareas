public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private Estado estado;

    public Tarea(int id, String titulo, String descripcion, Estado estado) {
        this.id = id; this.titulo = titulo; this.descripcion = descripcion; this.estado = estado;
    }
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado e) { this.estado = e; }

    @Override public String toString() {
        return String.format("[%d] %s | %s | %s", id, titulo, estado, descripcion);
    }
}
