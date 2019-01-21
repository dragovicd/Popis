package com.example.dragovicd.popis.entity;

public class Lokacija {
    //Table name
    public static final String TABLE_NAME = "lokacija";

    //Create table Query
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SIFRA= "sifra";
    public static final String COLUMN_LOKACIJA= "lokacija";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SIFRA + " TEXT,"
                    + COLUMN_LOKACIJA + " TEXT"
                    + ")";
    private String  sifra, lokacija;

    private int id;

    public Lokacija() {
    }

    public Lokacija(int id, String sifra, String lokacija){
        this.id = id;
        this.sifra =sifra;
        this.lokacija =lokacija;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}
