import java.util.Comparator;

/**
 * Sorts accounts from lowest to highest balance.
 */
public class AccountBalanceComparator implements Comparator<BankAccount> {
    @Override
    public int compare(BankAccount a1, BankAccount a2) {
        return Double.compare(a1.getBalance(), a2.getBalance());
    }
}
