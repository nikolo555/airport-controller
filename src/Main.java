import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Medialab Airport");

        Parent homeRoot = FXMLLoader.load(getClass().getResource("resources/homepage.fxml"));
        Scene homepage = new Scene(homeRoot);

        //Parent sampleRoot = FXMLLoader.load(getClass().getResource("fxml/gateView.fxml"));
        //Scene sample = new Scene(sampleRoot);

        primaryStage.setScene(homepage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
