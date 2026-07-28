import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WithdrawController {

    @FXML
    private TextField accountNoField;

    @FXML
    private TextField amountField;

    @FXML
    public void handleWithdraw() {

        String accNo = accountNoField.getText().trim();

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            AlertHelper.showError("Please enter a valid amount.");
            return;
        }

        try {
            BankAccount acc = Bank.getInstance().findAccount(accNo);
            acc.withdraw(amount);
            AlertHelper.showInfo(String.format("Withdrew %.2f. New balance: %.2f", amount, acc.getBalance()));

            accountNoField.clear();
            amountField.clear();
        } catch (AccountNotFoundException | InvalidAmountException | InsufficientBalanceException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
