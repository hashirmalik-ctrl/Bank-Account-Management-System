public interface Transferable {
    boolean transfer(BankAccount receiver, double amount)
            throws InvalidAmountException, InsufficientBalanceException;
}
