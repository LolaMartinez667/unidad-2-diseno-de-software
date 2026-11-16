package unidad2.e2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public final class Ejercicio04AdministradorPrestamos {
    public record Loan(String copyId, String memberId, LocalDate date) {}

    private final Map<String, Loan> activeLoans = new LinkedHashMap<>();

    private Ejercicio04AdministradorPrestamos() {}

    private static class Holder {
        private static final Ejercicio04AdministradorPrestamos INSTANCE =
                new Ejercicio04AdministradorPrestamos();
    }

    public static Ejercicio04AdministradorPrestamos getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean lend(String copyId, String memberId, LocalDate date) {
        validate(copyId, "ejemplar");
        validate(memberId, "socio");
        if (date == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (activeLoans.containsKey(copyId)) {
            return false;
        }
        activeLoans.put(copyId, new Loan(copyId, memberId, date));
        return true;
    }

    public synchronized boolean returnCopy(String copyId) {
        validate(copyId, "ejemplar");
        return activeLoans.remove(copyId) != null;
    }

    public synchronized List<Loan> activeLoansOf(String memberId) {
        validate(memberId, "socio");
        return activeLoans.values().stream()
                .filter(loan -> loan.memberId().equals(memberId))
                .toList();
    }

    private static void validate(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El identificador de " + name + " es obligatorio");
        }
    }

    public static void main(String[] args) {
        Ejercicio04AdministradorPrestamos counterA = getInstance();
        Ejercicio04AdministradorPrestamos counterB = getInstance();
        System.out.println("Mostrador A presta: " + counterA.lend("COPIA-001", "SOCIO-1", LocalDate.now()));
        System.out.println("Mostrador B presta: " + counterB.lend("COPIA-001", "SOCIO-2", LocalDate.now()));
        System.out.println(counterB.activeLoansOf("SOCIO-1"));
        counterB.returnCopy("COPIA-001");
    }
}
