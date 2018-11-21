package gui;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Album;
import model.Interpreter;
import model.Song;
import general.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GuiController implements Initializable {

    private Main stage;

    private enum searchTypes {INTERPRETER, ALBUM}

    ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("INIT");
        ObservableList<String> sTypeNames = FXCollections.observableArrayList(searchTypes.INTERPRETER.name(), searchTypes.ALBUM.name());
        searchCombo.setItems(sTypeNames);
        searchCombo.getSelectionModel().select(0);

        /*
        Interpreter i = new Interpreter("Inter");
        Song s = new Song(-1,"Song",100,-1);
        ArrayList<Song> aS = new ArrayList<>();
        for(int ix = 0; ix < 100; ix++)
            aS.add(s);
        Album a = new Album(-1,"Album",i,aS);

        ObservableList<Album> items = FXCollections.observableArrayList(a);
        addItemToResultList(items);
        */

        resultList.setCellFactory(ListView -> new listCellBaseController());

    }

    public void addItemToResultList(ObservableList<Album> items) {

        ObservableList<Album> currentList = resultList.getItems();
        currentList.addAll(items);
        resultList.setItems(currentList);


    }

    public void clearResultList() {
        resultList.setItems(FXCollections.observableArrayList());
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    @FXML
    private JFXTextField searchText;

    @FXML
    private JFXComboBox<String> searchCombo;

    @FXML
    private JFXListView<Album> resultList;

    @FXML
    private JFXButton searchButton;

    public GuiController() {


    }


    @FXML
    private void handleSearchButtonAction(ActionEvent event) {

        clearResultList();
        try {
            boolean searchByInter = searchCombo.getSelectionModel().getSelectedItem().equals(searchTypes.INTERPRETER.name());
            String path = searchByInter ? "Interpreter/byText" : "Album/byText";
            String query = searchByInter ? "interName" : "albumName";
            HttpURLConnection connection = getConnection(path, query, searchText.getText());

            ObservableList<Album> items = FXCollections.observableArrayList();
            ObjectMapper mapper = new ObjectMapper();
            List<Interpreter> interpreters = new LinkedList();
            InputStream json = null;


            System.out.println("Mapping...");
            if (searchByInter) {
                //get Interpreters
                json = connection.getInputStream();
                mapper = new ObjectMapper();
                interpreters = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Interpreter.class));
            } else {
                //get Albums
                json = connection.getInputStream();
                mapper = new ObjectMapper();
                List<Album> albums = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Album.class));

                //get Interpreters
                for (Album album : albums) {
                    path = "Interpreter/byAlbumID";
                    query = "albumID";
                    connection = getConnection(path, query, String.valueOf(album.getAlbumID()));
                    json = connection.getInputStream();
                    mapper = new ObjectMapper();
                    interpreters = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Interpreter.class));
                }
            }
            for (Interpreter interpreter : interpreters) {
                path = "Album/byInterID";
                query = "interID";

                connection = getConnection(path, query, String.valueOf(interpreter.getInterID()));
                json = connection.getInputStream();
                mapper = new ObjectMapper();
                List<Album> albums = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Album.class));

                for (Album album : albums) {

                    path = "Song/byAlbumID";
                    query = "albumID";
                    connection = getConnection(path, query, String.valueOf(album.getAlbumID()));
                    json = connection.getInputStream();
                    mapper = new ObjectMapper();
                    List<Song> songs = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Song.class));

                    items.add(new Album(album.getAlbumID(), album.getAlbumName(), interpreter, new ArrayList<>(songs)));

                }


            }


            addItemToResultList(items);
            System.out.println("Mapped.");


        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }


        System.out.println(searchText.getText() + " ");

    }

    private HttpURLConnection getConnection(String path, String query, String searchText) throws Exception {

        System.out.println("Connecting...");
        String uri =
                "http://localhost:18181/" + path + "?" + query + "=" + searchText;
        URL url = new URL(uri);
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        System.out.println(uri);
        System.out.println("Connected.");

        return connection;

    }


}
