import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TransactionHistoryController {

    @FXML
    private Label accountLabel;

    @FXML
    private ListView<String> transactionsList;

    public void setAccount(BankAccount account) {
        accountLabel.setText("Account: " + account.getAccountNumber() + " (" + account.getOwner().getName() + ")");

        if (account.getTransactions().isEmpty()) {
            transactionsList.setItems(FXCollections.observableArrayList("No transactions yet."));
            return;
        }

        transactionsList.setItems(FXCollections.observableArrayList(
                account.getTransactions().stream().map(Transaction::toString).toList()
        ));
    }
}
