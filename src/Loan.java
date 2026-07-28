import java.io.Serializable;

public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String loanID;
    private final Customer borrower;
    private final double amount;
    private final double interestRate;
    private double remainingBalance;

    public Loan(String loanID, Customer borrower, double amount, double interestRate) {
        this.loanID = loanID;
        this.borrower = borrower;
        this.amount = amount;
        this.interestRate = interestRate;
        this.remainingBalance = amount + (amount * interestRate / 100);
    }

    public double calculateMonthlyInstallment() {
        double totalAmount = amount + (amount * interestRate / 100);
        return totalAmount / 12;
    }

    public void makeRepayment(double payment) throws InvalidAmountException, InsufficientBalanceException {
        if (payment <= 0) {
            throw new InvalidAmountException("Repayment amount must be greater than zero.");
        }
        if (payment > remainingBalance) {
            throw new InsufficientBalanceException("Payment exceeds the remaining loan balance.");
        }
        remainingBalance -= payment;
    }

    public boolean isPaidOff() {
        return remainingBalance <= 0.0001;
    }

    /** Principal + interest — the actual total the borrower must repay, not just the principal. */
    public double getTotalPayable() {
        return amount + (amount * interestRate / 100);
    }

    public String getStatus() {
        return isPaidOff() ? "Paid Off" : "Active";
    }

    public String getLoanDetails() {
        return "Loan ID           : " + loanID
                + "\nBorrower          : " + borrower.getName()
                + "\nPrincipal Amount  : " + String.format("%.2f", amount)
                + "\nInterest Rate     : " + interestRate + "%"
                + "\nTotal Payable     : " + String.format("%.2f", getTotalPayable())
                + "  (principal + interest)"
                + "\nMonthly Installment: " + String.format("%.2f", calculateMonthlyInstallment())
                + "\nRemaining Balance : " + String.format("%.2f", remainingBalance)
                + "\nStatus            : " + getStatus();
    }

    public String getLoanID() {
        return loanID;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public double getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    @Override
    public String toString() {
        return loanID + " - " + borrower.getName() + " - Remaining: " + String.format("%.2f", remainingBalance)
                + " - " + getStatus();
    }
}
