package unidad2.e1;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Ejercicio04CacheMemoria {
    private final ConcurrentMap<String, String> values = new ConcurrentHashMap<>();

    private Ejercicio04CacheMemoria() {}

    private static class Holder {
        private static final Ejercicio04CacheMemoria INSTANCE = new Ejercicio04CacheMemoria();
    }

    public static Ejercicio04CacheMemoria getInstance() {
        return Holder.INSTANCE;
    }

    public void put(String key, String value) {
        validateKey(key);
        if (value == null) {
            throw new IllegalArgumentException("El valor no puede ser null");
        }
        values.put(key, value);
    }

    public Optional<String> get(String key) {
        validateKey(key);
        return Optional.ofNullable(values.get(key));
    }

    public boolean contains(String key) {
        validateKey(key);
        return values.containsKey(key);
    }

    public boolean remove(String key) {
        validateKey(key);
        return values.remove(key) != null;
    }

    private static void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("La clave no puede estar vacia");
        }
    }

    public static void main(String[] args) {
        Ejercicio04CacheMemoria cache = getInstance();
        cache.put("usuario:42", "Ana Perez");
        System.out.println(cache.get("usuario:42").orElseThrow());
        cache.remove("usuario:42");
    }
}
