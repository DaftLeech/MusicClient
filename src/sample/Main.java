package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setStage(this);
        primaryStage.setTitle("Music World | Login");
        primaryStage.getIcons().add(new Image("/resources/App.png"));
        primaryStage.setScene(new Scene(root, 368, 184));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void postLogin(String username){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/gui.fxml"));
            stage.setTitle("Music World | "+username);
            stage.setScene(new Scene(root,650,800));
        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
