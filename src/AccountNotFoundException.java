/**
 * Thrown when an account number does not correspond to any account
 * held by the bank.
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
