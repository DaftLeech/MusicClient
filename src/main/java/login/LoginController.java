package login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import general.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
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
        System.out.println(userField.getText()+" "+passwordField.getText());

        try {
            URL url = new URL("http://localhost:18183/User/login");
            Map<String, String> params = new LinkedHashMap<>();
            params.put("userName", userField.getText());
            params.put("password", passwordField.getText());

            StringBuilder postData = new StringBuilder();

            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            System.out.println(url);
            System.out.println("Connecting...");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            System.out.println("Connected.");

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(new InputStreamReader(conn.getInputStream(), "UTF-8"),  User.class);

            System.out.print("UserID:");
            System.out.println(user.getUserID());
            System.out.print("SessionID:");
            System.out.println(user.getSessionID());

            if(user!=null){
                user.setUserName(userField.getText());
                Main.user =user;
                stage.postLogin(Main.user.getUserName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }



}
