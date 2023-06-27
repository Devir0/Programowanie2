package com.example.studiaproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class LoginController {
    DatabaseController DAL = new DatabaseController();
    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    private PasswordField Haslo1 = new PasswordField();
    @FXML
    private PasswordField Haslo2 = new PasswordField();
    @FXML
    private TextField Nazwa;
    @FXML
    private PasswordField Haslo = new PasswordField();
    @FXML
    Label errorR;
    @FXML
    Label errorL;
    @FXML
    public void initialize() throws SQLException {
        Nazwa.setTextFormatter(maxLength(20));
        Haslo1.setTextFormatter(maxLength(12));
        Haslo2.setTextFormatter(maxLength(12));
        Haslo.setTextFormatter(maxLength(12));
    }


    @FXML
    protected void LoginButtonClick(ActionEvent event) throws IOException, SQLException {
        User Res = new User(0,null,0);
        Res = DAL.Login(Nazwa.getText(),Haslo.getText());
        if(Res.Id>0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Editor.fxml"));
            root = loader.load();
            EdytorController Editor = loader.getController();
            Editor.PassUser(Res,DAL);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }else {
            errorL.setText("Huja tam,Podaj dobre haslo :)");
        }
    }
    @FXML
    protected void RegisterButtonClick() throws SQLException, IOException {
    if(!Nazwa.getText().isBlank() && !Haslo1.getText().isBlank() && !Haslo2.getText().isBlank()){
        if(Haslo2.getText().equals(Haslo1.getText())) {

            errorR.setText(DAL.Register(Nazwa.getText(), Haslo1.getText()));
            Nazwa.clear();
            Haslo2.clear();
            Haslo1.clear();
        }
        else {
            errorR.setText("Hasla nie sa takie same");
            Haslo1.clear();
            Haslo2.clear();
        }
    }
    }
    @FXML
    protected void SwitchLButtonClick(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("Register.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    @FXML
    protected void SwitchRButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static TextFormatter<TextFormatter.Change> maxLength(int MaxL){
        UnaryOperator<TextFormatter.Change> LFilter = change -> change.getControlNewText().length() <= MaxL ? change : null;
        return new TextFormatter<>(LFilter);
    }
}