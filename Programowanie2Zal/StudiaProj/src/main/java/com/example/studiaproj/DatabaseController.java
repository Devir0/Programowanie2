package com.example.studiaproj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.*;

public class DatabaseController {

public Connection Polacz() {
    Connection c = null;
        try {
             c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Devir/Desktop/studia/Java/StudiaProj/src/main/resources/StudiaProj.db");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return  c;
}
public String Register(String Name, String Password) throws SQLException {
    String wynik = null;
   Connection c = Polacz();
    String sqlName = "SELECT Name FROM User Where Name = ?";
    String sql = "INSERT INTO User VALUES ((SELECT max(Id) FROM User)+1,?,?)";
    try {
        if (c != null) {
            PreparedStatement pstmt = c.prepareStatement(sqlName);
            pstmt.setString(1, Name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() == false) {
                PreparedStatement pstmt2 = c.prepareStatement(sql);
                pstmt2.setString(1, Name);
                pstmt2.setInt(2, Password.hashCode());
                pstmt2.executeUpdate();
                wynik = "Pomyślnie dodano uzytkownika.";
            } else {
                wynik = "Istnieje juz konto z takim Loginem.";
            }
        }else {
            wynik = "Wystąpił problem z polączeniem";
        }
    } catch(SQLException ex){
        throw new RuntimeException(ex);}
    c.close();
    return wynik;
}
    User Login(String Name,String Password) throws SQLException {
    int Id = 0,PasswordHash = 0;
    Connection c = Polacz();
    String sql = "SELECT Id,PasswordHash FROM User WHERE Name= ?";
    try {
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1,Name);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()== false){
            Id = 0;
            PasswordHash = 0;
        }else {
            Id = rs.getInt("Id");
            PasswordHash = rs.getInt("PasswordHash");

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        c.close();
    if(Password.hashCode() == PasswordHash){
        return new User(Id,Name,PasswordHash);}
    else{
        return new User(0,null,0);
    }
}

    public ObservableList CharList(int Id_User) throws SQLException {
        ObservableList<Postac> Postacie;
        Postacie = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection c = Polacz();
        String sql = "SELECT P.Id_Postaci,P.Nazwa,K.Nazwa as Klasa,P.Level FROM Postac P JOIN Klasy K ON P.Id_Klasy = K.Id WHERE Id_User= ?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,Id_User);
            rs = pstmt.executeQuery();
            while (rs.next() && rs!=null){
                Postacie.add(new Postac(rs.getInt("Id_Postaci"),rs.getString("Nazwa"), rs.getString("Klasa"),rs.getInt("Level") ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.close();
    return Postacie;
    }
    public void DodajPostac(int User_id,String Name,String Klasa) throws SQLException {
    Connection c = Polacz();
        String sql = "INSERT INTO Postac VALUES ((SELECT max(Id_Postaci) FROM Postac)+1,?,(SELECT K.Id FROM Klasy K WHERE K.Nazwa = ?),1,?)";
        try {
            if (c != null) {
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, Name);
                pstmt.setString(2,Klasa);
                pstmt.setInt(3,User_id);
                pstmt.executeUpdate();
            } else {
                System.out.println("Nie udalo sie dodac Postaci!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        c.close();
    }

    public void UsunPostac(int Id_Postac) throws SQLException {
        Connection c = Polacz();
        c.createStatement().execute("PRAGMA foreign_keys = ON");
        String sql = "DELETE FROM Postac WHERE Id_Postaci = ? ";
        try {
            if (c != null) {
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setInt(1,Id_Postac);
                pstmt.executeUpdate();
            } else {
                System.out.println("Nie udalo sie usunąc Postaci!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        c.close();
    }
    public void DodajPrzedmiot(String Nazwa,int Lvl,String Klasa,String Opis,int id_ekwi,String Typ) throws SQLException {
        Connection c = Polacz();
        String sql = "INSERT INTO Przedmiot(Id,Nazwa_Przedmiotu,Wymagany_lvl,Wymagana_Klasa,Opis,Id_Ekwi,Id_Typ) VALUES((SELECT max(Id) FROM Przedmiot)+1,?,?,(SELECT Id FROM Klasy WHERE Nazwa=?),?,?,(SELECT Id FROM Typ_Przedmiotu WHERE Nazwa=?))";
        try {
            if (c != null) {
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, Nazwa);
                pstmt.setInt(2,Lvl);
                pstmt.setString(3,Klasa);
                pstmt.setString(4,Opis);
                pstmt.setInt(5,id_ekwi);
                pstmt.setString(6,Typ);
                pstmt.executeUpdate();
            } else {
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        c.close();
    }
    public ObservableList EkwiList(int Id_Postac) throws SQLException {
        ObservableList<Ekwi> Ekwipunki;
        Ekwipunki = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection c = Polacz();
        String sql = "SELECT E.Id,E.Nazwa,E.Id_Postaci FROM Ekwipunek E JOIN Postac P on E.Id_Postaci=P.Id_Postaci WHERE P.Id_Postaci = ?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,Id_Postac);
            rs = pstmt.executeQuery();
            while (rs.next() && rs!=null){
                Ekwipunki.add(new Ekwi(rs.getInt("Id"),rs.getString("Nazwa"),rs.getInt("Id_Postaci")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.close();
        return Ekwipunki;
    }
    public ObservableList KlasList() throws SQLException {
        ObservableList<String> Klasy;
        Klasy = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection c = Polacz();
        String sql = "SELECT Nazwa FROM Klasy";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next() && rs!=null){
                Klasy.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.close();
        return Klasy;
    }
    public ObservableList TypList() throws SQLException {
        ObservableList<String> Typy;
        Typy = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection c = Polacz();
        String sql = "SELECT Nazwa FROM Typ_Przedmiotu";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next() && rs!=null){
                Typy.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.close();
        return Typy;
    }
    public ObservableList PrzedList (int Id_Ekwi) throws SQLException {
        ObservableList<Przedmiot> Przedmioty;
        Przedmioty = FXCollections.observableArrayList();
        ResultSet rs = null;
        Connection c = Polacz();
        String sql = "SELECT P.Id,P.Nazwa_Przedmiotu,P.Wymagany_lvl,K.Nazwa,T.Nazwa,P.Opis FROM Przedmiot P JOIN Typ_Przedmiotu T ON T.Id=P.Id_Typ JOIN Klasy K ON P.Wymagana_Klasa=K.Id WHERE P.Id_Ekwi=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,Id_Ekwi);
            rs = pstmt.executeQuery();
            while (rs.next()){
                System.out.println("Tu");
                Przedmioty.add(new Przedmiot(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.close();
        return Przedmioty;
    }
}



