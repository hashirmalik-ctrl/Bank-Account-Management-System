/**
 * Thrown when trying to create a customer or account that already
 * exists (duplicate ID / account number).
 */
public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String message) {
        super(message);
    }
}
