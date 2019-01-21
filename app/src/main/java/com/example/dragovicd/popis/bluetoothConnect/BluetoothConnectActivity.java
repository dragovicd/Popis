package com.example.dragovicd.popis.bluetoothConnect;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragovicd.popis.ArtikliAdapter;
import com.example.dragovicd.popis.ArtikliDodavanje;
import com.example.dragovicd.popis.DatabaseHelper;
import com.example.dragovicd.popis.R;
import com.example.dragovicd.popis.entity.OsnovnoSredstvo;
import com.generalscan.NotifyStyle;
import com.generalscan.OnConnectedListener;
import com.generalscan.OnDisconnectListener;
import com.generalscan.SendConstant;
import com.generalscan.bluetooth.BluetoothConnect;
import com.generalscan.bluetooth.BluetoothSettings;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试界面
 *
 * @author Administrator
 */
public class

BluetoothConnectActivity extends AppCompatActivity {

    private Button myBtnBlue;
    private Button myBtnBlueDevice;
    private Button myBtnBlueConnect;
    private Button myBtnBlueStop;
    private Button myBtnSendContent;
    private Button myBtnSendList;
    private Button myBtnClear;
    private Activity myActivity;

    private ReadBroadcast mReadBroadcast;

    private ArrayAdapter<OsnovnoSredstvo> adapter;
    private TextView data;
    private DatabaseHelper db;
    private MaterialSearchView searchView;
    private ListView listView;
    private List<OsnovnoSredstvo> osnovnaSredstva;
    private List<OsnovnoSredstvo> osnovnoSredstvoResults;
    private List<OsnovnoSredstvo> osnovnoSredstvoResultsFilter;
    private Dialog myDialog;
    private String filter_text ="";
    private EditText eteFlter;
    private int result = 0;
    TextView bateryStatus;
    private String message = "";
 
    private String osnovnoSredstvoSifraBk = "";
    private String sifraArtikla = "";
    private String odjelNaziv = "";
    private String nazivArtikla = "";

    /**
     * Called when the activity is first created.
     */
    private int filtered = 0;
    private String nextActivity = "2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.bluetooth_main);
        myActivity = BluetoothConnectActivity.this;
        BluetoothConnect.CurrentNotifyStyle = NotifyStyle.NotificationStyle1;
        // Bind Bluetooth Service(Must bind service before start)
        BluetoothConnect.BindService(myActivity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Osnovna sredstva");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //bateryStatus = (TextView) findViewById(R.id.bateryStatus);



        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //final SendList sendList = new SendList(this);
        /*fab.setOnClickListener(new OnClickListener() {
            @Override
             public void onClick(View view) {

                // Send one command in the command list
                // SendConstant.F_StartScan  Sart Scan Barcode



                //BluetoothSend.SendContent(BluetoothConnectActivity.this, SendConstant.F_StartScan);
                getIntent().setAction(SendConstant.GetBatteryDataAction);
                *//*String datas = getIntent().getStringExtra(SendConstant.GetBatteryData);
                ((TextView) findViewById(R.id.bateryStatus)).setText(datas);*//*

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show();
            }
        });*/


        db = new DatabaseHelper(BluetoothConnectActivity.this);
        filtered = 0;
        osnovnaSredstva = readDatabaseAllArtikli();
        myDialog = new Dialog(this);
        osnovnoSredstvoResults = new ArrayList<OsnovnoSredstvo>();
        osnovnoSredstvoResultsFilter = new ArrayList<OsnovnoSredstvo>();
        listView = (ListView) findViewById(R.id.listViewL);
        adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnaSredstva, nextActivity);
        listView.setAdapter(adapter);

        GetData();

/*        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                BluetoothConnect.Connect();
            }

        }.start();*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_itembt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.btnBlueM:
                BluetoothSettings.ACTION_BLUETOOTH_SETTINGS(myActivity);
                return true;
            case R.id.btnSetM:
                BluetoothSettings.SetScaner(myActivity);
                return true;
            case R.id.btnConnectM:
                BluetoothConnect.Connect();
                return true;
            case R.id.btnStopM:
                BluetoothConnect.Stop(myActivity);
                return true;

            case R.id.btnSendContentM:
                String text = ((EditText) findViewById(R.id.editText2))
                        .getText().toString();
                BluetoothConnect.BluetoothSend(text);
                return true;

            case R.id.btnStartScanM:
                BluetoothSendList SendList = new BluetoothSendList(myActivity);
                SendList.ShowoDialog();
                return true;

            case R.id.btnClearM:
                ((EditText) findViewById(R.id.editText1)).setText("");
                ((TextView) findViewById(R.id.tvGetData)).setText("");
                return true;

            case R.id.filter_lokacije:
                //PrikaziFilter(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBroadcast() {
        // 设置数据广播
        mReadBroadcast = new ReadBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SendConstant.GetDataAction);
        filter.addAction(SendConstant.GetReadDataAction);
        filter.addAction(SendConstant.GetBatteryDataAction);
        registerReceiver(mReadBroadcast, filter);

    }

    public List<OsnovnoSredstvo> readDatabaseAllArtikli() {
        osnovnaSredstva = db.getAllArtikal();
        //listView = (ListView) findViewById(R.id.listView);
        //creating the adapter object
        //adapter_a = new ArtikliAdapter(ArtikliActivity.this, R.layout.item_layout, osnovnaSredstva);
        //adding the adapter to listview
        //listView.setAdapter(adapter_a);
        return osnovnaSredstva;
    }

    public class ReadBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(SendConstant.GetBatteryDataAction)) {
                String data = intent.getStringExtra(SendConstant.GetBatteryData);
                ((EditText) findViewById(R.id.editText12)).append(data);
            }

            if (intent.getAction().equals(SendConstant.GetDataAction)) {
                char[] enterRespoundChar = (intent.getStringExtra(SendConstant.GetData)).toCharArray();
                final int enterRespound = (int) (enterRespoundChar[0]);
                //Toast.makeText(context, enterRespound +"", Toast.LENGTH_SHORT).show();
                if ((enterRespound == 13 || enterRespound == 10) && !osnovnoSredstvoSifraBk.equals("")) {
                    result = 0;
                    for(OsnovnoSredstvo item:osnovnaSredstva){
                        // Toast.makeText(context, item.getSifra()+"/"+osnovnoSredstvoSifraBk, Toast.LENGTH_SHORT).show();

                        if((item.getLokacija()).equals(osnovnoSredstvoSifraBk)){
                            result = 3;
                        }else if((item.getSifra()).equals(osnovnoSredstvoSifraBk)){
                            sifraArtikla = item.getSifra();
                            odjelNaziv = item.getLokacija();
                            nazivArtikla = item.getNaziv();
                            if(item.getPopisan().equals("1")){
                                if(filtered==0){
                                    message =  "Proizvod je vec popisan " + nazivArtikla;
                                    result = 1;
                                }else if (filtered==1 && filter_text.equals(item.getLokacija())){
                                    message =  "Proizvod je vec popisan " + nazivArtikla;
                                    result = 1;
                                }else if (filtered==1 && !filter_text.equals(item.getLokacija())){
                                    message =  "Proizvod je vec popisan ali ne pripada ovoj lokaciji " + nazivArtikla;
                                    result = 1;
                                }
                            }else{
                                if(filtered==0 ){
                                    message = "Popisali ste proizvod " + nazivArtikla;
                                    osnovnoSredstvoResults.add(item);
                                    result = 2;
                                }else if(filtered==1 && filter_text.equals(item.getLokacija())){
                                    message = "Popisali ste proizvod " + nazivArtikla;
                                    osnovnoSredstvoResults.add(item);
                                    result = 2;
                                }else if (filtered==1 && !filter_text.equals(item.getLokacija())){
                                    message = "Proizvod "+nazivArtikla+" ne pripada ovoj lokaciji";
                                    String staraLokacija = item.getLokacija();
                                    String novaLokacija = filter_text;
                                    popisiArtikalIzvanListe(this, sifraArtikla, staraLokacija, novaLokacija);
                                    result = 4;
                                }
                            }
                        }
                    }

                    if(result == 2 && filtered==0){
                        osnovnaSredstva.clear();
                        db.popisArtikal(sifraArtikla);
                        osnovnaSredstva =  readDatabaseAllArtikli();
                        adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnaSredstva, nextActivity);
                        listView.setAdapter(adapter);

                    }else if (result == 2 || result == 4 && filtered==1 ){
                        osnovnaSredstva.clear();
                        osnovnoSredstvoResultsFilter.clear();
                        db.popisArtikal(sifraArtikla);
                        osnovnaSredstva =  readDatabaseAllArtikli();
                        for(OsnovnoSredstvo item:osnovnaSredstva){
                            if(item.getLokacija().equals(filter_text)){
                                osnovnoSredstvoResultsFilter.add(item);
                            }
                        }
                        adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnoSredstvoResultsFilter, nextActivity);
                        listView.setAdapter(adapter);
                    }else if(result == 0){
                        OsnovnoSredstvo noviProizvod = new OsnovnoSredstvo();
                        noviProizvod.setSifra(osnovnoSredstvoSifraBk);
                        noviProizvod.setLokacija("");
                        noviProizvod.setNaziv("");
                        if(filtered==1){
                            noviProizvod.setLokacija(filter_text);
                        }
                        message = "Proizvod nema u evidenciji " + osnovnoSredstvoSifraBk;
                        dodajArtikal(this, noviProizvod);
                    }else if(result == 3){
                        filtrirajAuto(this, osnovnoSredstvoSifraBk);
                    }


                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    message ="";
                    osnovnoSredstvoSifraBk ="";
                    sifraArtikla = "";
                    odjelNaziv = "";
                    nazivArtikla = "";

                } else {
                    if(enterRespound != 13 && enterRespound != 10) {
                        osnovnoSredstvoSifraBk = osnovnoSredstvoSifraBk + intent.getStringExtra(SendConstant.GetData);
                    }
                }


            }
            // 接收发送数据的广播
            if (intent.getAction().equals(SendConstant.GetReadDataAction)) {
                String name = intent.getStringExtra(SendConstant.GetReadName);
                String data = intent.getStringExtra(SendConstant.GetReadData);

                // 如果接受到的是充电类型
                if (name.equals(myActivity.getString(R.string.gs_read_charge))) {
                    // 获取0，1标示
                    data = data.substring(7, 8);
                    if (data.equals("0")) {
                        data = myActivity
                                .getString(R.string.gs_usb_charge_fast);

                    } else {
                        data = myActivity
                                .getString(R.string.gs_usb_charge_normal);

                    }
                    // ((EditText) findViewById(R.id.editText1)).append(name + ":"
                    //+ data);
                } else {
                    //((EditText) findViewById(R.id.editText1)).append(name + ":"
                    //+ data);
                }
            }
        }

    }

    public void goToArtikalDodavanje(OsnovnoSredstvo artikal) {
        Context context = this;
        Intent intent = new Intent(context, ArtikliDodavanje.class);
        intent.putExtra("SIFRA_ARTIKLA", artikal.getSifra());
        intent.putExtra("LOKACIJA_ARTIKLA", artikal.getLokacija());
        intent.putExtra("NEXT_ACTIVITY", "2");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        sifraArtikla = "";
    }

    public void filtriraj(View view) {
        eteFlter = (EditText) myDialog.findViewById(R.id.eteSifraArtikla);
        filter_text = eteFlter.getText().toString();
        osnovnaSredstva = readDatabaseAllArtikli();
        osnovnoSredstvoResultsFilter.clear();


        for (OsnovnoSredstvo item : osnovnaSredstva) {
            //if(filter_text != null || filter_text !=""){
            if (item.getLokacija().equals(filter_text)) {
                osnovnoSredstvoResultsFilter.add(item);
            }
        }
        if (osnovnoSredstvoResultsFilter.size() > 0) {
            adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnoSredstvoResultsFilter, nextActivity);
            listView.setAdapter(adapter);
            filtered = 1;
        } else {
            adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnaSredstva, nextActivity);
            listView.setAdapter(adapter);
            filtered = 0;
        }

        myDialog.dismiss();

    }


    public void popisuj(View view) {
        eteFlter = (EditText) myDialog.findViewById(R.id.eteSifraArtikla);
        filter_text = eteFlter.getText().toString();
        osnovnaSredstva = readDatabaseAllArtikli();
        osnovnoSredstvoResultsFilter.clear();


        for (OsnovnoSredstvo item : osnovnaSredstva) {
            //if(filter_text != null || filter_text !=""){
            if (item.getLokacija().equals(filter_text)) {
                osnovnoSredstvoResultsFilter.add(item);
            }
        }
        if (osnovnoSredstvoResultsFilter.size() > 0) {
            adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnoSredstvoResultsFilter, nextActivity);
            listView.setAdapter(adapter);
            filtered = 1;
        } else {
            adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnaSredstva, nextActivity);
            listView.setAdapter(adapter);
            filtered = 0;
        }

        myDialog.dismiss();

    }

    public void filtrirajAuto(ReadBroadcast view, final String lokacija) {

        filter_text = lokacija;
        osnovnaSredstva = readDatabaseAllArtikli();
        osnovnoSredstvoResultsFilter.clear();


        CharSequence colors[] = new CharSequence[]{"Da", "Ne"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothConnectActivity.this);
        builder.setTitle("Želite li da filtrirate lokaciju "+ lokacija);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    for (OsnovnoSredstvo item : osnovnaSredstva) {

                        if (item.getLokacija().equals(filter_text)) {
                            osnovnoSredstvoResultsFilter.add(item);
                        }
                    }

                    adapter = new ArtikliAdapter(BluetoothConnectActivity.this, R.layout.item_layout, osnovnoSredstvoResultsFilter, nextActivity);
                    listView.setAdapter(adapter);
                    filtered = 1;

                } else {
                    sifraArtikla = "";
                }
            }
        });
        builder.show();
    }

    public void popisiArtikalIzvanListe(ReadBroadcast view, final String sifraArtiklaS, final String staraLokacija, final String novaLokacija) {

        CharSequence colors[] = new CharSequence[]{"Popiši bezuslovno", "Premjesti na ovu lokaciju i popiši" ,"Otkaži"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothConnectActivity.this);
        builder.setTitle("Proizvod ne pripada ovoj lokaciji");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    db.popisArtikal(sifraArtiklaS);

                }if (which == 1) {

                    db.premjestiArtikalNaLokaciju(sifraArtiklaS, staraLokacija, novaLokacija);

                } else {
                    sifraArtikla = "";
                }
            }
        });
        builder.show();


    }

    public void dodajArtikal(ReadBroadcast view, final OsnovnoSredstvo artikalNovi) {

        CharSequence colors[] = new CharSequence[]{"Da", "Ne"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothConnectActivity.this);
        builder.setTitle("Želite li da dodate novi artikal");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    goToArtikalDodavanje(artikalNovi);

                } else {
                    sifraArtikla = "";
                }
            }
        });
        builder.show();


    }

    private void GetData() {

        BluetoothConnect.SetOnConnectedListener(new OnConnectedListener() {

            @Override
            public void Connected() {
                Toast.makeText(myActivity, "Connected", Toast.LENGTH_SHORT).show();
            }

        });

        BluetoothConnect.SetOnDisconnectListener(new OnDisconnectListener() {

            @Override
            public void Disconnected() {
                Toast.makeText(myActivity, "Disconnected", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onStart() {
         setBroadcast();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mReadBroadcast != null) {
            this.unregisterReceiver(mReadBroadcast);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        BluetoothConnect.UnBindService(myActivity);
        super.onDestroy();
    }

    public void PrikaziFilter(BluetoothConnectActivity v) {
        Button btn_zatvori;
        Button btn_filtriraj;
        myDialog.setContentView(R.layout.filter_layout);

        btn_zatvori =(Button) myDialog.findViewById(R.id.btn_zatvori);

        btn_filtriraj = (Button) myDialog.findViewById(R.id.btn_popisi);


        //final String filter_text = ((EditText)().getText().toString();

        btn_filtriraj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                filtriraj(view);
                //myDialog.dismiss();
            }
        });

        btn_zatvori.setOnClickListener(new OnClickListener() {
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
}