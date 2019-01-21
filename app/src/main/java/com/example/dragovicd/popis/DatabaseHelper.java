package com.example.dragovicd.popis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.dragovicd.popis.entity.OsnovnoSredstvo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "popis_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(OsnovnoSredstvo.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + OsnovnoSredstvo.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void onDropTableArtikli() {
        // Drop older table if existed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + OsnovnoSredstvo.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertArtikalR(String sifraArtikla, String nazivArtikla, String lokacijaArtikla) {

    }

    public long insertArtikalR2(OsnovnoSredstvo artikal) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(OsnovnoSredstvo.COLUMN_SIFRA, artikal.getSifra());
        values.put(OsnovnoSredstvo.COLUMN_NAZIV_OS, artikal.getNaziv());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME, artikal.getLokacija());
        values.put(OsnovnoSredstvo.COLUMN_SLIKA, artikal.getSlika());
        values.put(OsnovnoSredstvo.COLUMN_NAPOMENA, artikal.getNapomena());
        values.put(OsnovnoSredstvo.COLUMN_POPISAN, artikal.getPopisan());
        // insert row
        long id = db.insert(OsnovnoSredstvo.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertArtikal(OsnovnoSredstvo artikal) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(OsnovnoSredstvo.COLUMN_SIFRA, artikal.getSifra());
        values.put(OsnovnoSredstvo.COLUMN_NAZIV_OS, artikal.getNaziv());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA, "");
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV, artikal.getLokacija_naziv());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA, "");
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA, "");
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA, "0");
        values.put(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA, "");
        values.put(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME, artikal.getOdgovorno_lice_ime());
        values.put(OsnovnoSredstvo.COLUMN_SLIKA, artikal.getSlika());
        values.put(OsnovnoSredstvo.COLUMN_NAPOMENA, artikal.getNapomena());
        values.put(OsnovnoSredstvo.COLUMN_POPISAN, artikal.getPopisan());


        // insert row
        long id = db.insert(OsnovnoSredstvo.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public OsnovnoSredstvo getArtikal(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(OsnovnoSredstvo.TABLE_NAME,
                new String[]{
                        OsnovnoSredstvo.COLUMN_ID,
                        OsnovnoSredstvo.COLUMN_SIFRA,
                        OsnovnoSredstvo.COLUMN_NAZIV_OS,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME,
                        OsnovnoSredstvo.COLUMN_SLIKA,
                        OsnovnoSredstvo.COLUMN_NAPOMENA,
                        OsnovnoSredstvo.COLUMN_POPISAN,
                        OsnovnoSredstvo.COLUMN_TIMESTAMP
                },
                OsnovnoSredstvo.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare artikal object

        OsnovnoSredstvo artikal = new OsnovnoSredstvo(
                cursor.getInt(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAZIV_OS)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SLIKA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAPOMENA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_POPISAN)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_TIMESTAMP)));
        // close the db connection
        cursor.close();
        return artikal;
    }

    public OsnovnoSredstvo getArtikalBySifra(String sifra) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(OsnovnoSredstvo.TABLE_NAME,
                new String[]{
                        OsnovnoSredstvo.COLUMN_ID,
                        OsnovnoSredstvo.COLUMN_SIFRA,
                        OsnovnoSredstvo.COLUMN_NAZIV_OS,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA,
                        OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA_STARA,
                        OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME_STARA,
                        OsnovnoSredstvo.COLUMN_SLIKA,
                        OsnovnoSredstvo.COLUMN_NAPOMENA,
                        OsnovnoSredstvo.COLUMN_POPISAN,
                        OsnovnoSredstvo.COLUMN_TIMESTAMP
                },
                OsnovnoSredstvo.COLUMN_SIFRA + "=?",
                new String[]{String.valueOf(sifra)}, null, null, null, null);

        if (cursor != null)
                cursor.moveToFirst();
        OsnovnoSredstvo artikal = new OsnovnoSredstvo(
                cursor.getInt(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAZIV_OS)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_LOKACIJA_PROMJENA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME_STARA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SLIKA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAPOMENA)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_POPISAN)),
                cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_TIMESTAMP)));
        // close the db connection
        cursor.close();

        return artikal;
    }

    public List<OsnovnoSredstvo> getAllArtikal() {
        List<OsnovnoSredstvo> artikli = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OsnovnoSredstvo.TABLE_NAME + " ORDER BY " +
                OsnovnoSredstvo.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OsnovnoSredstvo artikal = new OsnovnoSredstvo();
                artikal.setId(cursor.getInt(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ID)));
                artikal.setSifra(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SIFRA)));
                artikal.setNaziv(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAZIV_OS)));

                artikal.setLokacija(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_SIFRA)));
                artikal.setLokacija_naziv(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_NAZIV)));
                artikal.setLokacija_stara(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_SIFRA_STARA)));
                artikal.setLokacija_naziv_stara(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_NAZIV_STARA)));
                artikal.setLokacija_promjena(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_PROMJENA)));

                artikal.setOdgovorno_lice_sifra(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA)));
                artikal.setOdgovorno_lice_ime(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME)));

                artikal.setOdgovorno_lice_sifra_stara(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA_STARA)));
                artikal.setOdgovorno_lice_ime_stara(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME_STARA)));


                artikal.setSlika(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SLIKA)));
                artikal.setNapomena(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAPOMENA)));
                artikal.setPopisan(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_POPISAN)));
                artikal.setTimestamp(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_TIMESTAMP)));
             artikli.add(artikal);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return artikli;
    }

    public int updateArtikal(OsnovnoSredstvo artikal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OsnovnoSredstvo.COLUMN_NAZIV_OS, artikal.getNaziv());
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_NAZIV, artikal.getLokacija_naziv());
        values.put(OsnovnoSredstvo.COLUMN_NAPOMENA, artikal.getNapomena());
        values.put(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME, artikal.getOdgovorno_lice_ime());

        // updating row
        int id = db.update(OsnovnoSredstvo.TABLE_NAME, values, OsnovnoSredstvo.COLUMN_SIFRA + " = ?",
                new String[]{String.valueOf(artikal.getSifra())});
        db.close();
        return id;
    }

    public int popisArtikal(String art_sifra) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OsnovnoSredstvo.COLUMN_POPISAN, "1");

        // updating row
        int id = db.update(OsnovnoSredstvo.TABLE_NAME, values, OsnovnoSredstvo.COLUMN_SIFRA + " = ?",
                new String[]{String.valueOf(art_sifra)});

        db.close();
        return id;
    }

    public int premjestiArtikalNaLokaciju(String sifraArtiklaS, String staraLokacija, String novaLokacija) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OsnovnoSredstvo.COLUMN_POPISAN, "1");
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA, novaLokacija);
        values.put(OsnovnoSredstvo.COLUMN_LOKACIJA_SIFRA_STARA, staraLokacija);

        // updating row
        int id = db.update(OsnovnoSredstvo.TABLE_NAME, values, OsnovnoSredstvo.COLUMN_SIFRA + " = ?",
                new String[]{String.valueOf(sifraArtiklaS)});

        db.close();
        return id;
    }

    public void deleteArtikal(String art_sifra) {
        //Toast.makeText(context, "Ovo je artikal " + art_sifra, Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OsnovnoSredstvo.TABLE_NAME, OsnovnoSredstvo.COLUMN_SIFRA + " = ?",
               new String[]{String.valueOf(art_sifra)});
        db.close();
    }

    public List<OsnovnoSredstvo> exportDataToCsv() {
        List<OsnovnoSredstvo> artikli = new ArrayList<>();

        // Select All Query
        String arg="1";
        String selectQuery = "SELECT  * FROM " + OsnovnoSredstvo.TABLE_NAME + " WHERE "+ OsnovnoSredstvo.COLUMN_POPISAN + "="+arg+" ORDER BY " +
                OsnovnoSredstvo.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OsnovnoSredstvo artikal = new OsnovnoSredstvo();
                artikal.setId(cursor.getInt(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ID)));
                artikal.setSifra(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SIFRA)));
                artikal.setNaziv(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAZIV_OS)));

                artikal.setLokacija(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_SIFRA)));
                artikal.setLokacija_naziv(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_NAZIV)));
                artikal.setLokacija_stara(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_SIFRA_STARA)));
                artikal.setLokacija_naziv_stara(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_NAZIV_STARA)));
                artikal.setLokacija_promjena(cursor.getString(cursor.getColumnIndex(artikal.COLUMN_LOKACIJA_PROMJENA)));

                artikal.setOdgovorno_lice_sifra(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA)));
                artikal.setOdgovorno_lice_ime(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME)));
                artikal.setOdgovorno_lice_sifra_stara(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_SIFRA_STARA)));
                artikal.setOdgovorno_lice_ime_stara(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_ODGOVORNO_LICE_IME_STARA)));

                artikal.setSlika(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_SLIKA)));
                artikal.setNapomena(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_NAPOMENA)));
                artikal.setPopisan(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_POPISAN)));
                artikal.setTimestamp(cursor.getString(cursor.getColumnIndex(OsnovnoSredstvo.COLUMN_TIMESTAMP)));
                artikli.add(artikal);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return artikli;
    }

}
