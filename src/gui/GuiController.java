package gui;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import model.Album;
import model.Interpreter;
import model.Song;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuiController implements Initializable{

    private Main stage;
    private enum searchTypes {INTERPRETER , ALBUM};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("INIT");
        ObservableList<String> sTypeNames = FXCollections.observableArrayList(searchTypes.INTERPRETER.name(),searchTypes.ALBUM.name());
        searchCombo.setItems(sTypeNames);
        searchCombo.getSelectionModel().select(0);

        Interpreter i = new Interpreter("Inter");
        Song s = new Song("Song",100);
        ArrayList<Song> aS = new ArrayList<>();
        for(int ix = 0; ix < 100; ix++)
            aS.add(s);
        Album a = new Album("Album",i,aS);

        ObservableList<Album> items = FXCollections.observableArrayList(a);
        addItemToResultList(items);

        resultList.setCellFactory(studentListView -> new listCellBaseController());

    }

    public void addItemToResultList(ObservableList<Album> items){

        ObservableList<Album> currentList = resultList.getItems();
        currentList.addAll(items);
        resultList.setItems(currentList);


    }

    public void setStage(Main stage){
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

    public GuiController(){


    }


    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        // Button was clicked, do something…
        System.out.println(searchText.getText()+" ");

    }



}
