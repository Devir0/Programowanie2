package com.example.studiaproj;

public class Postac {

    private int Id_Postaci;
    private String Nazwa;
    private String Klasa;
    private int Level;
    public int getId_Postaci() {
        return Id_Postaci;
    }

    public void setId_Postaci(int id_Postaci) {
        Id_Postaci = id_Postaci;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public String getKlasa() {
        return Klasa;
    }

    public void setKlasa(String klasa) {
        Klasa = klasa;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }


    public Postac(int idPostaci, String nazwa, String klasa, int level) {
        this.Id_Postaci = idPostaci;
        this.Nazwa=nazwa;
        this.Klasa=klasa;
        this.Level=level;
    }

    private Przedmiot Napiersnik = null;
    private Przedmiot Naramiennik = null;
    private Przedmiot Helm = null;
    private Przedmiot Buty = null;
    private Przedmiot Bron = null;

    public Przedmiot getNapiersnik() {
        return Napiersnik;
    }

    public void setNapiersnik(Przedmiot napiersnik) {
        Napiersnik = napiersnik;
    }

    public Przedmiot getNaramiennik() {
        return Naramiennik;
    }

    public void setNaramiennik(Przedmiot naramiennik) {
        Naramiennik = naramiennik;
    }

    public Przedmiot getHelm() {
        return Helm;
    }

    public void setHelm(Przedmiot helm) {
        Helm = helm;
    }

    public Przedmiot getButy() {
        return Buty;
    }

    public void setButy(Przedmiot buty) {
        Buty = buty;
    }

    public Przedmiot getBron() {
        return Bron;
    }

    public void setBron(Przedmiot bron) {
        Bron = bron;
    }

    @Override
    public String toString(){
        return Nazwa + " [" + Level + "]";
    }
}
