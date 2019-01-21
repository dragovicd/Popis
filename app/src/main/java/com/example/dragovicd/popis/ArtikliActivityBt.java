package com.example.dragovicd.popis;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dragovicd.popis.entity.OsnovnoSredstvo;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class ArtikliActivityBt extends AppCompatActivity  {


    private ArrayAdapter<OsnovnoSredstvo> adapter;
    private TextView data;
    private DatabaseHelper db;
    private MaterialSearchView searchView;
    private ListView listView;
    private List<OsnovnoSredstvo> artikli;
    private List<OsnovnoSredstvo> artikliResults;
    private List<OsnovnoSredstvo> artikliResultsFilter;
    private Dialog myDialog;
    private String filter_text;
    private EditText eteFlter;
    private int result;
    private int filtered;
    private String nextActivity = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikli_svi_lista_bt);
        db = new DatabaseHelper(ArtikliActivityBt.this);
        artikli = readDatabaseAllArtikli();
        myDialog = new Dialog(this);
        filtered = 0;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Artikli");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikli, nextActivity);
        listView.setAdapter(adapter);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        artikliResults = new ArrayList<OsnovnoSredstvo>();
        artikliResultsFilter = new ArrayList<OsnovnoSredstvo>();


        /*searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {

                if(newText != null && !newText.isEmpty()){

                    String si_art = "";
                    String popisan = "";
                    Intent intent;
                    result = 0;
                    if (filtered==0){
                        for(Artikal item:artikli){
                            if((item.getSifra()).equals(newText)){
                                si_art = item.getSifra();

                                if(item.getPopisan().equals("1")){
                                    Toast.makeText(ArtikliActivityBt.this, "Proizvod je vec popisan "+item.getSifra(), Toast.LENGTH_LONG).show();

                                }else{
                                    artikliResults.add(item);
                                    result = 1;
                                }

                            }
                        }
                    }else if (filtered==1){
                        for(Artikal item:artikliResultsFilter){
                            if((item.getSifra()).equals(newText)){
                                si_art = item.getSifra();

                                if(item.getPopisan().equals("1")){
                                    Toast.makeText(ArtikliActivityBt.this, "Proizvod je vec popisan "+item.getSifra(), Toast.LENGTH_LONG).show();

                                }else{
                                    artikliResults.add(item);
                                    result = 2;
                                }

                            }
                        }
                        if(result == 0) {
                            for (Artikal item : artikli) {
                                if ((item.getSifra()).equals(newText)) {
                                    si_art = item.getSifra();

                                    if (item.getPopisan().equals("1")) {
                                        Toast.makeText(ArtikliActivityBt.this, "Proizvod je vec popisan i ne pripada ovoj l,okaciji " + item.getSifra(), Toast.LENGTH_LONG).show();

                                    } else {
                                        artikliResults.add(item);
                                        result = 3;
                                    }
                                }
                            }
                        }
                    }

                    if(result == 1){
                        artikli.clear();
                        db.popisArtikal(si_art);
                        artikli = db.getAllArtikal();
                        artikli =  readDatabaseAllArtikli();
                        Toast.makeText(ArtikliActivityBt.this, "Proizvod je popisan "+artikliResults.get(0).getSifra(), Toast.LENGTH_LONG).show();
                        adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikli);
                        listView.setAdapter(adapter);
                     }
                    if(result == 2){
                        artikli.clear();
                        artikliResultsFilter.clear();
                        db.popisArtikal(si_art);
                        artikli = db.getAllArtikal();
                        for(Artikal item:artikli){
                            if(item.getLokacija().equals(filter_text)){
                                artikliResultsFilter.add(item);
                            }
                        }
                        adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikliResultsFilter);
                        listView.setAdapter(adapter);
                        Toast.makeText(ArtikliActivityBt.this, "Proizvod je popisan "+artikliResults.get(0).getSifra(), Toast.LENGTH_LONG).show();


                    }
                    if(result == 0 ){
                        Toast.makeText(ArtikliActivityBt.this, "Proizvod nema u evidenciji ", Toast.LENGTH_SHORT).show();
                        dodajArtikal(this);
                    }
                    if(result == 3 ){
                        Toast.makeText(ArtikliActivityBt.this, "Proizvod ne pripada ovoj lokaciji ", Toast.LENGTH_SHORT).show();
                        //goToArtikalDodavanje(
                    }
                }
                else{
                    //if search text is null
                    if(filtered == 0){
                        adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikli);
                    }else{
                        adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikliResultsFilter);
                    }
                    //adding the adapter to listview
                    listView.setAdapter(adapter);
                }
                searchView.setQuery("", false);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        /*MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);*/

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.filter_lokacije:
                PrikaziFilter(this);
                return true;
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public List<OsnovnoSredstvo> readDatabaseAllArtikli(){


        artikli= db.getAllArtikal();
        //listView = (ListView) findViewById(R.id.listView);
        //creating the adapter object
        //adapter_a = new ArtikliAdapter(ArtikliActivity.this, R.layout.item_layout, artikli);
        //adding the adapter to listview
        //listView.setAdapter(adapter_a);

    return artikli;
    }

    public void goToArtikalDodavanje(){
    Context context = this;
    Intent intent = new Intent(context, ArtikliDodavanje.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    }
    public void goToSkenerOpcije(){
        //Context context = this;
        //Intent intent = new Intent(context, BluetoothConnectActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
    }

    public void PrikaziFilter(ArtikliActivityBt v) {
        Button btn_zatvori;
        Button btn_filtriraj;
        myDialog.setContentView(R.layout.filter_layout);

        btn_zatvori =(Button) myDialog.findViewById(R.id.btn_zatvori);

        btn_filtriraj = (Button) myDialog.findViewById(R.id.btn_popisi);


        //final String filter_text = ((EditText)().getText().toString();

        btn_filtriraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filtriraj(view);
                //myDialog.dismiss();
            }
        });

        btn_zatvori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //myDialog.setTitle("Filter po lokaciji");
        final Window window = myDialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void filtriraj(View view){
        eteFlter = (EditText) myDialog.findViewById(R.id.eteSifraArtikla);
        filter_text = eteFlter.getText().toString();
        artikli =  readDatabaseAllArtikli();
        artikliResultsFilter.clear();


         for(OsnovnoSredstvo item:artikli){
            //if(filter_text != null || filter_text !=""){
                if(item.getLokacija().equals(filter_text)){
                    artikliResultsFilter.add(item);
                }
         }
        if(artikliResultsFilter.size()>0) {
            adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikliResultsFilter, nextActivity);
            listView.setAdapter(adapter);
            filtered = 1;
        }else{
            adapter = new ArtikliAdapter(ArtikliActivityBt.this, R.layout.item_layout, artikli, nextActivity);
            listView.setAdapter(adapter);
            filtered = 0;
        }

        myDialog.dismiss();

    }

    public void dodajArtikal(MaterialSearchView.OnQueryTextListener view){

        CharSequence colors[] = new CharSequence[]{"Da", "Ne"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ArtikliActivityBt.this);
        builder.setTitle("Želite li da dodate novi artikal");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                        goToArtikalDodavanje();
                } else {

                }
            }
        });
        builder.show();


    }






}