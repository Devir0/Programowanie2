package com.example.studiaproj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class StworzController {
    User Usr=null;
    DatabaseController DAL= new DatabaseController();
    public void setUsr(User U){
        this.Usr = U;
    }
    @FXML
    Button Stworz;
    @FXML
    TextField Nazwa;
    @FXML
    ChoiceBox Klasy;
    @FXML
    public void initialize() throws SQLException {
        Klasy.setItems(DAL.KlasList());
        Nazwa.setTextFormatter(maxLength(20));

    }
    public static TextFormatter<Change> maxLength(int MaxL){
        UnaryOperator<Change> LFilter = change -> change.getControlNewText().length() <= MaxL ? change : null;
        return new TextFormatter<>(LFilter);
    }
    @FXML
    public void StworzButton() throws SQLException {
    if(Usr!=null){
    DAL.DodajPostac(Usr.Id,Nazwa.getText(),Klasy.getSelectionModel().getSelectedItem().toString());
        Stage stage = (Stage) Stworz.getScene().getWindow();
        stage.close();

    }
    }
}
