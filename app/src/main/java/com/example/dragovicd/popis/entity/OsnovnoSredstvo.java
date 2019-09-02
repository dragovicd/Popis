package com.example.dragovicd.popis.entity;

import java.util.ArrayList;

public class OsnovnoSredstvo extends ArrayList<CharSequence> {

    //Table name
    public static final String TABLE_NAME = "artikli";

    //Create table Query
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SIFRA= "sifra";
    public static final String COLUMN_NAZIV_OS= "naziv";

    public static final String COLUMN_LOKACIJA_SIFRA= "lokacija";
    public static final String COLUMN_LOKACIJA_NAZIV= "lokacija_naziv";

    public static final String COLUMN_LOKACIJA_SIFRA_STARA= "lokacija_stara";
    public static final String COLUMN_LOKACIJA_NAZIV_STARA= "lokacija_stara_naziv";

    public static final String COLUMN_LOKACIJA_PROMJENA= "lokacija_promjena";

    public static final String COLUMN_ODGOVORNO_LICE_SIFRA= "odgovorno_lice_sifra";
    public static final String COLUMN_ODGOVORNO_LICE_IME= "odgovorno_lice_ime";

    public static final String COLUMN_ODGOVORNO_LICE_SIFRA_STARA= "odgovorno_lice_sifra_stara";
    public static final String COLUMN_ODGOVORNO_LICE_IME_STARA= "odgovorno_lice_ime_stara";



    public static final String COLUMN_SLIKA= "slika";
    public static final String COLUMN_NAPOMENA = "napomena";
    public static final String COLUMN_POPISAN= "popisan";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SIFRA + " TEXT,"
                    + COLUMN_NAZIV_OS + " TEXT,"
                    + COLUMN_LOKACIJA_SIFRA + " TEXT,"
                    + COLUMN_LOKACIJA_NAZIV + " TEXT,"
                    + COLUMN_LOKACIJA_SIFRA_STARA + " TEXT,"
                    + COLUMN_LOKACIJA_NAZIV_STARA + " TEXT,"
                    + COLUMN_LOKACIJA_PROMJENA + " TEXT,"
                    + COLUMN_ODGOVORNO_LICE_SIFRA + " TEXT,"
                    + COLUMN_ODGOVORNO_LICE_IME + " TEXT,"
                    + COLUMN_ODGOVORNO_LICE_SIFRA_STARA + " TEXT,"
                    + COLUMN_ODGOVORNO_LICE_IME_STARA + " TEXT,"
                    + COLUMN_SLIKA + " TEXT,"
                    + COLUMN_NAPOMENA + " TEXT,"
                    + COLUMN_POPISAN + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    private String  sifra, naziv, lokacija, lokacija_naziv,  lokacija_stara, lokacija_naziv_stara, lokacija_promjena, odgovorno_lice_sifra, odgovorno_lice_ime_stara,odgovorno_lice_sifra_stara, odgovorno_lice_ime, slika, napomena, popisan, timestamp;

    private int id;

    public OsnovnoSredstvo() {
    }

    public OsnovnoSredstvo(int id, String sifra, String naziv,String lokacija, String lokacija_naziv, String lokacija_stara, String lokacija_naziv_stara, String lokacija_promjena, String odgovorno_lice_sifra, String odgovorno_lice_ime,String odgovorno_lice_sifra_stara, String odgovorno_lice_ime_stara, String slika, String napomena, String popisan, String timestamp) {

        this.id = id;
        this.sifra =sifra;
        this.naziv =naziv;

        this.lokacija =lokacija;
        this.lokacija_naziv = lokacija_naziv;
        this.lokacija_stara =lokacija_stara;
        this.lokacija_naziv_stara = lokacija_naziv_stara;
        this.lokacija_promjena = lokacija_promjena;

        this.odgovorno_lice_sifra = odgovorno_lice_sifra;
        this.odgovorno_lice_ime = odgovorno_lice_ime;

        this.odgovorno_lice_sifra_stara = odgovorno_lice_sifra_stara;
        this.odgovorno_lice_ime_stara = odgovorno_lice_ime_stara;

        this.slika = slika;
        this.napomena = napomena;
        this.popisan = popisan;
        this.timestamp = timestamp;

    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getLokacija_naziv() {
        return lokacija_naziv;
    }

    public void setLokacija_naziv(String lokacija_naziv) {
        this.lokacija_naziv = lokacija_naziv;
    }

    public String getLokacija_stara() {
        return lokacija_stara;
    }

    public void setLokacija_stara(String lokacija_stara) {
        this.lokacija_stara = lokacija_stara;
    }

    public String getLokacija_naziv_stara() {
        return lokacija_naziv_stara;
    }

    public void setLokacija_naziv_stara(String lokacija_naziv_stara) {
        this.lokacija_naziv_stara = lokacija_naziv_stara;
    }

    public String getLokacija_promjena() {
        return lokacija_promjena;
    }

    public void setLokacija_promjena(String lokacija_promjena) {
        this.lokacija_promjena = lokacija_promjena;
    }

    public String getOdgovorno_lice_sifra() {
        return odgovorno_lice_sifra;
    }

    public void setOdgovorno_lice_sifra(String odgovorno_lice_sifra) {
        this.odgovorno_lice_sifra = odgovorno_lice_sifra;
    }

    public String getOdgovorno_lice_ime() {
        return odgovorno_lice_ime;
    }

    public void setOdgovorno_lice_ime(String odgovorno_lice_ime) {
        this.odgovorno_lice_ime = odgovorno_lice_ime;
    }



    public String getOdgovorno_lice_sifra_stara() {
        return odgovorno_lice_sifra_stara;
    }

    public void setOdgovorno_lice_sifra_stara(String odgovorno_lice_sifra_stara) {
        this.odgovorno_lice_sifra = odgovorno_lice_sifra;
    }

    public String getOdgovorno_lice_ime_stara() {
        return odgovorno_lice_ime_stara;
    }

    public void setOdgovorno_lice_ime_stara(String odgovorno_lice_ime_stara) {
        this.odgovorno_lice_ime_stara = odgovorno_lice_ime;
    }



    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getPopisan() {
        return popisan;
    }

    public void setPopisan(String popisan) {
        this.popisan = popisan;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

}
