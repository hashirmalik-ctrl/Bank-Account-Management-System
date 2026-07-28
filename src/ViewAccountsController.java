import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ViewAccountsController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<BankAccount> accountsTable;

    @FXML
    private TableColumn<BankAccount, String> accountNoColumn;

    @FXML
    private TableColumn<BankAccount, String> ownerColumn;

    @FXML
    private TableColumn<BankAccount, String> typeColumn;

    @FXML
    private TableColumn<BankAccount, String> balanceColumn;

    @FXML
    private TableColumn<BankAccount, String> statusColumn;

    @FXML
    public void initialize() {
        accountNoColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAccountNumber()));
        ownerColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getOwner().getName()));
        typeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAccountType()));
        balanceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%,.2f", data.getValue().getBalance())));
        statusColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        statusColumn.setCellFactory(column -> new javafx.scene.control.TableCell<BankAccount, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                javafx.scene.control.Label badge = new javafx.scene.control.Label(status);
                badge.getStyleClass().add("Active".equalsIgnoreCase(status) ? "badge-active" : "badge-closed");
                setGraphic(badge);
                setText(null);
            }
        });

        refreshTable(Bank.getInstance().getAccounts());
    }

    private void refreshTable(List<BankAccount> accounts) {
        ObservableList<BankAccount> data = FXCollections.observableArrayList(accounts);
        accountsTable.setItems(data);
    }

    @FXML
    public void handleSearch() {
        String term = searchField.getText().trim().toLowerCase();
        if (term.isEmpty()) {
            refreshTable(Bank.getInstance().getAccounts());
            return;
        }
        List<BankAccount> filtered = Bank.getInstance().getAccounts().stream()
                .filter(acc -> acc.getAccountNumber().toLowerCase().contains(term)
                        || acc.getOwner().getName().toLowerCase().contains(term))
                .collect(Collectors.toList());
        refreshTable(filtered);
    }

    @FXML
    public void handleSortByBalance() {
        Bank.getInstance().sortAccountsByBalance();
        refreshTable(Bank.getInstance().getAccounts());
    }

    @FXML
    public void handleAddInterest() {
        BankAccount selected = accountsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showError("Please select an account first.");
            return;
        }
        if (!(selected instanceof SavingsAccount)) {
            AlertHelper.showError("Interest can only be added to savings accounts.");
            return;
        }
        try {
            double interest = ((SavingsAccount) selected).addInterest();
            AlertHelper.showInfo(String.format("Added %.2f interest. New balance: %.2f", interest, selected.getBalance()));
            refreshTable(Bank.getInstance().getAccounts());
        } catch (InvalidAmountException e) {
            AlertHelper.showError(e.getMessage());
        }
    }

    @FXML
    public void handleViewHistory() {
        BankAccount selected = accountsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showError("Please select an account first.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionHistory.fxml"));
            Parent root = loader.load();

            TransactionHistoryController controller = loader.getController();
            controller.setAccount(selected);

            Stage stage = new Stage();
            stage.setTitle("Transaction History - " + selected.getAccountNumber());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.showAndWait();
        } catch (Exception e) {
            AlertHelper.showError("Could not open transaction history: " + e.getMessage());
        }
    }
}
