package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
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
            stage.setScene(new Scene(root,1200,800));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int width = toolkit.getScreenSize().width;
            int height = toolkit.getScreenSize().height;
            stage.setX((width-1200)/2);
            stage.setY((height-800)/2);
        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
