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

class listCellBaseController extends ListCell<Album> {

    @FXML
    private Label headTitle;
    @FXML
    private ListView<Label> detailsList;
    @FXML
    private AnchorPane pane;

    @FXML
    private FXMLLoader mLLoader;


    private final GuiController parent;

    public listCellBaseController(GuiController parent){
        this.parent = parent;
    }

    @Override
    protected void updateItem(Album album, boolean empty) {
        super.updateItem(album, empty);

        if( detailsList!=null)
        detailsList.refresh();
        if(empty || album == null) {

            setText(null);
            setGraphic(pane);

        } else {
            if (mLLoader == null || detailsList==null) {
                mLLoader = new FXMLLoader(getClass().getResource("/listCellBase.fxml"));
                mLLoader.setController(this);


                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            headTitle.setText(String.valueOf(album.getAlbumName()));
            ObservableList<Label> labels = FXCollections.observableArrayList();
            for(Song song : album.getSongs()){
                Label label = new Label(album.getInterpreter().getInterName()+" - "+song.getSongName()+"("+String.valueOf(song.getSongLength())+")");
                label.setOnMouseClicked(e -> parent.handleWishListItem(song.getSongID()));
                labels.add(label);
            }

            ObservableList<Label> items = FXCollections.observableArrayList();
            items.addAll(labels);

            detailsList.getItems().clear();
            detailsList.getItems().addAll(items);

            detailsList.setFixedCellSize(35);
            double lastHeight = detailsList.getPrefHeight();
            detailsList.setPrefHeight((detailsList.getItems().size())*detailsList.getFixedCellSize()+detailsList.getFixedCellSize()/10);
            double newHeight = detailsList.getPrefHeight();

            pane.setPrefHeight(pane.getPrefHeight()-(lastHeight-newHeight));

            setText(null);
            setGraphic(pane);
        }

    }


}
