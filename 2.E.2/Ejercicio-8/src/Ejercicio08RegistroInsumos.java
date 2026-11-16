package unidad2.e2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Ejercicio08RegistroInsumos {
    public enum MovementType { ENTRADA, SALIDA }
    public record Movement(String supplyCode, MovementType type, int quantity,
                           String sector, LocalDateTime dateTime) {}

    private final List<Movement> history = new ArrayList<>();
    private final Map<String, Integer> balances = new LinkedHashMap<>();

    private Ejercicio08RegistroInsumos() {}

    private static class Holder {
        private static final Ejercicio08RegistroInsumos INSTANCE = new Ejercicio08RegistroInsumos();
    }

    public static Ejercicio08RegistroInsumos getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void register(String code, MovementType type, int quantity, String sector) {
        if (code == null || code.isBlank() || type == null || quantity <= 0
                || sector == null || sector.isBlank()) {
            throw new IllegalArgumentException("Datos de movimiento invalidos");
        }
        int balance = balances.getOrDefault(code, 0);
        if (type == MovementType.SALIDA && quantity > balance) {
            throw new IllegalStateException("La salida supera el saldo disponible");
        }
        int updated = type == MovementType.ENTRADA
                ? Math.addExact(balance, quantity)
                : balance - quantity;
        balances.put(code, updated);
        history.add(new Movement(code, type, quantity, sector, LocalDateTime.now()));
    }

    public synchronized List<Movement> history() {
        return List.copyOf(history);
    }

    public synchronized List<Movement> bySupply(String code) {
        return history.stream().filter(movement -> movement.supplyCode().equals(code)).toList();
    }

    public synchronized List<Movement> bySector(String sector) {
        return history.stream().filter(movement -> movement.sector().equalsIgnoreCase(sector)).toList();
    }

    public synchronized int balance(String code) {
        return balances.getOrDefault(code, 0);
    }

    public static void main(String[] args) {
        Ejercicio08RegistroInsumos warehouse = getInstance();
        Ejercicio08RegistroInsumos clinic = getInstance();
        Ejercicio08RegistroInsumos lab = getInstance();
        warehouse.register("GUANTE-M", MovementType.ENTRADA, 100, "Deposito");
        clinic.register("GUANTE-M", MovementType.SALIDA, 12, "Consultorios");
        lab.register("GUANTE-M", MovementType.SALIDA, 8, "Laboratorio");
        System.out.println("Saldo: " + warehouse.balance("GUANTE-M"));
        System.out.println("Movimientos: " + warehouse.history());
        System.out.println("Instancia compartida: " + (warehouse == clinic && clinic == lab));
    }
}
