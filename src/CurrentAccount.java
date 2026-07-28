public class CurrentAccount extends BankAccount {

    private static final long serialVersionUID = 1L;

    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, Customer owner, double balance, double overdraftLimit) {
        super(accountNumber, owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Current accounts may go negative up to the overdraft limit,
     * so this overrides the stricter rule in BankAccount.
     */
    @Override
    public boolean withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new InsufficientBalanceException("Overdraft limit exceeded.");
        }
        applyBalanceChange(-amount);
        recordTransaction("Withdraw", amount);
        return true;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() + "\nOverdraft Limit: " + overdraftLimit;
    }
}
