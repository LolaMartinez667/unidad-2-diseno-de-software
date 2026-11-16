package unidad2.e2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class Ejercicio09ColaImpresion {
    public record Document(String id, String name, String sector, int pages) {}

    private final Queue<Document> queue = new ArrayDeque<>();
    private final Set<String> submittedIds = new HashSet<>();
    private final List<Document> processed = new ArrayList<>();

    private Ejercicio09ColaImpresion() {}

    private static class Holder {
        private static final Ejercicio09ColaImpresion INSTANCE = new Ejercicio09ColaImpresion();
    }

    public static Ejercicio09ColaImpresion getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void enqueue(Document document) {
        if (document == null || document.id() == null || document.id().isBlank()
                || document.name() == null || document.name().isBlank()
                || document.sector() == null || document.sector().isBlank()
                || document.pages() <= 0) {
            throw new IllegalArgumentException("Documento invalido");
        }
        if (!submittedIds.add(document.id())) {
            throw new IllegalArgumentException("Identificador duplicado: " + document.id());
        }
        queue.add(document);
    }

    public synchronized List<Document> processAll() {
        while (!queue.isEmpty()) {
            Document document = queue.remove();
            System.out.println("Imprimiendo " + document.name() + " (" + document.pages() + " paginas)");
            processed.add(document);
        }
        return List.copyOf(processed);
    }

    public synchronized List<Document> report() {
        return List.copyOf(processed);
    }

    public static void main(String[] args) throws InterruptedException {
        Ejercicio09ColaImpresion manager = getInstance();
        int threadCount = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<?>> requests = new ArrayList<>();
        for (int thread = 0; thread < threadCount; thread++) {
            int threadId = thread;
            requests.add(pool.submit(() -> {
                ready.countDown();
                start.await();
                for (int doc = 0; doc < 3; doc++) {
                    int id = threadId * 3 + doc;
                    manager.enqueue(new Document("DOC-" + id, "documento-" + id + ".pdf",
                            threadId % 3 == 0 ? "Recepcion" : threadId % 3 == 1 ? "Facturacion" : "Archivo", 2));
                }
                return null;
            }));
        }
        ready.await();
        start.countDown();
        pool.shutdown();
        if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Las solicitudes no finalizaron a tiempo");
        }
        for (Future<?> request : requests) {
            try {
                request.get();
            } catch (java.util.concurrent.ExecutionException exception) {
                throw new IllegalStateException("Fallo al encolar un documento", exception.getCause());
            }
        }
        List<Document> report = manager.processAll();
        if (report.size() != 30 || new HashSet<>(report.stream().map(Document::id).toList()).size() != 30) {
            throw new AssertionError("Se perdieron o duplicaron documentos");
        }
        System.out.println("Documentos procesados exactamente una vez: " + report.size());
    }
}
