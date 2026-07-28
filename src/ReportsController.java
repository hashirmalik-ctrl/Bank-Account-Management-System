import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ReportsController {

    @FXML
    private TextArea reportArea;

    @FXML
    public void handleGenerateSummaryReport() {
        Bank bank = Bank.getInstance();

        StringBuilder data = new StringBuilder();
        data.append("Total Customers : ").append(bank.getCustomers().size()).append("\n");
        data.append("Total Accounts  : ").append(bank.getAccounts().size()).append("\n");
        data.append("Total Deposits  : ").append(String.format("%.2f", bank.getTotalDeposits())).append("\n");
        data.append("Active Loans    : ").append(bank.getLoans().size()).append("\n\n");

        data.append("Accounts:\n");
        for (BankAccount acc : bank.getAccounts()) {
            data.append(" - ").append(acc).append("\n");
        }

        BankStaff staff = new BankStaff("S001", "System", "Automated");
        Report report = staff.generateReport("Bank Summary", data.toString());
        bank.addReport(report);

        reportArea.setText(report.getReportText());
    }
}
