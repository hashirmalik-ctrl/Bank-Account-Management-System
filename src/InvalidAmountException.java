/**
 * Thrown when a monetary amount supplied to the system is invalid
 * (zero, negative, or otherwise not usable for the requested operation).
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
