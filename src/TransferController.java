import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TransferController {

    @FXML
    private TextField senderField;

    @FXML
    private TextField receiverField;

    @FXML
    private TextField amountField;

    @FXML
    public void handleTransfer() {

        String senderNo = senderField.getText().trim();
        String receiverNo = receiverField.getText().trim();

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            AlertHelper.showError("Please enter a valid amount.");
            return;
        }

        try {
            Bank bank = Bank.getInstance();
            BankAccount sender = bank.findAccount(senderNo);
            BankAccount receiver = bank.findAccount(receiverNo);

            sender.transfer(receiver, amount);
            AlertHelper.showInfo("Transfer successful!");

            senderField.clear();
            receiverField.clear();
            amountField.clear();
        } catch (AccountNotFoundException | InvalidAmountException | InsufficientBalanceException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
