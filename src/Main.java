import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load any previously saved data before the UI opens.
        Bank.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 720, 480);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Bank Account Management System");
        stage.setScene(scene);
        stage.show();

        // Persist data whenever the application is closed.
        stage.setOnCloseRequest(event -> Bank.getInstance().saveToFile());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
