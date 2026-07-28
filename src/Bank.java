import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central data holder for the whole application, implemented as a singleton
 * so every controller works with the same in-memory bank instead of relying
 * on a public static field. Also handles saving/loading to disk so data
 * survives an application restart.
 */
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SAVE_FILE = "bankdata.ser";

    private static Bank instance;

    private String bankName;
    private List<Customer> customers;
    private List<BankAccount> accounts;
    private List<Loan> loans;
    private List<Report> reports;

    private Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.reports = new ArrayList<>();
    }

    public static synchronized Bank getInstance() {
        if (instance == null) {
            instance = loadFromFile();
            if (instance == null) {
                instance = new Bank("My Bank");
            }
        }
        return instance;
    }

    // ----- Customers -----

    public void addCustomer(Customer customer) throws DuplicateEntryException {
        if (findCustomerOrNull(customer.getCustomerID()) != null) {
            throw new DuplicateEntryException("A customer with ID " + customer.getCustomerID() + " already exists.");
        }
        customers.add(customer);
    }

    public Customer findCustomer(String customerID) throws AccountNotFoundException {
        Customer c = findCustomerOrNull(customerID);
        if (c == null) {
            throw new AccountNotFoundException("No customer found with ID " + customerID);
        }
        return c;
    }

    private Customer findCustomerOrNull(String customerID) {
        for (Customer c : customers) {
            if (c.getCustomerID().equalsIgnoreCase(customerID)) {
                return c;
            }
        }
        return null;
    }

    public void sortCustomers() {
        Collections.sort(customers);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    // ----- Accounts -----

    public void openAccount(Customer customer, BankAccount account) throws DuplicateEntryException {
        if (findAccountOrNull(account.getAccountNumber()) != null) {
            throw new DuplicateEntryException("An account with number " + account.getAccountNumber() + " already exists.");
        }
        accounts.add(account);
        customer.addAccount(account);
    }

    public void closeAccount(String accountNo) throws AccountNotFoundException {
        BankAccount found = findAccountOrNull(accountNo);
        if (found == null) {
            throw new AccountNotFoundException("No account found with number " + accountNo);
        }
        accounts.remove(found);
    }

    public BankAccount findAccount(String accountNo) throws AccountNotFoundException {
        BankAccount acc = findAccountOrNull(accountNo);
        if (acc == null) {
            throw new AccountNotFoundException("No account found with number " + accountNo);
        }
        return acc;
    }

    private BankAccount findAccountOrNull(String accountNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accountNo)) {
                return acc;
            }
        }
        return null;
    }

    public void sortAccountsByBalance() {
        Collections.sort(accounts, new AccountBalanceComparator());
    }

    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public double getTotalDeposits() {
        double total = 0;
        for (BankAccount acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    // ----- Loans -----

    public Loan applyForLoan(Customer borrower, double amount, double interestRate) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Loan amount must be greater than zero.");
        }
        Loan loan = new Loan("L" + System.currentTimeMillis(), borrower, amount, interestRate);
        loans.add(loan);
        return loan;
    }

    public Loan findLoan(String loanID) throws AccountNotFoundException {
        for (Loan l : loans) {
            if (l.getLoanID().equalsIgnoreCase(loanID)) {
                return l;
            }
        }
        throw new AccountNotFoundException("No loan found with ID " + loanID);
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    // ----- Reports -----

    public Report addReport(Report report) {
        reports.add(report);
        return report;
    }

    public List<Report> getReports() {
        return Collections.unmodifiableList(reports);
    }

    public String getBankName() {
        return bankName;
    }

    // ----- Persistence -----

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.err.println("Could not save bank data: " + e.getMessage());
        }
    }

    private static Bank loadFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Bank) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load saved bank data, starting fresh: " + e.getMessage());
            return null;
        }
    }
}
