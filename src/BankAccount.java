import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for all account types. Fields are private (real encapsulation) -
 * subclasses and controllers must go through the public API instead of
 * poking at the balance directly.
 */
public abstract class BankAccount implements Transferable, Serializable {

    private static final long serialVersionUID = 1L;

    private final String accountNumber;
    private double balance;
    private String status;
    private final Customer owner;
    private final List<Transaction> transactions;

    public BankAccount(String accountNumber, Customer owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.status = "Active";
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        recordTransaction("Deposit", amount);
    }

    /**
     * Base withdrawal rule: cannot withdraw more than the current balance.
     * CurrentAccount overrides this to allow an overdraft.
     */
    public boolean withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal.");
        }
        balance -= amount;
        recordTransaction("Withdraw", amount);
        return true;
    }

    @Override
    public boolean transfer(BankAccount receiver, double amount)
            throws InvalidAmountException, InsufficientBalanceException {
        if (receiver == null) {
            throw new InvalidAmountException("Receiving account does not exist.");
        }
        if (receiver == this) {
            throw new InvalidAmountException("Cannot transfer to the same account.");
        }
        withdraw(amount);
        receiver.deposit(amount);
        return true;
    }

    /** Lets a subclass adjust the balance directly (e.g. an overdraft-aware withdraw) while keeping the field itself private. */
    protected void applyBalanceChange(double delta) {
        balance += delta;
    }

    protected void recordTransaction(String type, double amount) {
        Transaction t = new Transaction(generateTransactionId(), type, amount, this);
        transactions.add(t);
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + transactions.size();
    }

    /** Interest is zero by default; interest-bearing accounts override this. */
    public double calculateInterest() {
        return 0;
    }

    public abstract String getAccountType();

    public String getAccountDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account Number : ").append(accountNumber).append("\n");
        sb.append("Owner Name     : ").append(owner.getName()).append("\n");
        sb.append("Balance        : ").append(String.format("%.2f", balance)).append("\n");
        sb.append("Status         : ").append(status).append("\n");
        sb.append("Account Type   : ").append(getAccountType());
        return sb.toString();
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getAccountType() + " " + accountNumber + " (" + owner.getName() + ") - Balance: " + String.format("%.2f", balance);
    }
}
