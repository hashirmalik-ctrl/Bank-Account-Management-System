public class SavingsAccount extends BankAccount {

    private static final long serialVersionUID = 1L;

    private final double interestRate;

    public SavingsAccount(String accountNumber, Customer owner, double balance, double interestRate) {
        super(accountNumber, owner, balance);
        this.interestRate = interestRate;
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    public double addInterest() throws InvalidAmountException {
        double interest = calculateInterest();
        deposit(interest);
        return interest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() + "\nInterest Rate  : " + interestRate + "%";
    }
}
