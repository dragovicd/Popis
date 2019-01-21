package com.example.dragovicd.popis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragovicd.popis.bluetoothConnect.BluetoothConnectActivity;
import com.example.dragovicd.popis.entity.OsnovnoSredstvo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.io.FileWriter;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {
    Button btnImportExport;
    private Handler handler = new Handler();
    XMLPullParserHandlerArtikli parser;
    ArrayAdapter<OsnovnoSredstvo> adapter;
    public DatabaseHelper db;
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    public static TextView  data;
    List<String[]> scoreList = null;
    ProgressBar progressBar;
    TextView brrecourds;
    AlertDialog.Builder builder;
    CSVExporter cSVExporter;
    FileWriter writer;
    List<OsnovnoSredstvo> artikli;


    String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
    String f_popis = "/osnovna_sredstva";
    String f_popis_export = f_popis + "/export";
    String f_popis_import = f_popis + "/import";
    String f_popis_images = f_popis + "/images";
    String nextActivity = "3";



    String file_name_import="/sredstva_d.csv";
    String file_name_export="/sredstva_d.csv";
    int i;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Odaberite opciju");
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        db = new DatabaseHelper(this);

        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo("com.generalscan.sdk", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        //if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE }, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.VIBRATE}, PERMISSION_REQUEST_CODE);

            File fldPopis = new File(extStorageDirectory + f_popis);
            fldPopis.mkdir();

            File fldPopisExport = new File(extStorageDirectory + f_popis_export);
            fldPopisExport.mkdir();

            File fldPopisImport = new File(extStorageDirectory + f_popis_import);
            fldPopisImport.mkdir();

            File fldPopisImages= new File(extStorageDirectory + f_popis_images);
            fldPopisImages.mkdir();

    }

    public  void readXmlFromFolder(View v){
        listView = (ListView) findViewById(R.id.listView);
        List<OsnovnoSredstvo> artikli = null;
        String fileName = "sredstva_k.csv";
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pathDir = baseDir + "/osnovna_sredstva/import/";
        File f = new File(pathDir + File.separator + fileName);
        InputStream inputStream_folder = null;
        try {
            inputStream_folder = new FileInputStream(f);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            parser = new XMLPullParserHandlerArtikli();
            inputStream_folder = getAssets().open("artikli.xml");
            artikli = parser.parse(inputStream_folder);

            //creating the adapter object
            adapter = new ArtikliAdapter(this, R.layout.item_layout, artikli, nextActivity);
            listView.setAdapter(adapter);
        }catch(IOException e) {
            e.printStackTrace();
        };
    }

    public  void readXmlFromResource(View v) {

        //listView = (ListView) findViewById(R.id.listView);
        //dbItemArrayAdapter = new DbItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        //Parcelable state = listView.onSaveInstanceState();
        //listView.setAdapter(dbItemArrayAdapter);
        //listView.onRestoreInstanceState(state);
        //InputStream inputStream_assets = null;
        //try {
        //inputStream_assets = getAssets().open("artikli.xml");
        //} catch (IOException e) {
        //e.printStackTrace();
        //}
        //XMLPullParserHandlerArtikli csvFile = new XMLPullParserHandlerArtikli();
        //List<Artikal> scoreList = null;
        //try {
        //scoreList = csvFile.parse(inputStream_assets);
        //} catch (UnsupportedEncodingException e) {
        //e.printStackTrace();
        //}

        //for(Artikal scoreData:scoreList ) {
        //dbItemArrayAdapter.add(scoreData);
        //}
    }

    public  void readXmlFromAssets(View v){
        listView = (ListView) findViewById(R.id.listView);
        List<OsnovnoSredstvo> artikli = null;
        InputStream inputStream_assets = null;
        try {
            parser = new XMLPullParserHandlerArtikli();
            inputStream_assets = getAssets().open("artikli.xml");
            artikli = parser.parse(inputStream_assets);

            //creating the adapter object
            adapter = new ArtikliAdapter(this, R.layout.item_layout, artikli, nextActivity);
            listView.setAdapter(adapter);
        }catch(IOException e) {
            e.printStackTrace();
        };


    }

    public void readDatabaseAllArtikli(View v){


        List <OsnovnoSredstvo> artikli= db.getAllArtikal();
        listView = (ListView) findViewById(R.id.listView);


        //creating the adapter object
        adapter = new ArtikliAdapter(this, R.layout.item_layout, artikli, nextActivity);
        Intent intent = new Intent(this, MainActivity.class);

        db.close();


        //adding the adapter to listview
        listView.setAdapter(adapter);

    }

    public  void readCsvFromFolder(View v){

        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        File f = new File(extStorageDirectory + f_popis_import + file_name_import);
        InputStream inputStream_folder = null;

        if(f.exists()){
            db.onDropTableArtikli();
            try {
                inputStream_folder = new FileInputStream(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CSVFile csvFile = new CSVFile(inputStream_folder);

            try {
                scoreList = csvFile.read();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            brrecourds = (TextView) findViewById(R.id.brrecourds);
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setMax(scoreList.size());
            i = 0;

            final String[] sifra = {""};
            final String[] naziv_os = {""};
            final String[] lokacija_naziv = {""};
            final String[] odgovorno_lice_ime = {""};

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (String[] scoreData:scoreList) {


                        if(scoreData[0]==""){
                            sifra[0] = "";
                        }else{
                            sifra[0] =scoreData[0];
                        }


                        if(scoreData[1]==""){
                            naziv_os[0] = "";
                        }else{
                            naziv_os[0] =scoreData[1];
                        }


                        if(scoreData[2]==""){
                            lokacija_naziv[0] = "";
                        }else{
                            lokacija_naziv[0] =scoreData[2];
                        }

                        if(scoreData[3] ==""){
                            odgovorno_lice_ime[0] = "";
                        }else{
                            odgovorno_lice_ime[0] =scoreData[3];
                        }




                        OsnovnoSredstvo artikal = new OsnovnoSredstvo(0, sifra[0], naziv_os[0], "", lokacija_naziv[0],"0","0","0","0", odgovorno_lice_ime[0],"0","0","0","0","0","");
                        db.insertArtikal(artikal);

                        //Try to sleep the thread for 20 milliseconds
                        try{
                            Thread.sleep(20);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }

                        //Update the progress bar
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(i);
                                brrecourds.setText(i+"/"+scoreList.size());
                            }
                        });

                        i++;
                    }


                }
            });
            thread.start();

        }else{
            Toast.makeText(this, "Podaci za inport ne postoje!", Toast.LENGTH_SHORT).show();
        }
        f.delete();

    }

    public  void readCsvFromResource(View v){
        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);


        InputStream inputStream_resource = getResources().openRawResource(R.raw.sredstva_k);
        CSVFile csvFile = new CSVFile(inputStream_resource);
        List<String[]> scoreList = null;
        try {
            scoreList = csvFile.read();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for(String[] scoreData:scoreList ) {
            itemArrayAdapter.add(scoreData);
        }
    }

    public  void readFromCsvAssets(View v){
        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);
        InputStream inputStream_assets = null;


        try {
            inputStream_assets = getAssets().open("sredstva_k.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVFile csvFile = new CSVFile(inputStream_assets);
        List<String[]> scoreList = null;
        try {
            scoreList = csvFile.read();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for(String[] scoreData:scoreList ) {
            itemArrayAdapter.add(scoreData);
        }
    }

    public  void readFromJSON_URL(View v){
        JsonParser process = new JsonParser();
        process.execute();
    }

    public void goToArtikliAllList(View v) {
        Intent intent = new Intent(MainActivity.this, ArtikliActivity.class);
        startActivity(intent);
    }

    public void goToArtikliAllListBt(View v) {
        Intent intent = new Intent(MainActivity.this, ArtikliActivityBt.class);
        startActivity(intent);
    }

    public void goToBarkod(View v) {
        Intent intent = new Intent(MainActivity.this, BluetoothConnectActivity.class);
        startActivity(intent);
    }

    public void goToArtikliDodavanje(View v) {
        Intent intent = new Intent(MainActivity.this, ArtikliDodavanje.class);
        intent.putExtra("SIFRA_ARTIKLA", "");
        intent.putExtra("NEXT_ACTIVITY", "1");
        startActivity(intent);
    }

    public void exportCSV(View v){
        cSVExporter = new CSVExporter();
        artikli = db.exportDataToCsv();
        String CSVFileLink = extStorageDirectory + f_popis_export + file_name_export;
        File CSVFile = new File(CSVFileLink);


            try {
                writer = new FileWriter(CSVFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


            brrecourds = (TextView) findViewById(R.id.brrecourds);
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setMax(artikli.size());
            i = 0;

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (OsnovnoSredstvo artikal : artikli) {

                        try {
                            if(i == 0){
                                cSVExporter.writeLine(writer, Arrays.asList("Sifra OS", "Nayiv OS", "Odgovorno lice aktivno", "O lice staro", "Odjel aktivni", "Odjel stari"));

                            }
                            cSVExporter.writeLine(writer, Arrays.asList(artikal.getSifra(), artikal.getNaziv(), artikal.getOdgovorno_lice_ime(), artikal.getOdgovorno_lice_ime_stara(), artikal.getLokacija(), artikal.getLokacija_stara()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Update the progress bar
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(i);
                                brrecourds.setText(i + "/" + artikli.size());
                            }
                        });

                        i++;
                    }

                    try {
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            thread.start();





    }

    public void createFolder(String fname){
        String myfolder=Environment.getExternalStorageDirectory()+"/"+fname;
        File f=new File(myfolder);
        if(!f.exists())
            if(!f.mkdir()){
                Toast.makeText(this, myfolder+" can't be created.", Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(this, myfolder+" can be created.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, myfolder+" already exits.", Toast.LENGTH_SHORT).show();
    }


}
