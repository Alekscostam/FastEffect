package com.example.aleksander.fasteffect.ProductClass;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.AuxiliaryClass.DataHolder;
import com.example.aleksander.fasteffect.AuxiliaryClass.Produkty;
import com.example.aleksander.fasteffect.AuxiliaryClass.ResizeListView;
import com.example.aleksander.fasteffect.MainActivity;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AddProductActivity extends AppCompatActivity {


    public boolean switchOnOfS;
    public boolean switchOnOfL;
    public boolean switchOnOfFOS;
    public boolean switchOnOfGOS;
    public boolean switchOnOfP;
    public boolean switchOnOfF;
    public String valueToReplace;
    public StringBuilder valueToReplaceBuilder;

    public Double kalorie;
    public Double białko;
    public Double tłuszcze;
    public Double węglowodany;
    public Double błonnik;

    Long calyczas=0l;
    String dateOpen;
    String poraDnia;

    DatabaseReference databaseReference;
    ListView listViewProdukty;
    List<String> linkedList = new LinkedList<>();
    ArrayAdapter<String> arrayAdapter;

    ImageButton imageButtoAdd;

    int wartoscGram = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Produkty");



        TextView textViewBack = findViewById(R.id.textViewOpis);
        EditText editTextFilter = findViewById(R.id.editTextSearch);

        imageButtoAdd = findViewById(R.id.buttonAdd);

        dateOpen = DataHolder.getInstance().getData();
        SharedPreferences prefsPoraDnia = PreferenceManager.getDefaultSharedPreferences(this);
        poraDnia = prefsPoraDnia.getString("PoraDnia", "no id"); //no id: default value
        listViewProdukty = findViewById(R.id.listViewProdukty);
        listViewProdukty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(AddProductActivity.this, String.valueOf(calyczas), Toast.LENGTH_SHORT).show();
                final EditText input = new EditText(AddProductActivity.this);
                final String selectedItem = (String) adapterView.getItemAtPosition(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this, R.style.Dialog);
                builder.setTitle("Ile gram produktu chcesz wybrać?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int ilosc = Integer.valueOf(input.getText().toString());
                        String iloscValue = String.valueOf(ilosc);
                        addProductToDatabase(selectedItem, iloscValue);

                        try {
                            wartoscGram = Integer.valueOf(input.getText().toString());

                        } catch (Exception e) {
                            Toast.makeText(AddProductActivity.this, "Za duża wartośc", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });

                AlertDialog alertDialog = builder.create();
                input.setTextColor(Color.GRAY);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialog.setView(input);
                alertDialog.show();
                alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);

            }

        });

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, linkedList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.rgb(72, 72, 72));
                return view;
            }

        };

        listViewProdukty.setAdapter(arrayAdapter);

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                (AddProductActivity.this).arrayAdapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {

            @Override
            public void run() {

                TestMethod();

            }


        });
        executorService.shutdown();


        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cofnij);
            }
        });

        imageButtoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cofnij = new Intent(getApplicationContext(), AddNewProductActivity.class);
                startActivity(cofnij);
            }
        });

    }




    public void TestMethod() {
        databaseReference.addChildEventListener(new ChildEventListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Long Start = System.currentTimeMillis();
                StringBuilder stringBuilderValue = new StringBuilder();

                stringBuilderValue.setLength(0);
                final List<StringBuilder> listModify = new ArrayList<>();

                stringBuilderValue.append(" "+dataSnapshot.getKey() + " ] " + dataSnapshot.getValue(Produkty.class));
                listModify.add( stringBuilderValue);

                linkedList.add(String.valueOf(listModify));
                arrayAdapter.notifyDataSetChanged();
                ResizeListView resizeListView = new ResizeListView();
                resizeListView.resize(listViewProdukty);
                Long stop = System.currentTimeMillis();
                calyczas+=stop-Start;
                System.out.println(String.valueOf(calyczas));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });

    }


    public void addProductToDatabase(String selectedItem, String ilosc) {

        double converterValue = (Double.valueOf(ilosc) / 100);
        DecimalFormat df = new DecimalFormat("#.#");

        int indexWęglowodany = selectedItem.indexOf("W:") + 2;
        int indexBiałko = selectedItem.indexOf("B:") + 2;
        int indexTłuszcze = selectedItem.indexOf("T:") + 2;
        int indexBłonnik = selectedItem.indexOf("Błonnik:") + 8;
        int indexKcal = selectedItem.indexOf("Kalorie:")+ 8;

        int znakWęglowodany = selectedItem.indexOf(".", indexWęglowodany);
        int znakBiałko = selectedItem.indexOf(".", indexBiałko);
        int znakTłszcze = selectedItem.indexOf(".", indexTłuszcze);
        int znakBłonnik = selectedItem.indexOf(".", indexBłonnik);
        int znakNazwaKalorie = selectedItem.indexOf(".");

        String ciagWęglowodany = selectedItem.substring(indexWęglowodany, znakWęglowodany);
        String ciagBiałko = selectedItem.substring(indexBiałko, znakBiałko);
        String ciagTłszcze = selectedItem.substring(indexTłuszcze, znakTłszcze);
        String ciagBłonnik = selectedItem.substring(indexBłonnik, znakBłonnik);

        String ciagNazwa = selectedItem.substring(0, indexKcal);
        String ciagKalorie = selectedItem.substring(indexKcal, znakNazwaKalorie);

        kalorie = (Double.valueOf(ciagKalorie) * converterValue);
        białko = Double.valueOf(ciagBiałko) * converterValue;
        tłuszcze = Double.valueOf(ciagTłszcze) * converterValue;
        węglowodany = Double.valueOf(ciagWęglowodany) * converterValue;
        błonnik = Double.valueOf(ciagBłonnik) * converterValue;

        String kalorieS = (df.format(Math.round(kalorie))).replace(",", ".");
        String białkoS = (df.format(białko)).replace(",", ".");
        String tłuszczeS = (df.format(tłuszcze)).replace(",", ".");
        String węglowodanyS = (df.format(węglowodany)).replace(",", ".");
        String błonnikS = (df.format(błonnik)).replace(",", ".");



        SQLiteDatabase baza = openOrCreateDatabase(BazaDanychStruktura.BazaPlik,
                android.content.Context.MODE_PRIVATE, null);
        ContentValues rekordProdukt = new ContentValues();
        ContentValues rekordHash = new ContentValues();
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaNazwa, ciagNazwa);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaBialko, białkoS);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaWeglowodany, węglowodanyS);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaTluszcze, tłuszczeS);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaBłonnik, błonnikS);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaKalorie, kalorieS);
        rekordProdukt.put(BazaDanychStruktura.BazaTabelaIlość, Integer.valueOf(ilosc));

        rekordHash.put(BazaDanychStruktura.Hash_idPoradnia, Integer.valueOf(poraDnia));
        rekordHash.put(BazaDanychStruktura.Hash_Data, dateOpen);

        String[] kolumny = {BazaDanychStruktura.BazaTabelaidPosilek, BazaDanychStruktura.BazaTabelaNazwa,
                BazaDanychStruktura.BazaTabelaIlość};

        try {
            String where = "Nazwa=? AND Ilość=?";
            String[] args = {ciagNazwa, ilosc};
            Cursor k = baza.query(BazaDanychStruktura.TabelaPosilek, kolumny, where, args, null, null, null);
            k.moveToFirst();
            rekordHash.put(BazaDanychStruktura.Hash_idPosilek, Integer.valueOf(k.getString(0)));
            baza.insert(BazaDanychStruktura.TabelaHash, null, rekordHash);
            k.close();
        } catch (Exception ex) {
            baza.insert(BazaDanychStruktura.TabelaPosilek, null, rekordProdukt);
            String where = "Nazwa=? AND Ilość=?";
            String[] args = {ciagNazwa, ilosc};
            Cursor k = baza.query(BazaDanychStruktura.TabelaPosilek, kolumny, where, args, null, null, null);
            k.moveToFirst();
            rekordHash.put(BazaDanychStruktura.Hash_idPosilek, Integer.valueOf(k.getString(0)));
            baza.insert(BazaDanychStruktura.TabelaHash, null, rekordHash);
            k.close();
            baza.close();
        }finally {
            baza.close();
        }


    }



}



