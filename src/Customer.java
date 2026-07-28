import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer implements Comparable<Customer>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String customerID;
    private final String name;
    private String email;
    private final List<BankAccount> accounts;

    public Customer(String customerID, String name, String email) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public double getAccountBalance(String accountNo) throws AccountNotFoundException {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accountNo)) {
                return acc.getBalance();
            }
        }
        throw new AccountNotFoundException("Account " + accountNo + " does not belong to this customer.");
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public String getCustomerDetails() {
        return "Customer ID : " + customerID + "\nName        : " + name + "\nEmail       : " + email;
    }

    @Override
    public int compareTo(Customer other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return customerID + " - " + name + " (" + email + ")";
    }
}
