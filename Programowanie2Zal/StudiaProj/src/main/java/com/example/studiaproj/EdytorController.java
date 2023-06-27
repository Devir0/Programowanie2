package com.example.studiaproj;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class EdytorController {

    private User Usr;
    DatabaseController D;
    private Postac SelectedChar;
    @FXML
    Label UserN;
    @FXML
    Label Klasa;
    @FXML
    Label Level;
    public void PassUser(User U,DatabaseController DA) throws SQLException {
        Usr = U;
        D = DA;
        UserN.setText("Witaj: "+ Usr.Name);
        Postacie.setItems(D.CharList(Usr.Id));
    }
    //PostacSloty
    @FXML
    private Rectangle SlotHelm;
    @FXML
    public Label TextHelm;
    @FXML
    private Rectangle SlotNapiersnik;
    @FXML
    private Label TextNapiersnik;

    @FXML
    private Rectangle SlotNaramiennik;
    @FXML
    private Label TextNaramiennik;

    @FXML
    private Rectangle SlotButy;
    @FXML
    private Label TextButy;
    @FXML
    private Rectangle SlotBron;
    @FXML
    private Label TextBron;
    //Listy
    @FXML
    private ChoiceBox<Postac> Postacie = new ChoiceBox<>();
    @FXML
    private TableView<Ekwi> Ekwi = new TableView<>();
    @FXML
    private TableColumn<Ekwi,String> EkwiN;
    @FXML
    private TableView<Przedmiot> Tprzedmiot = new TableView<>();
    @FXML
    private TableColumn<Przedmiot,String> PNazwa;
    @FXML
    private TableColumn<Przedmiot,Integer> PLvl;
    @FXML
    private TableColumn<Przedmiot,String> PKlasa;
    @FXML
    private TableColumn<Przedmiot,String> PTyp;
    @FXML
    public void initialize(){
        initDragAndDrop();
        PropertyValueFactory<Ekwi,String> EkwiNameFact = new PropertyValueFactory<>("Nazwa");
        PropertyValueFactory<Przedmiot,String > PrzedNameFact = new PropertyValueFactory<>("Nazwa");
        PropertyValueFactory<Przedmiot,Integer > PrzedLvlFact = new PropertyValueFactory<>("WLevel");
        PropertyValueFactory<Przedmiot,String > PrzedKlasaFact = new PropertyValueFactory<>("WKlasa");
        PropertyValueFactory<Przedmiot,String > PrzedTypFact = new PropertyValueFactory<>("Typ");

        //-----------------------------------------------------------------------------------------//
        EkwiN.setCellValueFactory(EkwiNameFact);
        PNazwa.setCellValueFactory(PrzedNameFact);
        PLvl.setCellValueFactory(PrzedLvlFact);
        PKlasa.setCellValueFactory(PrzedKlasaFact);
        PTyp.setCellValueFactory(PrzedTypFact);
        //----------------------------------------------------------------------------------------//
        Postacie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Postac>() {
            @Override
            public void changed(ObservableValue<? extends Postac> observableValue, Postac postac, Postac t1) {
                try {
                    if(t1 != null){
                        SelectedChar = t1;
                        Ekwi.setItems(D.EkwiList(t1.getId_Postaci()));
                        SlotChange(t1);
                    }
                } catch (SQLException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Ekwi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<com.example.studiaproj.Ekwi>() {
            @Override
            public void changed(ObservableValue<? extends com.example.studiaproj.Ekwi> observableValue, com.example.studiaproj.Ekwi ekwi, com.example.studiaproj.Ekwi t1) {
                try {
                    if(t1 != null){
                        Tprzedmiot.setItems(D.PrzedList(t1.getId()));}
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

}
public void SlotChange(Postac p2 ) throws URISyntaxException {
        Image img = null;
        try {
            if(p2 != null) {
                Klasa.setText("Klasa: " + p2.getKlasa());
                Level.setText("Level: " + p2.getLevel());
                if (p2.getNapiersnik() == null) {
                    SlotNapiersnik.setFill(Color.DODGERBLUE);
                    TextNapiersnik.setText("Napiersnik: ");
                } else {
                    img = new Image(new File(getClass().getResource("/Chestplate.png").toURI()).toString());
                    SlotNapiersnik.setFill(new ImagePattern(img));
                    TextNapiersnik.setText("Napiersnik: " + p2.getNapiersnik().getNazwa());
                }
                if (p2.getHelm() == null) {
                    SlotHelm.setFill(Color.DODGERBLUE);
                    TextHelm.setText("Helm: ");
                } else {
                    img = new Image(new File(getClass().getResource("/Helmet.png").toURI()).toString());
                    SlotHelm.setFill(new ImagePattern(img));
                }
                if (p2.getNaramiennik() == null) {
                    SlotNaramiennik.setFill(Color.DODGERBLUE);
                    TextNaramiennik.setText("Naramiennik: ");
                } else {
                    img = new Image(new File(getClass().getResource("/Shoulder.png").toURI()).toString());
                    SlotNaramiennik.setFill(new ImagePattern(img));
                    TextNaramiennik.setText("Naramiennik: " + p2.getNaramiennik().getNazwa());
                }
                if (p2.getButy() == null) {
                    SlotButy.setFill(Color.DODGERBLUE);
                    TextButy.setText("Buty: ");
                } else {
                    img = new Image(new File(getClass().getResource("/Boots.png").toURI()).toString());
                    SlotButy.setFill(new ImagePattern(img));
                    TextButy.setText("Buty: " + p2.getButy().getNazwa());
                }
                if (p2.getBron() == null) {
                    SlotBron.setFill(Color.DODGERBLUE);
                    TextBron.setText("Bron: ");
                } else {
                    img = new Image(new File(getClass().getResource("/Bron.png").toURI()).toString());
                    SlotBron.setFill(new ImagePattern(img));
                    TextBron.setText("Bron: " + p2.getBron().getNazwa());
                }
            }
        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
}
@FXML
public void StworzButton() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Stworz.fxml"));
    Stage stage = new Stage();
    stage.setTitle("TworzeniePostaci");
    stage.setScene(new Scene(loader.load(),240,340));
    StworzController pass= loader.getController();
    pass.setUsr(Usr);
    stage.showAndWait();
    Postacie.setItems(D.CharList(Usr.Id));
    }
    public void StworzPrzedmiotButton(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StworzPrzedmiot.fxml"));
        Stage stage = new Stage();
        stage.setTitle("TworzeniePostaci");
        stage.setScene(new Scene(loader.load(),240,340));
        StworzPrzedmiotController pass= loader.getController();
        pass.PassEkwi(Ekwi.getSelectionModel().getSelectedItem());
        stage.showAndWait();
        Tprzedmiot.setItems(D.PrzedList(Ekwi.getSelectionModel().getSelectedItem().getId()));
    }

    public void UsunPButton(ActionEvent actionEvent) throws SQLException {
        Postac UsunP;
        UsunP = Postacie.getSelectionModel().getSelectedItem();
        D.UsunPostac(UsunP.getId_Postaci());
        Postacie.setItems(D.CharList(Usr.Id));
        Ekwi.getItems().clear();
    }

    public void WylogujButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Usr = null;

    }
    private void initDragAndDrop(){
         DataFormat PrzedDataFormat = DataFormat.lookupMimeType("Przedmiot");
         if(PrzedDataFormat == null){
             PrzedDataFormat = new DataFormat("Przedmiot");
         }
        DataFormat finalPrzedDataFormat = PrzedDataFormat;
        Tprzedmiot.setOnDragDetected(event ->{
            Dragboard db = Tprzedmiot.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.put(finalPrzedDataFormat,Tprzedmiot.getSelectionModel().getSelectedItem());
            db.setContent(content);
        });
        //Helm
        SlotHelm.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.ANY);
            if(db.hasContent(finalPrzedDataFormat)) {
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Helm")){
                    SlotHelm.setFill(Color.GREEN);
                } else {
                    if(SelectedChar.getHelm() == null)
                        SlotHelm.setFill(Color.RED);
                }
            }
        });
        SlotHelm.setOnDragDropped(event ->{
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasContent(finalPrzedDataFormat)){
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Helm")){
                    Image img = null;
                    try {
                        img = new Image(new File(getClass().getResource("/Helmet.png").toURI()).toString());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    SelectedChar.setHelm(droppedPrzed);
                    SlotHelm.setFill(new ImagePattern(img));
                    TextHelm.setText("Helm: "+ droppedPrzed.getNazwa());
                    Tprzedmiot.getItems().remove(Tprzedmiot.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        //Napiersnik
        SlotNapiersnik.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.ANY);
            if(db.hasContent(finalPrzedDataFormat)) {
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Napiersnik")){
                    SlotNapiersnik.setFill(Color.GREEN);
                } else {
                    if(SelectedChar.getNapiersnik() == null)
                        SlotNapiersnik.setFill(Color.RED);
                }
            }
            event.consume();
        });
        SlotNapiersnik.setOnDragDropped(event ->{
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasContent(finalPrzedDataFormat)){
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Napiersnik")){
                    Image img = null;
                    try {
                        img = new Image(new File(getClass().getResource("/Chestplate.png").toURI()).toString());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    SelectedChar.setNapiersnik(droppedPrzed);
                    SlotNapiersnik.setFill(new ImagePattern(img));
                    TextNapiersnik.setText("Napiersnik: "+ droppedPrzed.getNazwa());
                    Tprzedmiot.getItems().remove(Tprzedmiot.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        //Buty
        SlotButy.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.ANY);
            if(db.hasContent(finalPrzedDataFormat)) {
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Buty")){
                    SlotButy.setFill(Color.GREEN);
                } else {
                    if(SelectedChar.getButy() == null)
                        SlotButy.setFill(Color.RED);
                }
            }
            event.consume();
        });
        SlotButy.setOnDragDropped(event ->{
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasContent(finalPrzedDataFormat)){
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Buty")){
                    Image img = null;
                    try {
                        img = new Image(new File(getClass().getResource("/Boots.png").toURI()).toString());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    SelectedChar.setButy(droppedPrzed);
                    SlotButy.setFill(new ImagePattern(img));
                    TextButy.setText("Buty: "+ droppedPrzed.getNazwa());
                    Tprzedmiot.getItems().remove(Tprzedmiot.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        //Bron
        SlotBron.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.ANY);
            if(db.hasContent(finalPrzedDataFormat)) {
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Bron")){
                    SlotBron.setFill(Color.GREEN);
                } else {
                    if(SelectedChar.getBron() == null)
                        SlotBron.setFill(Color.RED);
                }
            }
            event.consume();
        });
        SlotBron.setOnDragDropped(event ->{
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasContent(finalPrzedDataFormat)){
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Bron")){
                    Image img = null;
                    try {
                        img = new Image(new File(getClass().getResource("/Bron.png").toURI()).toString());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    SelectedChar.setBron(droppedPrzed);
                    SlotBron.setFill(new ImagePattern(img));
                    TextBron.setText("Bron: "+ droppedPrzed.getNazwa());
                    Tprzedmiot.getItems().remove(Tprzedmiot.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        //Naramiennik
        SlotNaramiennik.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.ANY);
            if(db.hasContent(finalPrzedDataFormat)) {
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Naramiennik")){
                    SlotNaramiennik.setFill(Color.GREEN);
                } else {
                    if(SelectedChar.getNaramiennik() == null)
                        SlotNaramiennik.setFill(Color.RED);
                }
            }
            event.consume();
        });
        //Naramiennik
        SlotNaramiennik.setOnDragDropped(event ->{
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasContent(finalPrzedDataFormat)){
                Przedmiot droppedPrzed = (Przedmiot) db.getContent(finalPrzedDataFormat);
                if(droppedPrzed.getTyp().equals("Naramiennik")){
                    Image img = null;
                    try {
                        img = new Image(new File(getClass().getResource("/Shoulder.png").toURI()).toString());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    SelectedChar.setNaramiennik(droppedPrzed);
                    SlotNaramiennik.setFill(new ImagePattern(img));
                    TextNaramiennik.setText("Naramiennik: "+ droppedPrzed.getNazwa());
                    Tprzedmiot.getItems().remove(Tprzedmiot.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }


}


