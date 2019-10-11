package com.example.aleksander.fasteffect.FragmentClass;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AddProductActivity;
import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.AuxiliaryClass.DataHolder;
import com.example.aleksander.fasteffect.AuxiliaryClass.ResizeListView;
import com.example.aleksander.fasteffect.LoginActivity;
import com.example.aleksander.fasteffect.MainActivity;
import com.example.aleksander.fasteffect.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseFragment extends Fragment {


    TextView textViewData;
    ListView listViewSniadanie;
    Calendar calendarDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public HouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_house, container, false);

        Button button = (Button) view.findViewById(R.id.loginform);
        ImageButton buttonAddProduct = (ImageButton) view.findViewById(R.id.buttonAddProduct);
        ListView listViewSniadanie = (ListView) view.findViewById(R.id.ListViewSniadanie);
        textViewData = (TextView) view.findViewById(R.id.textViewData);


    /*    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.edit().commit();
        String data = prefs.getString("wartoscOdlozona", "");*/


        if (DataHolder.getInstance().getData() == "") {
            setDate();
        } else
            textViewData.setText(DataHolder.getInstance().getData());


        Intent i = getActivity().getIntent();
        String message = i.getStringExtra("value");


        textViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarDate = Calendar.getInstance();
                int day = calendarDate.get(Calendar.DAY_OF_MONTH);
                int month = calendarDate.get(Calendar.MONTH);
                int year = calendarDate.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), AlertDialog.THEME_HOLO_LIGHT,
                        mDateSetListener,
                        year, month, day);
                //    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                dialog.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;


                // SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                //  String date = formatter.format(day+"."+mon);

                String monthString = String.valueOf(month);
                String dayString = String.valueOf(day);
                if (month < 10) {

                    monthString = "0" + month;
                }
                if (day < 10) {

                    dayString = "0" + day;
                }
                String dateSend = (dayString + "-" + monthString + "-" + year);
                textViewData.setText(dayString + "-" + monthString + "-" + year);
                addToDatabse(dateSend);


               SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("DataSend", dateSend); //InputString: from the EditText
                editor.commit();


                DataHolder.getInstance().setData(dateSend);


            }
        };


        /*if (i.hasExtra("value")) {

            ArrayList<String> lista= new ArrayList<String>();

           lista.add(message);



            //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(

                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    lista

            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    // Get the Item from ListView

                    Toast.makeText(view.getContext(),String.valueOf(position) , Toast.LENGTH_SHORT).show();


                    View view = super.getView(position, convertView, parent);


                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextColor(Color.GRAY);
                    return view;
                }
            };
            listViewSniadanie.setAdapter(listViewAdapter);

        } else {

        }*/
        ResizeListView resizeListView = new ResizeListView();
        resizeListView.resize(listViewSniadanie);


        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddProduct = new Intent(getContext(), AddProductActivity.class);
                startActivity(AddProduct);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("PoraDnia", "1"); //InputString: from the EditText
                editor.commit();


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
            }
        });


        return view;
    }

    public void setDate() {
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//formating according to my need
        String date = formatter.format(today);
        textViewData.setText(date);
        String dateSend = date;
        addToDatabse(dateSend);
        DataHolder.getInstance().setData(dateSend);

// zapisujemy date
     /*   SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("wartosc", dateSend); //InputString: from the EditText
        editor.commit();*/

    }


    public void addToDatabse(String dateSend) {
        //    zapis do bazy

        BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();

        // SqlDatabase dbEntry = new SqlDatabase(getActivity());

        SQLiteDatabase baza = getActivity().openOrCreateDatabase(bazaDanychStruktura.BazaPlik, android.content.Context.MODE_PRIVATE, null);
        ContentValues rekord = new ContentValues();
        rekord.put(bazaDanychStruktura.BazaTabelaData, dateSend);
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Dzien'(Data NUMERIC PRIMARY KEY)");
        baza.insert(bazaDanychStruktura.TabelaDzien, null, rekord);
        baza.close();


    }


}
