package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AuxiliaryClass.DataHolder;
import com.example.aleksander.fasteffect.AuxiliaryClass.Produkt;
import com.example.aleksander.fasteffect.AuxiliaryClass.ResizeListView;
import com.example.aleksander.fasteffect.FragmentClass.HouseFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listViewProdukty;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    String clickItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_main);

        HouseFragment asd = new HouseFragment();

        String dateOpen = DataHolder.getInstance().getData();
        Toast.makeText(this, dateOpen, Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Produkty");

        TextView textViewBack = (TextView) findViewById(R.id.textViewOpis);
        Button buttontest = (Button) findViewById(R.id.buttonTest);
        EditText editTextFilter = (EditText) findViewById(R.id.editTextSearch);
        //  final int[] SelectedItem = new int[1];









        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String data = prefs.getString("wartosc", "no id"); //no id: default value
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();*/


        listViewProdukty = (ListView) findViewById(R.id.listViewProdukty);


        listViewProdukty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //   SelectedItem[0] = i;
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                //   Toast.makeText(AddProductActivity.this, selectedItem, Toast.LENGTH_SHORT).show();

                addToList(selectedItem);


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
                //  clickItem = (dataSnapshot.getKey()).toString() + value;

                arrayList.add(dataSnapshot.getKey() + " " + value);
                arrayAdapter.notifyDataSetChanged();


                ResizeListView resizeListView = new ResizeListView();
                resizeListView.resize(listViewProdukty);

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

    public void addToDatabse() {

    }


}



