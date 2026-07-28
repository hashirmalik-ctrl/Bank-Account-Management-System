import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LoansController {

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField interestRateField;

    @FXML
    private TextField loanIdField;

    @FXML
    private TextField repaymentField;

    @FXML
    private TableView<Loan> loansTable;

    @FXML
    private TableColumn<Loan, String> loanIdColumn;

    @FXML
    private TableColumn<Loan, String> borrowerColumn;

    @FXML
    private TableColumn<Loan, String> principalColumn;

    @FXML
    private TableColumn<Loan, String> totalPayableColumn;

    @FXML
    private TableColumn<Loan, String> remainingColumn;

    @FXML
    private TableColumn<Loan, String> statusColumn;

    @FXML
    public void initialize() {
        loanIdColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getLoanID()));
        borrowerColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getBorrower().getName()));
        principalColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%,.2f", d.getValue().getAmount())));
        totalPayableColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%,.2f", d.getValue().getTotalPayable())));
        remainingColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%,.2f", d.getValue().getRemainingBalance())));
        statusColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getStatus()));
        statusColumn.setCellFactory(column -> new TableCell<Loan, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                Label badge = new Label(status);
                badge.getStyleClass().add("Active".equalsIgnoreCase(status) ? "badge-active" : "badge-paid");
                setGraphic(badge);
                setText(null);
            }
        });

        refreshLoans();
    }

    private void refreshLoans() {
        ObservableList<Loan> data = FXCollections.observableArrayList(Bank.getInstance().getLoans());
        loansTable.setItems(data);
    }

    @FXML
    public void handleApplyLoan() {
        String customerId = customerIdField.getText().trim();

        double amount;
        double rate;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
            rate = Double.parseDouble(interestRateField.getText().trim());
        } catch (NumberFormatException e) {
            AlertHelper.showError("Please enter valid numbers for amount and interest rate.");
            return;
        }

        try {
            Customer customer = Bank.getInstance().findCustomer(customerId);
            Loan loan = Bank.getInstance().applyForLoan(customer, amount, rate);
            AlertHelper.showInfo("Loan approved!\n\n" + loan.getLoanDetails());

            customerIdField.clear();
            amountField.clear();
            interestRateField.clear();
            refreshLoans();
        } catch (AccountNotFoundException | InvalidAmountException e) {
            AlertHelper.showError(e.getMessage());
        }
    }

    @FXML
    public void handleRepayment() {
        String loanId = loanIdField.getText().trim();

        double payment;
        try {
            payment = Double.parseDouble(repaymentField.getText().trim());
        } catch (NumberFormatException e) {
            AlertHelper.showError("Please enter a valid repayment amount.");
            return;
        }

        try {
            Loan loan = Bank.getInstance().findLoan(loanId);
            loan.makeRepayment(payment);
            AlertHelper.showInfo("Repayment successful!\n\n" + loan.getLoanDetails());

            loanIdField.clear();
            repaymentField.clear();
            refreshLoans();
        } catch (AccountNotFoundException | InvalidAmountException | InsufficientBalanceException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
