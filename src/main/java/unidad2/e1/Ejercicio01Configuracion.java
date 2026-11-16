package unidad2.e1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Optional;

public final class Ejercicio01Configuracion {
    private final Properties values = new Properties();

    private Ejercicio01Configuracion() {
        Path file = Path.of("config.properties");
        try (InputStream input = Files.newInputStream(file)) {
            values.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo cargar " + file.toAbsolutePath(), exception);
        }
    }

    private static class Holder {
        private static final Ejercicio01Configuracion INSTANCE = new Ejercicio01Configuracion();
    }

    public static Ejercicio01Configuracion getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(values.getProperty(key));
    }

    public static void main(String[] args) {
        Ejercicio01Configuracion config = getInstance();
        System.out.println("Aplicacion: " + config.get("application.name").orElseThrow());
        System.out.println("URL: " + config.get("database.url").orElseThrow());
    }
}
