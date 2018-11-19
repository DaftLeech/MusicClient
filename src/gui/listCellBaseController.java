package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Album;
import model.Song;

import java.io.IOException;
import java.util.ArrayList;

public class listCellBaseController extends ListCell<Album> {

    @FXML
    private Label headTitle;
    @FXML
    private ListView<Label> detailsList;
    @FXML
    private AnchorPane pane;

    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Album album, boolean empty) {
        super.updateItem(album, empty);

        if(empty || album == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/gui/listCellBase.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            headTitle.setText(String.valueOf(album.getName()));
            ArrayList<Label> labels = new ArrayList<>();
            for(Song song : album.getSongs()){
                labels.add(new Label(album.getInterpreter().getName()+" - "+song.getName()+"("+String.valueOf(song.getLength())+")"));
            }
            ObservableList<Label> items = FXCollections.observableArrayList(labels);

            System.out.println(items.size()*detailsList.getFixedCellSize());
            detailsList.setFixedCellSize(35);
            detailsList.setItems(items);
            double lastHeight = detailsList.getPrefHeight();
            detailsList.setPrefHeight((detailsList.getItems().size())*detailsList.getFixedCellSize()+detailsList.getFixedCellSize()/10);
            double newHeight = detailsList.getPrefHeight();

            pane.setPrefHeight(pane.getPrefHeight()-(lastHeight-newHeight));

            setText(null);
            setGraphic(pane);
        }

    }
}
