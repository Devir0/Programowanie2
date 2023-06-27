package com.example.studiaproj;

import java.security.PublicKey;

public class User {
    User(int Id,String Name,int Hash){
        this.Id = Id;
        this.Name = Name;
        this.PasswordHash = Hash;
    }
    public int Id;
    public String  Name;
    public int PasswordHash;



}
