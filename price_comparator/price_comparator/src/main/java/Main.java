import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by RUSLAN on 03.05.2016.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("scenes/mainScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Программа для сравнение цен");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
