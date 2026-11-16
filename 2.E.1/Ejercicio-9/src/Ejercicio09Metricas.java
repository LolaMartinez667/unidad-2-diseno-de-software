package unidad2.e1;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public final class Ejercicio09Metricas {
    private static final int MAX_METRICS = 1_000;

    private static final class Metric {
        private final LongAdder count = new LongAdder();
        private final LongAdder latencyNanos = new LongAdder();
    }

    private final ConcurrentMap<String, Metric> metrics = new ConcurrentHashMap<>();
    private final Object creationLock = new Object();

    private Ejercicio09Metricas() {}

    private static class Holder {
        private static final Ejercicio09Metricas INSTANCE = new Ejercicio09Metricas();
    }

    public static Ejercicio09Metricas getInstance() {
        return Holder.INSTANCE;
    }

    public void increment(String name) {
        metric(name).count.increment();
    }

    public void recordLatency(String name, long nanos) {
        if (nanos < 0) {
            throw new IllegalArgumentException("La latencia no puede ser negativa");
        }
        Metric metric = metric(name);
        metric.count.increment();
        metric.latencyNanos.add(nanos);
    }

    private Metric metric(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la metrica es obligatorio");
        }
        Metric metric = metrics.get(name);
        if (metric != null) {
            return metric;
        }
        synchronized (creationLock) {
            metric = metrics.get(name);
            if (metric == null) {
                if (metrics.size() >= MAX_METRICS) {
                    throw new IllegalStateException("Se alcanzo el limite de metricas");
                }
                metric = new Metric();
                metrics.put(name, metric);
            }
            return metric;
        }
    }

    public Map<String, String> export() {
        Map<String, String> snapshot = new TreeMap<>();
        metrics.forEach((name, metric) -> snapshot.put(name,
                "count=" + metric.count.sum() + ", latencyNanos=" + metric.latencyNanos.sum()));
        return Map.copyOf(snapshot);
    }

    public static void main(String[] args) {
        Ejercicio09Metricas metrics = getInstance();
        metrics.increment("http.requests");
        metrics.recordLatency("http.latency", 125_000);
        System.out.println(metrics.export());
    }
}
