package unidad2.e1;

public final class Ejercicio05ControladorHardware {
    private boolean available = true;
    private String status = "LISTO";

    private Ejercicio05ControladorHardware() {}

    private static class Holder {
        private static final Ejercicio05ControladorHardware INSTANCE = new Ejercicio05ControladorHardware();
    }

    public static Ejercicio05ControladorHardware getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
        if (!available) {
            status = "NO DISPONIBLE";
        } else if (status.equals("NO DISPONIBLE")) {
            status = "LISTO";
        }
    }

    public synchronized void sendCommand(String command) {
        if (!available) {
            throw new IllegalStateException("El dispositivo no esta disponible");
        }
        if (command == null || command.isBlank()) {
            throw new IllegalArgumentException("El comando no puede estar vacio");
        }
        status = "EJECUTANDO: " + command;
        System.out.println(status);
        status = "LISTO";
    }

    public synchronized String readStatus() {
        return status;
    }

    public static void main(String[] args) {
        Ejercicio05ControladorHardware controller = getInstance();
        System.out.println("Disponible: " + controller.isAvailable());
        controller.sendCommand("MOVER_BRAZO 10");
        controller.setAvailable(false);
        System.out.println("Disponible luego de simular desconexion: " + controller.isAvailable());
        System.out.println(controller.readStatus());
    }
}
