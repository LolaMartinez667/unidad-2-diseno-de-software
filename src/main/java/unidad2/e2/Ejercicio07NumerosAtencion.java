package unidad2.e2;

import java.util.ArrayList;
import java.util.List;

public final class Ejercicio07NumerosAtencion {
    public record Ticket(int number, String terminal) {}

    private int lastNumber;
    private final List<Ticket> history = new ArrayList<>();

    private Ejercicio07NumerosAtencion() {}

    private static class Holder {
        private static final Ejercicio07NumerosAtencion INSTANCE = new Ejercicio07NumerosAtencion();
    }

    public static Ejercicio07NumerosAtencion getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized int issue(String terminal) {
        if (terminal == null || terminal.isBlank()) {
            throw new IllegalArgumentException("La terminal es obligatoria");
        }
        int number = ++lastNumber;
        history.add(new Ticket(number, terminal));
        return number;
    }

    public synchronized int lastNumber() {
        return lastNumber;
    }

    public synchronized List<Ticket> history() {
        return List.copyOf(history);
    }

    public static void main(String[] args) {
        Ejercicio07NumerosAtencion counter = getInstance();
        String[] terminals = {"T1", "T2", "T3"};
        for (int i = 0; i < 20; i++) {
            System.out.printf("%s entrego el numero %d%n", terminals[i % terminals.length],
                    counter.issue(terminals[i % terminals.length]));
        }
        System.out.println("Ultimo: " + counter.lastNumber());
        System.out.println("Emitidos: " + counter.history().size());
    }
}
