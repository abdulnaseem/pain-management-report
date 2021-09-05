package com.example.reportpain;

import java.io.Serializable;

public class User implements Serializable {

    private static int ID;
    private static String fullname;
    private static String dateOfBirth;
    private static String weight;
    private static String height;
    private static String bmi;
    private static String username;
    private static String password;


    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }
    public String getFullname(){
        return fullname;
    }

    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }
    public String getWeight(){
        return weight;
    }

    public void setHeight(String height){
        this.height = height;
    }
    public String getHeight(){
        return height;
    }

    public void setBmi(String bmi){
        this.bmi = bmi;
    }
    public String getBmi(){
        return bmi;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
