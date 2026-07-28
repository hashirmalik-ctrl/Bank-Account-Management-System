import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Small helper so every controller doesn't have to repeat the same
 * Alert boilerplate. This is also what fixes the old behaviour of
 * printing success/error messages to the console where the user
 * running the compiled app could never see them.
 */
public final class AlertHelper {

    private AlertHelper() {
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("Something went wrong");
        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }
}
