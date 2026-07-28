import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CloseAccountController {

    @FXML
    private TextField accountNoField;

    @FXML
    public void handleCloseAccount() {
        String accNo = accountNoField.getText().trim();

        if (accNo.isEmpty()) {
            AlertHelper.showError("Please enter an account number.");
            return;
        }

        if (!AlertHelper.confirm("Are you sure you want to close account " + accNo + "?")) {
            return;
        }

        try {
            Bank.getInstance().closeAccount(accNo);
            AlertHelper.showInfo("Account closed successfully.");
            accountNoField.clear();
        } catch (AccountNotFoundException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
