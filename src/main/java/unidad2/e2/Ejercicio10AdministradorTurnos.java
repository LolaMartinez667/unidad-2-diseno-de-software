package unidad2.e2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Ejercicio10AdministradorTurnos {
    public record Slot(String professional, LocalDate date, LocalTime time) {}
    public record Appointment(String patient, Slot slot) {}

    private final Set<Slot> available = new HashSet<>();
    private final Map<Slot, Appointment> booked = new HashMap<>();

    private Ejercicio10AdministradorTurnos() {}

    private static class Holder {
        private static final Ejercicio10AdministradorTurnos INSTANCE =
                new Ejercicio10AdministradorTurnos();
    }

    public static Ejercicio10AdministradorTurnos getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void addAvailableSlot(Slot slot) {
        if (slot == null || slot.professional() == null || slot.professional().isBlank()
                || slot.date() == null || slot.time() == null) {
            throw new IllegalArgumentException("Horario invalido");
        }
        available.add(slot);
    }

    public synchronized boolean book(String patient, Slot slot) {
        if (patient == null || patient.isBlank() || slot == null) {
            throw new IllegalArgumentException("Paciente y horario son obligatorios");
        }
        if (!available.contains(slot) || booked.containsKey(slot)) {
            return false;
        }
        booked.put(slot, new Appointment(patient, slot));
        return true;
    }

    public synchronized boolean cancel(Slot slot) {
        return booked.remove(slot) != null;
    }

    public synchronized Map<Slot, Appointment> agenda() {
        return Map.copyOf(booked);
    }

    public static void main(String[] args) throws Exception {
        Ejercicio10AdministradorTurnos admin = getInstance();
        Slot slot = new Slot("Dra. Rivera", LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        admin.addAvailableSlot(slot);

        int patients = 20;
        ExecutorService pool = Executors.newFixedThreadPool(patients);
        CountDownLatch ready = new CountDownLatch(patients);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<Boolean>> results = new ArrayList<>(patients);
        for (int i = 0; i < patients; i++) {
            String patient = "PACIENTE-" + i;
            results.add(pool.submit(() -> {
                ready.countDown();
                start.await();
                return admin.book(patient, slot);
            }));
        }
        ready.await();
        start.countDown();
        int successes = 0;
        for (Future<Boolean> result : results) {
            if (result.get()) {
                successes++;
            }
        }
        pool.shutdown();
        if (successes != 1 || admin.agenda().size() != 1) {
            throw new AssertionError("El horario debe asignarse una sola vez");
        }
        System.out.println("Reservas exitosas: " + successes);
        System.out.println("Agenda desde recepcion/autoservicio: " + admin.agenda());
        System.out.println("Cancelar inexistente: "
                + admin.cancel(new Slot("Dr. Nadie", slot.date(), slot.time())));
    }
}
