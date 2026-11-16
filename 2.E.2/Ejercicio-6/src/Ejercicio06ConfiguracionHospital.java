package unidad2.e2;

import java.time.LocalTime;

public final class Ejercicio06ConfiguracionHospital {
    private String hospitalName = "Hospital General";
    private LocalTime opening = LocalTime.of(8, 0);
    private LocalTime closing = LocalTime.of(18, 0);
    private int appointmentMinutes = 30;

    private Ejercicio06ConfiguracionHospital() {}

    private static class Holder {
        private static final Ejercicio06ConfiguracionHospital INSTANCE =
                new Ejercicio06ConfiguracionHospital();
    }

    public static Ejercicio06ConfiguracionHospital getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized String getHospitalName() { return hospitalName; }

    public synchronized void setHospitalName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        hospitalName = name.trim();
    }

    public synchronized LocalTime getOpening() { return opening; }
    public synchronized LocalTime getClosing() { return closing; }

    public synchronized void setHours(LocalTime opening, LocalTime closing) {
        if (opening == null || closing == null || !closing.isAfter(opening)) {
            throw new IllegalArgumentException("El cierre debe ser posterior a la apertura");
        }
        this.opening = opening;
        this.closing = closing;
    }

    public synchronized int getAppointmentMinutes() { return appointmentMinutes; }

    public synchronized void setAppointmentMinutes(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("La duracion debe ser positiva");
        }
        appointmentMinutes = minutes;
    }

    public static void main(String[] args) {
        Ejercicio06ConfiguracionHospital reception = getInstance();
        Ejercicio06ConfiguracionHospital office = getInstance();
        reception.setHospitalName("Hospital del Sur");
        System.out.println(reception.getHospitalName() + " / " + office.getHospitalName());
        System.out.println("Misma instancia: " + (reception == office));
    }
}
