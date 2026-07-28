/**
 * Thrown when a withdrawal / transfer / repayment is attempted for more
 * than the available balance (including any overdraft limit).
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
