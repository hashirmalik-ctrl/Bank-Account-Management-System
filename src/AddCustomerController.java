import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCustomerController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    public void handleSaveCustomer() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            AlertHelper.showError("Please fill in all fields.");
            return;
        }

        try {
            Customer customer = new Customer(id, name, email);
            Bank.getInstance().addCustomer(customer);
            AlertHelper.showInfo("Customer added successfully!");

            idField.clear();
            nameField.clear();
            emailField.clear();
        } catch (DuplicateEntryException e) {
            AlertHelper.showError(e.getMessage());
        }
    }
}
