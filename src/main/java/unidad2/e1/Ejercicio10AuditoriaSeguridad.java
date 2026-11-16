package unidad2.e1;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public enum Ejercicio10AuditoriaSeguridad {
    INSTANCE;

    public record AuditEvent(long sequence, Instant timestamp, String sessionId, String action) {}

    private final List<AuditEvent> events = new ArrayList<>();

    public static Ejercicio10AuditoriaSeguridad getInstance() {
        return INSTANCE;
    }

    public synchronized AuditEvent record(String sessionId, String action) {
        if (sessionId == null || sessionId.isBlank() || action == null || action.isBlank()) {
            throw new IllegalArgumentException("La sesion y la accion son obligatorias");
        }
        Instant now = Instant.now();
        if (!events.isEmpty() && !now.isAfter(events.get(events.size() - 1).timestamp())) {
            now = events.get(events.size() - 1).timestamp().plusNanos(1);
        }
        AuditEvent event = new AuditEvent(events.size() + 1L, now, sessionId, action);
        events.add(event);
        return event;
    }

    public synchronized int size() {
        return events.size();
    }

    public synchronized List<AuditEvent> trace() {
        return List.copyOf(events);
    }

    public static void main(String[] args) {
        Ejercicio10AuditoriaSeguridad audit = getInstance();
        audit.record("sesion-42", "Cambio de privilegios");
        audit.record("sesion-42", "Transferencia aprobada");
        System.out.println(audit.trace());
    }
}
