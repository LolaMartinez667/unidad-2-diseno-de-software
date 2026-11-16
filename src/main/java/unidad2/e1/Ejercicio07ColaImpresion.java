package unidad2.e1;

import java.util.ArrayDeque;
import java.util.Queue;

public final class Ejercicio07ColaImpresion {
    public record PrintJob(String fileName, String author) {}

    private final Queue<PrintJob> queue = new ArrayDeque<>();

    private Ejercicio07ColaImpresion() {}

    private static class Holder {
        private static final Ejercicio07ColaImpresion INSTANCE = new Ejercicio07ColaImpresion();
    }

    public static Ejercicio07ColaImpresion getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void enqueue(String fileName, String author) {
        if (fileName == null || fileName.isBlank() || author == null || author.isBlank()) {
            throw new IllegalArgumentException("El archivo y el autor son obligatorios");
        }
        queue.add(new PrintJob(fileName, author));
    }

    public synchronized boolean processNext() {
        PrintJob job = queue.poll();
        if (job == null) {
            return false;
        }
        System.out.println("Imprimiendo " + job.fileName() + " (autor: " + job.author() + ")");
        return true;
    }

    public synchronized int pendingJobs() {
        return queue.size();
    }

    public static void main(String[] args) {
        Ejercicio07ColaImpresion printer = getInstance();
        printer.enqueue("informe.pdf", "Luis");
        printer.enqueue("acta.docx", "Sofia");
        while (printer.processNext()) {}
    }
}
