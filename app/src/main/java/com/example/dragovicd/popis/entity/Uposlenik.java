package com.example.dragovicd.popis.entity;

public class Uposlenik {
    //Table name
    public static final String TABLE_NAME = "uposlenik";

    //Create table Query
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IME= "ime";
    public static final String COLUMN_PREZIME= "prezime";
    public static final String COLUMN_STATUS= "status"; //Aktivan, Neaktivan

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_IME + " TEXT,"
                    + COLUMN_PREZIME + " TEXT,"
                    + COLUMN_STATUS + " TEXT"
                    + ")";
    private String  ime, prezime, status;

    private int id;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
