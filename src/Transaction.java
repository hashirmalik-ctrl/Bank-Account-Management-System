import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String transactionID;
    private final String type;
    private final double amount;
    private final Date date;
    private final String accountNumber;

    public Transaction(String transactionID, String type, double amount, BankAccount account) {
        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.accountNumber = account.getAccountNumber();
        this.date = new Date();
    }

    public String getReceipt() {
        return "Transaction ID : " + transactionID
                + "\nType           : " + type
                + "\nAmount         : " + String.format("%.2f", amount)
                + "\nDate           : " + FORMAT.format(date)
                + "\nAccount No     : " + accountNumber;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return FORMAT.format(date) + " | " + type + " | " + String.format("%.2f", amount);
    }
}
