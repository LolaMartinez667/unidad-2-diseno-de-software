package unidad2.e2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Ejercicio03CatalogoLibros {
    public record Book(String code, String title, String author, int copies) {}

    private final Map<String, Book> books = new LinkedHashMap<>();

    private Ejercicio03CatalogoLibros() {}

    private static class Holder {
        private static final Ejercicio03CatalogoLibros INSTANCE = new Ejercicio03CatalogoLibros();
    }

    public static Ejercicio03CatalogoLibros getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void add(String code, String title, String author, int copies) {
        if (code == null || code.isBlank() || title == null || title.isBlank()
                || author == null || author.isBlank() || copies < 0) {
            throw new IllegalArgumentException("Datos de libro invalidos");
        }
        if (books.containsKey(code)) {
            throw new IllegalArgumentException("El codigo ya existe: " + code);
        }
        books.put(code, new Book(code, title, author, copies));
    }

    public synchronized boolean updateCopies(String code, int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        Book current = books.get(code);
        if (current == null) {
            return false;
        }
        books.put(code, new Book(current.code(), current.title(), current.author(), copies));
        return true;
    }

    public synchronized List<Book> search(String query) {
        if (query == null) {
            throw new IllegalArgumentException("La busqueda no puede ser null");
        }
        String normalized = query.toLowerCase();
        return books.values().stream()
                .filter(book -> book.title().toLowerCase().contains(normalized)
                        || book.author().toLowerCase().contains(normalized))
                .toList();
    }

    public synchronized List<Book> all() {
        return List.copyOf(new ArrayList<>(books.values()));
    }

    public static void main(String[] args) {
        Ejercicio03CatalogoLibros staffScreen = getInstance();
        Ejercicio03CatalogoLibros queryScreen = getInstance();
        staffScreen.add("L-01", "El principito", "Antoine de Saint-Exupery", 4);
        System.out.println("Catalogo de consulta: " + queryScreen.search("principito"));
        staffScreen.updateCopies("L-01", 3);
        System.out.println("Instancia compartida: " + (staffScreen == queryScreen));
    }
}
