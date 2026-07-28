import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DepositController {

    @FXML
    private TextField accountNoField;

    @FXML
    private TextField amountField;

    @FXML
    public void handleDeposit() {

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
            acc.deposit(amount);
            AlertHelper.showInfo(String.format("Deposited %.2f. New balance: %.2f", amount, acc.getBalance()));

            accountNoField.clear();
            amountField.clear();
        } catch (AccountNotFoundException | InvalidAmountException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
