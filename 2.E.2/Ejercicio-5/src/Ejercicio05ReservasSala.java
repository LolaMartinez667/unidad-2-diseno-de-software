package unidad2.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Ejercicio05ReservasSala {
    private static final int CAPACITY = 10;
    private final Set<String> members = new HashSet<>();

    private Ejercicio05ReservasSala() {}

    private static class Holder {
        private static final Ejercicio05ReservasSala INSTANCE = new Ejercicio05ReservasSala();
    }

    public static Ejercicio05ReservasSala getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean reserve(String memberId) {
        validate(memberId);
        if (members.contains(memberId) || members.size() >= CAPACITY) {
            return false;
        }
        return members.add(memberId);
    }

    public synchronized boolean cancel(String memberId) {
        validate(memberId);
        return members.remove(memberId);
    }

    public synchronized Set<String> activeReservations() {
        return Set.copyOf(members);
    }

    public synchronized int availablePlaces() {
        return CAPACITY - members.size();
    }

    private static void validate(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("El identificador del socio es obligatorio");
        }
    }

    public static void main(String[] args) throws Exception {
        Ejercicio05ReservasSala manager = getInstance();
        int requests = 30;
        ExecutorService pool = Executors.newFixedThreadPool(requests);
        CountDownLatch ready = new CountDownLatch(requests);
        CountDownLatch start = new CountDownLatch(1);
        Future<?>[] results = new Future<?>[requests];
        for (int i = 0; i < requests; i++) {
            String member = "SOCIO-" + i;
            results[i] = pool.submit(() -> {
                ready.countDown();
                start.await();
                manager.reserve(member);
                return null;
            });
        }
        ready.await();
        start.countDown();
        for (Future<?> result : results) {
            result.get();
        }
        pool.shutdown();
        if (manager.activeReservations().size() != CAPACITY || manager.availablePlaces() < 0) {
            throw new AssertionError("Se excedio la capacidad de la sala");
        }
        System.out.println("Reservas activas: " + manager.activeReservations().size());
        System.out.println("Lugares disponibles: " + manager.availablePlaces());
        System.out.println("Cancelar inexistente: " + manager.cancel("NO-EXISTE"));
    }
}
