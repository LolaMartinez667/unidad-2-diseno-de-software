package unidad2.e1;

public final class Ejercicio02GestorConexion {
    private final String connectionId = "CONEXION-1";
    private boolean open = true;

    private Ejercicio02GestorConexion() {
        System.out.println("Conexion fisica simulada abierta: " + connectionId);
    }

    private static class Holder {
        private static final Ejercicio02GestorConexion INSTANCE = new Ejercicio02GestorConexion();
    }

    public static Ejercicio02GestorConexion getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized String query(String sql) {
        ensureOpen();
        return "Consulta ejecutada en " + connectionId + ": " + sql;
    }

    public synchronized int update(String sql) {
        ensureOpen();
        System.out.println("Actualizacion ejecutada en " + connectionId + ": " + sql);
        return 1;
    }

    public synchronized boolean isOpen() {
        return open;
    }

    private void ensureOpen() {
        if (!open) {
            throw new IllegalStateException("La conexion esta cerrada");
        }
    }

    public static void main(String[] args) {
        Ejercicio02GestorConexion gestor = getInstance();
        System.out.println(gestor.query("SELECT * FROM libros"));
        gestor.update("UPDATE libros SET disponibles = 3");
    }
}
