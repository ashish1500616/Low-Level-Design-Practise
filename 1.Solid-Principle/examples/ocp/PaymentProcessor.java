// Bad example - violates OCP
class PaymentProcessor {
    public void processPayment(String type) {
        if (type.equals("CREDIT")) {
            // process credit payment
        } else if (type.equals("DEBIT")) {
            // process debit payment
        }
        // Adding new payment type requires MODIFYING this class
    }
}

// Good example - follows OCP
interface PaymentMethod {
    void processPayment();
}

class CreditPayment implements PaymentMethod {
    public void processPayment() {
        // process credit payment
    }
}

class DebitPayment implements PaymentMethod {
    public void processPayment() {
        // process debit payment
    }
}

// New payment types can be added without modifying existing code
class CryptoPayment implements PaymentMethod {
    public void processPayment() {
        // process crypto payment
    }
}