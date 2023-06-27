package com.example.studiaproj;

import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Przedmiot implements Serializable {
    private int Id;
    private String Nazwa;
    private int WLevel;
    private String WKlasa;
    private String Typ;
    private String Opis;

    public Przedmiot(int id, String nazwa, int lvl, String klasa, String typ, String opis) {
        this.Id = id;
        this.Nazwa = nazwa;
        this.WLevel = lvl;
        this.WKlasa = klasa;
        this.Typ = typ;
        this.Opis = opis;


    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public int getWLevel() {
        return WLevel;
    }

    public void setWLevel(int WLevel) {
        this.WLevel = WLevel;
    }

    public String getWKlasa() {
        return WKlasa;
    }

    public void setWKlasa(String WKlasa) {
        this.WKlasa = WKlasa;
    }

    public String getTyp() {
        return Typ;
    }

    public void setTyp(String typ) {
        Typ = typ;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }
}
