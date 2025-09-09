import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RepoTareas repo = new RepoTareas();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Gestor de Tareas ---");
            System.out.println("1. Crear tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Actualizar estado");
            System.out.println("4. Eliminar tarea");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();



            try {
                switch (op) {
                    case "1" -> {
                        System.out.print("Título: "); String titulo = sc.nextLine();
                        System.out.print("Descripción: "); String desc = sc.nextLine();
                        Tarea t = repo.crear(titulo, desc);
                        System.out.println("Creada: " + t);
                    }
                    case "2" -> {
                        List<Tarea> lista = repo.listar();
                        if (lista.isEmpty()) System.out.println("No hay tareas.");
                        else lista.forEach(System.out::println);
                    }
                    case "3" -> {
                        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Estado (PENDIENTE, EN_PROGRESO, COMPLETADA): ");
                        Estado e = Estado.valueOf(sc.nextLine().trim().toUpperCase());
                        System.out.println(repo.actualizarEstado(id, e) ? "Actualizada." : "No encontrada.");
                    }
                    case "4" -> {
                        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.println(repo.eliminar(id) ? "Eliminada." : "No encontrada.");
                    }
                    case "0" -> { System.out.println("Hasta luego."); return; }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}