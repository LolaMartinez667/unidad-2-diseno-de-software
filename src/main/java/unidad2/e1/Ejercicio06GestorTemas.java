package unidad2.e1;

public final class Ejercicio06GestorTemas {
    public enum Theme { CLARO, OSCURO }

    private volatile Theme activeTheme = Theme.CLARO;

    private Ejercicio06GestorTemas() {}

    private static class Holder {
        private static final Ejercicio06GestorTemas INSTANCE = new Ejercicio06GestorTemas();
    }

    public static Ejercicio06GestorTemas getInstance() {
        return Holder.INSTANCE;
    }

    public Theme getTheme() {
        return activeTheme;
    }

    public void setTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("El tema no puede ser null");
        }
        activeTheme = theme;
    }

    public static void main(String[] args) {
        Ejercicio06GestorTemas viewA = getInstance();
        Ejercicio06GestorTemas viewB = getInstance();
        viewA.setTheme(Theme.OSCURO);
        System.out.println("Vista B: " + viewB.getTheme());
    }
}
