import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OpenAccountController {

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField accountNoField;

    @FXML
    private TextField balanceField;

    @FXML
    private ChoiceBox<String> accountTypeBox;

    @FXML
    private TextField extraField;

    @FXML
    private Label extraFieldLabel;

    @FXML
    public void initialize() {
        accountTypeBox.getItems().addAll("Savings", "Current");
        accountTypeBox.setValue("Savings");
        updateExtraFieldLabel();
        accountTypeBox.setOnAction(e -> updateExtraFieldLabel());
    }

    private void updateExtraFieldLabel() {
        if ("Savings".equals(accountTypeBox.getValue())) {
            extraFieldLabel.setText("Interest Rate (%)");
        } else {
            extraFieldLabel.setText("Overdraft Limit");
        }
    }

    @FXML
    public void handleCreateAccount() {

        String customerId = customerIdField.getText().trim();
        String accNo = accountNoField.getText().trim();
        String type = accountTypeBox.getValue();

        if (customerId.isEmpty() || accNo.isEmpty()) {
            AlertHelper.showError("Please fill in all fields.");
            return;
        }

        double balance;
        double extraValue;
        try {
            balance = Double.parseDouble(balanceField.getText().trim());
            extraValue = Double.parseDouble(extraField.getText().trim());
        } catch (NumberFormatException e) {
            AlertHelper.showError("Balance and " + extraFieldLabel.getText() + " must be valid numbers.");
            return;
        }

        try {
            Customer customer = Bank.getInstance().findCustomer(customerId);

            BankAccount account;
            if ("Savings".equals(type)) {
                account = new SavingsAccount(accNo, customer, balance, extraValue);
            } else {
                account = new CurrentAccount(accNo, customer, balance, extraValue);
            }

            Bank.getInstance().openAccount(customer, account);
            AlertHelper.showInfo("Account created successfully!");

            customerIdField.clear();
            accountNoField.clear();
            balanceField.clear();
            extraField.clear();
        } catch (AccountNotFoundException | DuplicateEntryException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
