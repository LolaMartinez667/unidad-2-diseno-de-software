package unidad2.e2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Ejercicio02RegistroActividades {
    public record Activity(LocalDateTime dateTime, String sector, String description) {}

    private final List<Activity> history = new ArrayList<>();

    private Ejercicio02RegistroActividades() {}

    private static class Holder {
        private static final Ejercicio02RegistroActividades INSTANCE =
                new Ejercicio02RegistroActividades();
    }

    public static Ejercicio02RegistroActividades getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void add(String sector, String description) {
        if (sector == null || sector.isBlank() || description == null || description.isBlank()) {
            throw new IllegalArgumentException("Sector y descripcion son obligatorios");
        }
        history.add(new Activity(LocalDateTime.now(), sector, description));
    }

    public synchronized List<Activity> history() {
        return List.copyOf(history);
    }

    public synchronized List<Activity> bySector(String sector) {
        return history.stream().filter(event -> event.sector().equalsIgnoreCase(sector)).toList();
    }

    public synchronized int count() {
        return history.size();
    }

    public static void main(String[] args) {
        Ejercicio02RegistroActividades loans = getInstance();
        Ejercicio02RegistroActividades returns = getInstance();
        Ejercicio02RegistroActividades service = getInstance();
        loans.add("Prestamos", "Prestamo de L-10");
        returns.add("Devoluciones", "Devolucion de L-10");
        service.add("Atencion", "Alta de socio S-20");
        System.out.println("Eventos: " + service.count());
        System.out.println(service.history());
        System.out.println("Prestamos: " + loans.bySector("Prestamos"));
    }
}
