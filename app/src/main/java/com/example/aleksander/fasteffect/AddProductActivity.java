package com.example.aleksander.fasteffect;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.AuxiliaryClass.DataHolder;
import com.example.aleksander.fasteffect.AuxiliaryClass.Produkt;
import com.example.aleksander.fasteffect.AuxiliaryClass.ResizeListView;
import com.example.aleksander.fasteffect.FragmentClass.HouseFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    public Double kalorie;
    public Double białko;
    public Double tłuszcze;
    public Double węglowodany;
    public Double błonnik;
    public String nietolerancje;
    public String nazwaProdukt;
    String cciagNazwa;

    String dateOpen;
    String poraDnia;

    DatabaseReference databaseReference;
    ListView listViewProdukty;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    String clickItem;

    int wartoscGram = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_main);

        HouseFragment asd = new HouseFragment();




        databaseReference = FirebaseDatabase.getInstance().getReference("Produkty");

        TextView textViewBack = (TextView) findViewById(R.id.textViewOpis);
        Button buttontest = (Button) findViewById(R.id.buttonTest);
        EditText editTextFilter = (EditText) findViewById(R.id.editTextSearch);
        //  final int[] SelectedItem = new int[1];


        dateOpen = DataHolder.getInstance().getData();
        SharedPreferences prefsPoraDnia = PreferenceManager.getDefaultSharedPreferences(this);
        poraDnia = prefsPoraDnia.getString("PoraDnia", "no id"); //no id: default value



       // addToDatabse(poraDnia);

        Toast.makeText(this, poraDnia, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, dateOpen, Toast.LENGTH_SHORT).show();

        listViewProdukty = (ListView) findViewById(R.id.listViewProdukty);


        listViewProdukty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final EditText input = new EditText(AddProductActivity.this);
                //   SelectedItem[0] = i;

                final String selectedItem = (String) adapterView.getItemAtPosition(i);


                //    Toast.makeText(AddProductActivity.this, selectedItem, Toast.LENGTH_SHORT).show();



                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     /*   Produkt produkt = new Produkt();

                        String value = dataSnapshot.getValue(Produkt.class).toString();
                        produkt.getNietolerancje();*/

                   //     String abd = dataSnapshot.child(cciagNazwa).child("Kalorie").getValue().toString();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



             //   Toast.makeText(AddProductActivity.this,String.valueOf(nazwaProdukt), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this, R.style.Dialog);

                builder.setTitle("Ile gram produktu chcesz wybrać?");
                builder.setCancelable(true);


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //    AddProductActivity.this.finish();



                        int ilosc  = Integer.valueOf(input.getText().toString()) ;

                        String iloscValue = String.valueOf(ilosc);

                        addProductToDatabase(selectedItem,iloscValue);
                        addToDatabse(dateOpen,poraDnia,selectedItem ,ilosc);


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

                //  alertDialog.setTitle("asd");
                alertDialog.show();

                //       final AlertDialog dialog = new AlertDialog.Builder(AddProductActivity.this);


               /* final Dialog dialog = new Dialog(AddProductActivity.this,R.style.Dialog);

                dialog.setTitle("Enter An Administrative Password");
                dialog.show();*/


                //    addToList(selectedItem);


            }

        });


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.GRAY);


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

                (AddProductActivity.this).arrayAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(Produkt.class).toString();


              /*    clickItem = (dataSnapshot.getKey()).toString() + value;


              Toast.makeText(AddProductActivity.this,  value, Toast.LENGTH_SHORT).show();
                int indexNietolerancje = value.indexOf("Nietolerancje");
                int znakNietolerancje = value.indexOf(":",indexNietolerancje);


               Toast.makeText(AddProductActivity.this, valueReduce, Toast.LENGTH_SHORT).show();*/


                String valueReduce=value.replace("Nietolerancje:"," ");
                int indexNietolerancje = valueReduce.indexOf("Błonnik:")+13;
                String ciagNietolerancje= valueReduce.substring(indexNietolerancje);

           //   Toast.makeText(getApplicationContext(), ciagNietolerancje, Toast.LENGTH_SHORT).show();

             //   String strNew = valueReduce.substring(0, valueReduce.length()-2);
                String strNew = value;

                arrayList.add(dataSnapshot.getKey() + " " + strNew);
                arrayAdapter.notifyDataSetChanged();


                ResizeListView resizeListView = new ResizeListView();
                resizeListView.resize(listViewProdukty);


               // Toast.makeText(AddProductActivity.this, value, Toast.LENGTH_SHORT).show();

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


        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cofnij);
            }
        });


    }


    public void addToList(String addClickItem) {
      /* setContentView(R.layout.fragment_house);

        ListView listViewSniadanie = (ListView) findViewById(R.id.ListViewSniadanie);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        //  listViewSniadanie
       arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.GRAY);
                return view;
            }

        };
        listViewSniadanie.setAdapter(arrayAdapter);
        arrayList.add(addClickItem);
        arrayAdapter.notifyDataSetChanged();


        ResizeListView resizeListView = new ResizeListView();
        resizeListView.resize(listViewSniadanie);
        setContentView(R.layout.product_main);
        Intent asd = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(asd);*/

        String putValue = addClickItem;
        Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
        intent.putExtra("value", putValue);
        startActivity(intent);


    }


    public void addToDatabse(String data, String poraDnia, String produkt, Integer ilosc) {


      /* Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
       Toast.makeText(getApplicationContext(), poraDnia, Toast.LENGTH_SHORT).show();
       Toast.makeText(getApplicationContext(), produkt, Toast.LENGTH_SHORT).show();
       Toast.makeText(getApplicationContext(), ilosc.toString(), Toast.LENGTH_SHORT).show();*/





    }
    public void addProductToDatabase(String selectedItem, String ilosc) {


        double converterValue =(Double.valueOf(ilosc) /100);


     //   Toast.makeText(this, ilosc, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this,String.valueOf(converterValue) , Toast.LENGTH_SHORT).show();

     DecimalFormat df = new DecimalFormat("#.#");


        int indexWęglowodany = selectedItem.indexOf("W:")+2;
        int indexBiałko = selectedItem.indexOf("B:")+2;
        int indexTłuszcze = selectedItem.indexOf("T:")+2;
        int indexBłonnik = selectedItem.indexOf("Błonnik:")+8;
        int indexKcal = selectedItem.indexOf("kcal");
        int indexNietolerancje = selectedItem.indexOf("Nietolerancje:")+14;


        int znakWęglowodany = selectedItem.indexOf("|",indexWęglowodany);
        int znakBiałko = selectedItem.indexOf("|",indexBiałko);
        int znakTłszcze = selectedItem.indexOf("|",indexTłuszcze);
        int znakBłonnik = selectedItem.indexOf("|",indexBłonnik);
        int znakNazwaKalorie = selectedItem.indexOf("|");



        String ciagWęglowodany= selectedItem.substring(indexWęglowodany,znakWęglowodany);
        String ciagBiałko= selectedItem.substring(indexBiałko,znakBiałko);
        String ciagTłszcze= selectedItem.substring(indexTłuszcze,znakTłszcze);
        String ciagBłonnik= selectedItem.substring(indexBłonnik, znakBłonnik);
        String ciagNietolerancje= selectedItem.substring(indexNietolerancje);


        String ciagNazwa= selectedItem.substring(0,znakNazwaKalorie);
        String ciagKalorie= selectedItem.substring(znakNazwaKalorie+1,indexKcal);

        kalorie = (Double.valueOf(ciagKalorie)*converterValue);
        białko= Double.valueOf(ciagBiałko)*converterValue;
        tłuszcze= Double.valueOf(ciagTłszcze)*converterValue;
        węglowodany= Double.valueOf(ciagWęglowodany)*converterValue;
        błonnik= Double.valueOf(ciagBłonnik)*converterValue;

        String kalorieS = (df.format(kalorie)).replace("," , ".");
        String białkoS = (df.format(białko)).replace("," , ".");
        String tłuszczeS = (df.format(tłuszcze)).replace("," , ".");
        String węglowodanyS = (df.format(węglowodany)).replace("," , ".");
        String błonnikS = (df.format(błonnik)).replace("," , ".");




      //  Toast.makeText(getApplicationContext(), kalorie.toString(), Toast.LENGTH_SHORT).show();
  /*     Toast.makeText(getApplicationContext(), białkoS, Toast.LENGTH_SHORT).show();
       Toast.makeText(getApplicationContext(), tłuszczeS, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), błonnikS, Toast.LENGTH_SHORT).show();*/
     /*   Toast.makeText(getApplicationContext(), ciagNazwa, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), ciagNietolerancje, Toast.LENGTH_SHORT).show(); */


        BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();

        // SqlDatabase dbEntry = new SqlDatabase(getActivity());

       SQLiteDatabase baza = openOrCreateDatabase(bazaDanychStruktura.BazaPlik, android.content.Context.MODE_PRIVATE, null);
        ContentValues rekordProdukt = new ContentValues();
        ContentValues rekordHash = new ContentValues();
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaNazwa, ciagNazwa.toString());
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaBialko ,białkoS );
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaWeglowodany , węglowodanyS);
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaTluszcze  ,tłuszczeS);
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaBłonnik  , błonnikS);
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaKalorie   , kalorieS);
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaNietolerancje  , ciagNietolerancje.toString());
        rekordProdukt.put(bazaDanychStruktura.BazaTabelaIlość   ,Integer.valueOf( ilosc));


        rekordHash.put(bazaDanychStruktura.Hash_idPoradnia,Integer.valueOf(poraDnia));

        rekordHash.put(bazaDanychStruktura.Hash_Data,dateOpen);



        String kolumny[]={bazaDanychStruktura.BazaTabelaidPosilek,bazaDanychStruktura.BazaTabelaNazwa, bazaDanychStruktura.BazaTabelaIlość};
        try {

            String where = "Nazwa=? AND Ilość=?";
            String[] args = {ciagNazwa, ilosc};
            Cursor k = baza.query(bazaDanychStruktura.TabelaPosilek, kolumny, where, args, null, null, null);
            k.moveToFirst();
            Toast.makeText(getApplicationContext(), k.getString(0)+ ", " +   k.getString(1)+ ", " + k.getString(2), Toast.LENGTH_SHORT).show();

            rekordHash.put(bazaDanychStruktura.Hash_idPosilek    ,Integer.valueOf( k.getString(0)));
            baza.insert(bazaDanychStruktura.TabelaHash, null, rekordHash);


            k.close();


        }
        catch(Exception ex) {
            baza.execSQL("CREATE TABLE IF NOT EXISTS 'Posilek'(Data NUMERIC PRIMARY KEY)");
            baza.execSQL("CREATE TABLE IF NOT EXISTS 'Hash'( Data NUMERIC NOT NULL, idPosilek INTEGER NOT NULL,idPoraDnia INTEGER NOT NULL,  CONSTRAINT fk_Data FOREIGN KEY(Data) REFERENCES Dzien(Data),CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek),CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))");
            baza.insert(bazaDanychStruktura.TabelaPosilek, null, rekordProdukt);

            String where = "Nazwa=? AND Ilość=?";
            String[] args = {ciagNazwa, ilosc};
            Cursor k = baza.query(bazaDanychStruktura.TabelaPosilek, kolumny, where,args , null, null, null);
            k.moveToFirst();
            Toast.makeText(getApplicationContext(), k.getString(0), Toast.LENGTH_SHORT).show();
            rekordHash.put(bazaDanychStruktura.Hash_idPosilek    ,Integer.valueOf( k.getString(0)));
            baza.insert(bazaDanychStruktura.TabelaHash, null, rekordHash);
            k.close();

            baza.close();


        }
        baza.close();


    }


}



