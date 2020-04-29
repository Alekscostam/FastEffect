package com.example.aleksander.fasteffect.FragmentClass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.R;

import java.io.File;
import java.io.FileOutputStream;

public class ExportFragment extends Fragment {

    Button buttonExport;


    public ExportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_export, container, false);

        buttonExport =  view.findViewById(R.id.d_export);
        final BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();
        final SQLiteDatabase baza = getActivity().openOrCreateDatabase(BazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);

        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor pp = baza.rawQuery("SELECT Hash.Data,PoraDnia.Pora ,Posilek.Nazwa, Posilek.Kalorie, Posilek.Ilość, Posilek.Bialko, Posilek.Weglowodany, Posilek.Tluszcze, Posilek.Błonnik FROM Hash, Posilek,PoraDnia WHERE Hash.idPosilek=Posilek.idPosilek AND Hash.idPoraDnia=PoraDnia.idPoraDnia;", null);
                pp.moveToFirst();

                StringBuilder data = new StringBuilder();

                data.append("Data, Pora dnia, Nazwa, Kalorie, Ilość, Białko, Weglowodany, Tłuszcze, Błonnik");
                for (int i = 0; i < pp.getCount(); i++) {

                    String message = (pp.getString(0) + ", " + pp.getString(1) + ", " + pp.getString(2) + ", " + pp.getString(3) + ", " + pp.getString(4) + ", " + pp.getString(5) + ", " + pp.getString(6) + ", " + pp.getString(7) + ", " + pp.getString(8));
                    data.append("\n" + message);
                    pp.moveToNext();

                }

                try {

                    FileOutputStream outputStream = getActivity().openFileOutput("Dziennik", Context.MODE_PRIVATE);
                    outputStream.write((data.toString()).getBytes());
                    outputStream.close();
                    Context context = getActivity().getApplicationContext();
                    File filelocation = new File(getActivity().getFilesDir(), "data");

                    Uri path = FileProvider.getUriForFile(context, "com.example.aleksander.fasteffect.fileprovider", filelocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/*");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Dziennik");
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "send or save your food diary"));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;


    }




}
