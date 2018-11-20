package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import general.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable
{

    private Main stage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void setStage(Main stage){
        this.stage = stage;
    }

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton button;

    public LoginController(){

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something…
        System.out.println(userField.getText()+" "+passwordField.getText());
        if(1==1||userField.getText().equals("admin") && passwordField.getText().equals("admin"))
            stage.postLogin(userField.getText());
    }



}
