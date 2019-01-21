package com.example.dragovicd.popis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragovicd.popis.bluetoothConnect.BluetoothConnectActivity;
import com.example.dragovicd.popis.entity.OsnovnoSredstvo;

import java.io.File;
import java.util.List;


public class ArtikliAdapter extends ArrayAdapter<OsnovnoSredstvo> {

    Context mCtx;
    int listLayoutRes;
    List<OsnovnoSredstvo> artikliList;
    DatabaseHelper db;
    String nextActivity;

    public ArtikliAdapter(Context mCtx, int listLayoutRes, List<OsnovnoSredstvo> artikliList, String nextActivity) {
        super(mCtx, listLayoutRes, artikliList);
        this.nextActivity = nextActivity;

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.artikliList = artikliList;
        db = new DatabaseHelper(mCtx);
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        OsnovnoSredstvo artikal = artikliList.get(position);
        TextView textViewSifra = view.findViewById(R.id.sifra);
        TextView textViewNaziv = view.findViewById(R.id.naziv);
        TextView textViewLokacija = view.findViewById(R.id.lokacija);
        TextView textViewLice = view.findViewById(R.id.lice);

        textViewSifra.setText(artikal.getSifra());
        textViewNaziv.setText(artikal.getNaziv());
        textViewLokacija.setText(artikal.getLokacija_naziv());
        textViewLice.setText(artikal.getOdgovorno_lice_ime());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        String pathToPicture = extStorageDirectory+"/osnovna_sredstva/images/"+artikal.getSifra()+".JPG";

        File myFile = new File(pathToPicture);
        if (myFile.exists()) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(pathToPicture));
        }
        final String artikal_sifra = artikal.getSifra();
        final String popisan = artikal.getPopisan();
        //final int y = Integer.valueOf(popisan);
        if (artikal.getPopisan() != null){
            if (Integer.parseInt(artikal.getPopisan()) == 1) {
                view.setBackgroundColor(Color.GREEN);
            }
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(final View view) {
                CharSequence colors[] = new CharSequence[]{"Uredi", "Obriši"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Artikal šifra: "+ artikal_sifra);
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            goToArtikliUredjivanje(view, artikal_sifra );
                        } else {
                            db.deleteArtikal(artikal_sifra);
                            if(nextActivity.equals("3")){
                                goToArtikliAllList(view);
                            }else{
                                goToBluetoothArtikliAllList(view);
                            }

                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        return view;
    }

    public void goToArtikliAllList(View v) {
        Intent intent = new Intent(mCtx, ArtikliActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }

    public void goToBluetoothArtikliAllList(View v) {
        Intent intent = new Intent(mCtx, BluetoothConnectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }

    public void goToArtikliUredjivanje(View v, String artikal_sifra) {
        Intent intent = new Intent(mCtx, ArtikliUredjivanje.class);
        intent.putExtra("STRING_I_NEED", artikal_sifra);
        intent.putExtra("NEXT_ACTIVITY", nextActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }


}
