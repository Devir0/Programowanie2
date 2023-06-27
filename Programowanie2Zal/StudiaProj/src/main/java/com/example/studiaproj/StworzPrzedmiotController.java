package com.example.studiaproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class StworzPrzedmiotController {
    DatabaseController DAL = new DatabaseController();
    private Ekwi ekw;
    @FXML
    public TextField Nazwa;
    @FXML
    public ChoiceBox<String> Typ;
    @FXML
    public ChoiceBox<String> Klasy;
    @FXML
    public ChoiceBox<Integer> Level;
    @FXML
    public TextArea TextOpis;
    @FXML
    public Button Stworz;
    private void generatelvls(){
        for(int i=1;i<99;i++){
            Level.getItems().add(i);
        }
    }
    public void PassEkwi(Ekwi eq){
        this.ekw = eq;
    }
    public void initialize() throws SQLException {
        Klasy.setItems(DAL.KlasList());
        Typ.setItems(DAL.TypList());
        generatelvls();
        Nazwa.setTextFormatter(maxLength(15));
        TextOpis.setTextFormatter(maxLength(200));
    }

    public static TextFormatter<TextFormatter.Change> maxLength(int MaxL){
        UnaryOperator<TextFormatter.Change> LFilter = change -> change.getControlNewText().length() <= MaxL ? change : null;
        return new TextFormatter<>(LFilter);
    }
    public void StworzButton(ActionEvent actionEvent) throws SQLException {
        try {
            DAL.DodajPrzedmiot(Nazwa.getText(),Level.getSelectionModel().getSelectedItem(),Klasy.getSelectionModel().getSelectedItem(),TextOpis.getText(),ekw.getId(),Typ.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) Stworz.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
