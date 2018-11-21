package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import general.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
        // Button was clicked, do something…
        System.out.println(userField.getText()+" "+passwordField.getText());

        try {
            URL url = new URL("http://g.php"); // URL to your application
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("value", 5); // All parameters, also easy
            params.put("id", 17);

            StringBuilder postData = new StringBuilder();
            // POST as urlencoded is basically key-value pairs, as with GET
            // This creates key=value&key=value&... pairs
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            // Convert string to byte array, as it should be sent
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            // Connect, easy
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Tell server that this is POST and in which format is the data
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            // This gets the output from your server
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            for (int c; (c = in.read()) >= 0; )
                System.out.print((char) c);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(1==1||userField.getText().equals("admin") && passwordField.getText().equals("admin"))
            stage.postLogin(userField.getText());
    }



}
