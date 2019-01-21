package com.example.dragovicd.popis;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.dragovicd.popis.entity.OsnovnoSredstvo;

public class ArtikliDodavanje extends AppCompatActivity {
DatabaseHelper db;
    String gotoNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikal_dodavanje);
        db = new DatabaseHelper(this);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Artikal dodavanje");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String sifra_artikla = extras.getString("SIFRA_ARTIKLA");
        gotoNext =  extras.getString("NEXT_ACTIVITY");


        EditText eteSifraArtikla = (EditText)findViewById(R.id.eteSifraArtikla);
        EditText eteLokacijaArtikla = (EditText)findViewById(R.id.eteLokacijaArtikla);
        EditText eteNazivArtikla = (EditText)findViewById(R.id.eteSifraArtikla);
        EditText eteOdgovornoLice = (EditText)findViewById(R.id.eteOdgovornoLice);
        if(gotoNext.equals("2")){
            String lokacija_artikla = extras.getString("LOKACIJA_ARTIKLA_NAZIV");

            eteSifraArtikla.setText(sifra_artikla);
            eteLokacijaArtikla.setText(lokacija_artikla);
            eteOdgovornoLice.setText("");
            eteSifraArtikla.setEnabled(false);
            if(!lokacija_artikla.equals("")){
                eteLokacijaArtikla.setEnabled(false);
            }

            eteNazivArtikla.requestFocus();
        }else{
            eteSifraArtikla.setText(sifra_artikla);

        }

    }

    public void goToArtikliLista(View v) {
        finish();
     /*   if(gotoNext.equals("2")){
            Intent intent = new Intent(ArtikliDodavanje.this,  BluetoothConnectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (gotoNext.equals("3")){
            Intent intent = new Intent(ArtikliDodavanje.this,  ArtikliActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveArtikal(View v) {

        EditText sifraArtikla = (EditText) findViewById(R.id.eteSifraArtikla);
        EditText nazivArtikla = (EditText) findViewById(R.id.eteNazivArtikla);
        EditText odgovornoLice = (EditText) findViewById(R.id.eteOdgovornoLice);
        EditText lokacijaArtikla = (EditText) findViewById(R.id.eteLokacijaArtikla);
        EditText napomenaArtikal = (EditText) findViewById(R.id.eteNapomenaArtikla);

        OsnovnoSredstvo artikal = new OsnovnoSredstvo();
        artikal.setSifra((sifraArtikla.getText()).toString());
        artikal.setNaziv((nazivArtikla.getText()).toString());
        artikal.setLokacija_naziv((lokacijaArtikla.getText()).toString());
        artikal.setOdgovorno_lice_ime((odgovornoLice.getText()).toString());
        artikal.setNapomena((napomenaArtikal.getText()).toString());
        artikal.setPopisan("0");
        db.insertArtikal(artikal);
        goToArtikliLista(v);


    }

}