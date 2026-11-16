package unidad2.e1;

public final class Ejercicio08InyeccionDependencias {
    public interface PaymentGateway {
        boolean pay(String account, double amount);
    }

    public static final class GatewayUnico implements PaymentGateway {
        private GatewayUnico() {}

        private static class Holder {
            private static final GatewayUnico INSTANCE = new GatewayUnico();
        }

        public static GatewayUnico getInstance() {
            return Holder.INSTANCE;
        }

        @Override
        public boolean pay(String account, double amount) {
            if (account == null || account.isBlank() || amount <= 0) {
                throw new IllegalArgumentException("Cuenta y monto valido son obligatorios");
            }
            System.out.printf("Pago de %.2f procesado para %s%n", amount, account);
            return true;
        }
    }

    public static final class CheckoutService {
        private final PaymentGateway gateway;

        public CheckoutService(PaymentGateway gateway) {
            if (gateway == null) {
                throw new IllegalArgumentException("La pasarela es obligatoria");
            }
            this.gateway = gateway;
        }

        public boolean checkout(String account, double amount) {
            return gateway.pay(account, amount);
        }
    }

    public static void main(String[] args) {
        CheckoutService checkout = new CheckoutService(GatewayUnico.getInstance());
        System.out.println("Pago exitoso: " + checkout.checkout("cliente-1", 1250.00));
    }
}
