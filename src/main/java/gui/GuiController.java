package gui;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;




public class GuiController implements Initializable {

    private enum searchTypes {INTERPRETER, ALBUM}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> sTypeNames = FXCollections.observableArrayList(searchTypes.INTERPRETER.name(), searchTypes.ALBUM.name());
        searchCombo.setItems(sTypeNames);
        searchCombo.getSelectionModel().select(0);


        clearResultList();
        loadWishList();
        resultList.setCellFactory(ListView -> new listCellBaseController(this));
        wishList.setCellFactory(ListView -> new listCellBaseController(this));

    }

    private void addItemToResultList(ObservableList<Album> items) {

        clearResultList();
        resultList.getItems().addAll(items);
        resultList.refresh();


    }

    private void addItemToWishList(ObservableList<Album> items) {

        clearWishList();
        wishList.getItems().addAll(items);
        wishList.refresh();

    }

    public void handleWishListItem(long songID){


        try {
            String path = "Wishlist/HandleID";
            String query = "userID";

            String uri =
                    "http://localhost:18181/" + path + "?" + query + "=" + String.valueOf(Main.user.getUserID())+"&songID="+String.valueOf(songID);
            URL url = new URL(uri);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.connect();
            System.out.println(connection.getResponseCode());
            System.out.println(uri);
            System.out.println("Connected.");

            loadWishList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void loadWishList() {

        clearWishList();
        try {

            System.out.println("Connecting...");
            String path = "Wishlist/IDs";
            String query = "userID";

            String uri =
                    "http://localhost:18181/" + path + "?" + query + "=" + String.valueOf(Main.user.getUserID());
            URL url = new URL(uri);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("Accept", "text/plain");
            System.out.println(uri);
            System.out.println("Connected.");
            ObservableList<Album> items = FXCollections.observableArrayList();
            ObjectMapper mapper;
            InputStream text = connection.getInputStream();
            String songIDs = new BufferedReader(new InputStreamReader(text))
                    .lines().collect(Collectors.joining("\n"));



            if(songIDs.equals(""))
                return;


            path = "Album/bySongIDs";
            query = "songIDs";
            connection = getConnection(path, query, songIDs);
            InputStream json = connection.getInputStream();
            mapper = new ObjectMapper();
            List<Album> albums = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Album.class));

            for (Album album : albums) {


                path = "Interpreter/byAlbumID";
                query = "albumID";
                connection = getConnection(path, query, String.valueOf(album.getAlbumID()));
                json = connection.getInputStream();
                mapper = new ObjectMapper();
                List<Interpreter> interpreters = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Interpreter.class));

                album.setInterpreter(interpreters.get(0));


                path = "Song/byAlbumIDAndSongIDs";
                query = "albumID";
                connection = getConnection(path, query, String.valueOf(album.getAlbumID())+"&songIDs="+songIDs);
                json = connection.getInputStream();
                mapper = new ObjectMapper();
                List<Song> songs = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Song.class));

                album.setSongs(new ArrayList<>(songs));
                items.add(album);



            }

            addItemToWishList(items);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearResultList() {
        resultList.getItems().clear();
        resultList.getItems().removeAll();
        resultList.setCellFactory(ListView -> new listCellBaseController(this));
        resultList.refresh();
    }

    private void clearWishList() {


        wishList.getItems().clear();
        wishList.getItems().removeAll(wishList.getItems());
        wishList.setCellFactory(ListView -> new listCellBaseController(this));
        wishList.refresh();

    }

    @FXML
    private JFXTextField searchText;

    @FXML
    private JFXComboBox<String> searchCombo;

    @FXML
    private JFXListView<Album> resultList;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXListView<Album> wishList;

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
            ObjectMapper mapper;
            List<Interpreter> interpreters = new LinkedList<>();
            InputStream json;


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
                path = searchByInter ? "Album/byInterID" : "Album/byInterIDAndText";
                query = "interID";
                String query2 = searchByInter ? "" : "&albumName=" + searchText.getText();
                connection = getConnection(path, query, String.valueOf(interpreter.getInterID()) + query2);
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


        } catch (Throwable ex) {
            ex.printStackTrace();
        }



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



