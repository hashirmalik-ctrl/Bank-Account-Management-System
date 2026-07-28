import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label totalCustomersLabel;

    @FXML
    private Label totalAccountsLabel;

    @FXML
    private Label totalDepositsLabel;

    @FXML
    public void initialize() {
        refreshStats();
    }

    private void refreshStats() {
        Bank bank = Bank.getInstance();
        if (totalCustomersLabel != null) {
            totalCustomersLabel.setText(String.valueOf(bank.getCustomers().size()));
        }
        if (totalAccountsLabel != null) {
            totalAccountsLabel.setText(String.valueOf(bank.getAccounts().size()));
        }
        if (totalDepositsLabel != null) {
            totalDepositsLabel.setText("$" + String.format("%,.2f", bank.getTotalDeposits()));
        }
    }

    private void openWindow(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.showAndWait();

            // Save after every dialog closes and refresh the summary numbers.
            Bank.getInstance().saveToFile();
            refreshStats();
        } catch (Exception e) {
            AlertHelper.showError("Could not open this screen: " + e.getMessage());
        }
    }

    @FXML
    public void handleAddCustomer() {
        openWindow("AddCustomer.fxml", "Add Customer");
    }

    @FXML
    public void handleOpenAccount() {
        openWindow("OpenAccount.fxml", "Open Account");
    }

    @FXML
    public void handleDeposit() {
        openWindow("Deposit.fxml", "Deposit");
    }

    @FXML
    public void handleWithdraw() {
        openWindow("Withdraw.fxml", "Withdraw");
    }

    @FXML
    public void handleTransfer() {
        openWindow("Transfer.fxml", "Transfer");
    }

    @FXML
    public void handleViewAccounts() {
        openWindow("ViewAccounts.fxml", "View Accounts");
    }

    @FXML
    public void handleCloseAccount() {
        openWindow("CloseAccount.fxml", "Close Account");
    }

    @FXML
    public void handleLoans() {
        openWindow("Loans.fxml", "Loans");
    }

    @FXML
    public void handleReports() {
        openWindow("Reports.fxml", "Reports");
    }
}
