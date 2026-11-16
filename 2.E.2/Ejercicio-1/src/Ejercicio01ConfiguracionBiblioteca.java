package unidad2.e2;

public final class Ejercicio01ConfiguracionBiblioteca {
    private String libraryName = "Biblioteca Central";
    private int maxBooksPerMember = 5;
    private int returnDays = 14;

    private Ejercicio01ConfiguracionBiblioteca() {}

    private static class Holder {
        private static final Ejercicio01ConfiguracionBiblioteca INSTANCE =
                new Ejercicio01ConfiguracionBiblioteca();
    }

    public static Ejercicio01ConfiguracionBiblioteca getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized String getLibraryName() {
        return libraryName;
    }

    public synchronized void setLibraryName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        libraryName = name.trim();
    }

    public synchronized int getMaxBooksPerMember() {
        return maxBooksPerMember;
    }

    public synchronized void setMaxBooksPerMember(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("El maximo debe ser positivo");
        }
        maxBooksPerMember = max;
    }

    public synchronized int getReturnDays() {
        return returnDays;
    }

    public synchronized void setReturnDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("El plazo debe ser positivo");
        }
        returnDays = days;
    }

    private static final class LoanDesk {
        private final Ejercicio01ConfiguracionBiblioteca config;
        private LoanDesk(Ejercicio01ConfiguracionBiblioteca config) { this.config = config; }
        private String summary() {
            return config.getLibraryName() + ": maximo " + config.getMaxBooksPerMember();
        }
    }

    private static final class CatalogDesk {
        private final Ejercicio01ConfiguracionBiblioteca config;
        private CatalogDesk(Ejercicio01ConfiguracionBiblioteca config) { this.config = config; }
        private String summary() {
            return config.getLibraryName() + ": devolucion en " + config.getReturnDays() + " dias";
        }
    }

    public static void main(String[] args) {
        Ejercicio01ConfiguracionBiblioteca shared = getInstance();
        LoanDesk loans = new LoanDesk(shared);
        CatalogDesk catalog = new CatalogDesk(getInstance());
        shared.setLibraryName("Biblioteca Municipal");
        System.out.println(loans.summary());
        System.out.println(catalog.summary());
        System.out.println("Misma instancia: " + (loans.config == catalog.config));
    }
}
