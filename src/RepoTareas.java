import java.nio.file.*;
        import java.util.*;
        import java.io.IOException;

public class RepoTareas {
    private final Path archivo = Paths.get("tareas.csv");
    private final List<Tarea> tareas = new ArrayList<>();
    private int nextId = 1;

    public RepoTareas() { cargar(); }

    // Separador simple: ; (escapamos ; y saltos de línea)
    private String esc(String s){ return s.replace(";", "\\;").replace("\n", "\\n"); }
    private String des(String s){ return s.replace("\\n","\n").replace("\\;",";"); }

    private void cargar() {
        if (!Files.exists(archivo)) return;
        try {
            for (String line : Files.readAllLines(archivo)) {
                if (line.isBlank()) continue;
                String[] p = splitCsv(line);
                int id = Integer.parseInt(p[0]);
                String titulo = des(p[1]);
                String desc = des(p[2]);
                Estado estado = Estado.valueOf(p[3]);
                tareas.add(new Tarea(id, titulo, desc, estado));
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) { throw new RuntimeException("Error cargando CSV", e); }
    }

    private void guardar() {
        List<String> lines = new ArrayList<>();
        for (Tarea t : tareas) {
            lines.add(t.getId() + ";" + esc(t.getTitulo()) + ";" + esc(t.getDescripcion()) + ";" + t.getEstado().name());
        }
        try { Files.write(archivo, lines); } catch (IOException e) { throw new RuntimeException("Error guardando CSV", e); }
    }

    private String[] splitCsv(String line){
        // separa por ; ignorando las secuencias escapadas \;
        List<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (int i=0;i<line.length();i++){
            char c = line.charAt(i);
            if (escape){ sb.append(c); escape=false; continue; }
            if (c=='\\'){ escape=true; continue; }
            if (c==';'){ out.add(sb.toString()); sb.setLength(0); }
            else sb.append(c);
        }
        out.add(sb.toString());
        return out.toArray(new String[0]);
    }

    // CRUD
    public Tarea crear(String titulo, String desc){
        Tarea t = new Tarea(nextId++, titulo, desc, Estado.PENDIENTE);
        tareas.add(t); guardar(); return t;
    }
    public List<Tarea> listar(){ return Collections.unmodifiableList(tareas); }
    public boolean actualizarEstado(int id, Estado e){
        for (Tarea t : tareas) if (t.getId()==id){ t.setEstado(e); guardar(); return true; }
        return false;
    }
    public boolean eliminar(int id){
        boolean ok = tareas.removeIf(t -> t.getId()==id);
        if (ok) guardar();
        return ok;
    }
}