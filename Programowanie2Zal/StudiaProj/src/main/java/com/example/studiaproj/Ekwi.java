package com.example.studiaproj;

public class Ekwi {
    Ekwi(int id,String nazwa,int Id_postaci){
        this.Id = id;
        this.Nazwa=nazwa;
        this.Id_Postaci=Id_postaci;
    }
    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_Postaci() {
        return Id_Postaci;
    }

    public void setId_Postaci(int id) {
        Id_Postaci = id;
    }

    private String Nazwa;
    private int Id;
    private int Id_Postaci;


}
