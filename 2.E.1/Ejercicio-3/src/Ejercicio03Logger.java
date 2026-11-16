package unidad2.e1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Ejercicio03Logger {
    public enum Level { INFO, WARNING, ERROR }

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Ejercicio03Logger() {}

    private static class Holder {
        private static final Ejercicio03Logger INSTANCE = new Ejercicio03Logger();
    }

    public static Ejercicio03Logger getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void log(Level level, String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacio");
        }
        System.out.printf("%s [%s] %s%n", LocalDateTime.now().format(FORMAT), level, message);
    }

    public static void main(String[] args) {
        getInstance().log(Level.INFO, "Aplicacion iniciada");
        getInstance().log(Level.WARNING, "Quedan pocos ejemplares");
        getInstance().log(Level.ERROR, "No se pudo completar la operacion");
    }
}
