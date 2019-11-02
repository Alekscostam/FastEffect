package com.example.aleksander.fasteffect.FragmentClass;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.example.aleksander.fasteffect.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseFragment extends Fragment {


    int[] hide = {0, 0, 0, 0, 0};
    double[] addValueCalories = new double[5];
    double[] addValueProtein = new double[5];
    double[] addValueFat = new double[5];
    double[] addValueCarb = new double[5];


    TextView textViewData;

    //Sniadanie
    TextView textViewKcalS;
    TextView textViewPS;
    TextView textViewWS;
    TextView textViewTS;

    //lunch
    TextView textViewKcalL;
    TextView textViewPL;
    TextView textViewWL;
    TextView textViewTL;

    //obiad
    TextView textViewKcalO;
    TextView textViewPO;
    TextView textViewWO;
    TextView textViewTO;
    //przekasaka
    TextView textViewKcalP;
    TextView textViewPP;
    TextView textViewWP;
    TextView textViewTP;
    //kolacja
    TextView textViewKcalK;
    TextView textViewPK;
    TextView textViewWK;
    TextView textViewTK;

    TextView textViewAllCalories;
    TextView textViewAllProtein;
    TextView textViewAllCarb;
    TextView textViewAllFat;


    ListView listViewSniadanie;
    ListView listViewLunch;
    ListView listViewObiad;
    ListView listViewPrzekąska;
    ListView listViewKolacja;

    CardView cardViewSniadanie;
    CardView cardViewLunch;
    CardView cardViewObiad;
    CardView cardViewPrzekąska;
    CardView cardViewKolacja;

    ArrayList<String> listItemSniadanie;
    ArrayList<String> listItemLunch;
    ArrayList<String> listItemObiad;
    ArrayList<String> listItemPrzekąska;
    ArrayList<String> listItemKolacja;

    DatePicker myDatePicker;
    Calendar calendarDate;

    TextView textViewSniadanie;
    TextView textViewLunch;
    TextView textViewObiad;
    TextView textViewPrzekąska;
    TextView textViewKolacja;

    ArrayAdapter adapterSniadanie;
    ArrayAdapter adapterLunch;
    ArrayAdapter adapterObiad;
    ArrayAdapter adapterPrzekąska;
    ArrayAdapter adapterKolacja;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public HouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_house, container, false);

        textViewAllCalories = (TextView) view.findViewById(R.id.textViewAllCalories);
        textViewAllProtein = (TextView) view.findViewById(R.id.textViewAllProtein);
        textViewAllCarb = (TextView) view.findViewById(R.id.textViewAllCarb);
        textViewAllFat = (TextView) view.findViewById(R.id.textViewAllFat);


        textViewKcalS = (TextView) view.findViewById(R.id.KcalS);
        textViewPS = (TextView) view.findViewById(R.id.PS);
        textViewWS = (TextView) view.findViewById(R.id.WS);
        textViewTS = (TextView) view.findViewById(R.id.TS);


        textViewKcalL = (TextView) view.findViewById(R.id.KcalL);
        textViewPL = (TextView) view.findViewById(R.id.PL);
        textViewWL = (TextView) view.findViewById(R.id.WL);
        textViewTL = (TextView) view.findViewById(R.id.TL);

        textViewKcalO = (TextView) view.findViewById(R.id.KcalO);
        textViewPO = (TextView) view.findViewById(R.id.PO);
        textViewWO = (TextView) view.findViewById(R.id.WO);
        textViewTO = (TextView) view.findViewById(R.id.TO);

        textViewKcalP = (TextView) view.findViewById(R.id.KcalP);
        textViewPP = (TextView) view.findViewById(R.id.PP);
        textViewWP = (TextView) view.findViewById(R.id.WP);
        textViewTP = (TextView) view.findViewById(R.id.TP);

        textViewKcalK = (TextView) view.findViewById(R.id.KcalK);
        textViewPK = (TextView) view.findViewById(R.id.PK);
        textViewWK = (TextView) view.findViewById(R.id.WK);
        textViewTK = (TextView) view.findViewById(R.id.TK);


        listItemSniadanie = new ArrayList<>();
        listItemLunch = new ArrayList<>();
        listItemObiad = new ArrayList<>();
        listItemPrzekąska = new ArrayList<>();
        listItemKolacja = new ArrayList<>();

        myDatePicker = (DatePicker) view.findViewById(R.id.datePicker);

        textViewSniadanie = (TextView) view.findViewById(R.id.textViewSniadanie);
        textViewLunch = (TextView) view.findViewById(R.id.textViewLunch);
        textViewObiad = (TextView) view.findViewById(R.id.textViewObiad);
        textViewPrzekąska = (TextView) view.findViewById(R.id.textViewPrzekąska);
        textViewKolacja = (TextView) view.findViewById(R.id.textViewKolacja);

        cardViewSniadanie = (CardView) view.findViewById(R.id.cardViewSniadanie);
        cardViewLunch = (CardView) view.findViewById(R.id.cardViewLunch);
        cardViewObiad = (CardView) view.findViewById(R.id.cardViewObiad);
        cardViewPrzekąska = (CardView) view.findViewById(R.id.cardViewPrzekąska);
        cardViewKolacja = (CardView) view.findViewById(R.id.cardViewKolacja);

        listViewSniadanie = (ListView) view.findViewById(R.id.ListViewSniadanie);
        listViewLunch = (ListView) view.findViewById(R.id.ListViewLunch);
        listViewObiad = (ListView) view.findViewById(R.id.ListViewObiad);
        listViewPrzekąska = (ListView) view.findViewById(R.id.ListViewPrzekąska);
        listViewKolacja = (ListView) view.findViewById(R.id.ListViewKolacja);


        ImageButton buttonAddProduct = (ImageButton) view.findViewById(R.id.buttonAddProduct);
        ImageButton buttonAddProduct2 = (ImageButton) view.findViewById(R.id.buttonAddProduct2);
        ImageButton buttonAddProduct3 = (ImageButton) view.findViewById(R.id.buttonAddProduct3);
        ImageButton buttonAddProduct4 = (ImageButton) view.findViewById(R.id.buttonAddProduct4);
        ImageButton buttonAddProduct5 = (ImageButton) view.findViewById(R.id.buttonAddProduct5);
        textViewData = (TextView) view.findViewById(R.id.textViewData);

        cardViewSniadanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hide[0] == 0) {
                    showListview(listViewSniadanie);
                    hide[0] = 1;
                } else {
                    hideListview(listViewSniadanie);
                    hide[0] = 0;
                }

            }
        });
        cardViewLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hide[1] == 0) {
                    showListview(listViewLunch);
                    hide[1] = 1;
                } else {
                    hideListview(listViewLunch);
                    hide[1] = 0;
                }

            }
        });
        cardViewObiad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hide[2] == 0) {
                    showListview(listViewObiad);
                    hide[2] = 1;
                } else {
                    hideListview(listViewObiad);
                    hide[2] = 0;
                }

            }
        });
        cardViewPrzekąska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hide[3] == 0) {
                    showListview(listViewPrzekąska);
                    hide[3] = 1;
                } else {
                    hideListview(listViewPrzekąska);
                    hide[3] = 0;
                }

            }
        });
        cardViewKolacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hide[4] == 0) {
                    showListview(listViewKolacja);
                    hide[4] = 1;
                } else {
                    hideListview(listViewKolacja);
                    hide[4] = 0;
                }

            }
        });

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
                        getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                        mDateSetListener,
                        year, month, day);

                //     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//R.style.myDataPicker,
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        resetAllTextview();
                        viewDatabase();
                        sumUpEverything();
                    }
                });
                checkAdapter(adapterSniadanie, cardViewSniadanie, listViewSniadanie, textViewSniadanie);
                checkAdapter(adapterLunch, cardViewLunch, listViewLunch, textViewLunch);
                checkAdapter(adapterObiad, cardViewObiad, listViewObiad, textViewObiad);
                checkAdapter(adapterPrzekąska, cardViewPrzekąska, listViewPrzekąska, textViewPrzekąska);
                checkAdapter(adapterKolacja, cardViewKolacja, listViewKolacja, textViewKolacja);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                datePicker = myDatePicker;

                month = month + 1;
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

                resetAllTextview();
                hideAllListview();
                viewDatabase();
                sumUpEverything();


            }
        };


        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct("1");


            }
        });
        buttonAddProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct("2");


            }
        });
        buttonAddProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct("3");


            }
        });
        buttonAddProduct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct("4");


            }
        });
        buttonAddProduct5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct("5");


            }
        });


        resetAllTextview();

        hideAllListview();
        viewDatabase();
        sumUpEverything();


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

    public void viewDatabase() {
        listItemSniadanie.clear();
        adapterSniadanie = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItemSniadanie);
        listViewSniadanie.setAdapter(adapterSniadanie);

        listItemLunch.clear();
        adapterLunch = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItemLunch);
        listViewLunch.setAdapter(adapterLunch);

        listItemObiad.clear();
        adapterObiad = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItemObiad);
        listViewObiad.setAdapter(adapterObiad);

        listItemPrzekąska.clear();
        adapterPrzekąska = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItemPrzekąska);
        listViewPrzekąska.setAdapter(adapterPrzekąska);

        listItemKolacja.clear();
        adapterKolacja = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItemKolacja);
        listViewKolacja.setAdapter(adapterKolacja);

        BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();
        SQLiteDatabase baza = getActivity().openOrCreateDatabase(bazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Hash'( Data NUMERIC NOT NULL, idPosilek INTEGER NOT NULL,idPoraDnia INTEGER NOT NULL,  CONSTRAINT fk_Data FOREIGN KEY(Data) REFERENCES Dzien(Data),CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek),CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))");


        for (int i = 1; i <= 5; i++) {

            Cursor k = baza.rawQuery("SELECT Posilek.Nazwa , Posilek.Kalorie, Posilek.Bialko,Posilek.Weglowodany,Posilek.Tluszcze,Posilek.Błonnik,Posilek.Ilość " +
                    "FROM Dzien, Hash, PoraDnia,Posilek " +
                    "WHERE Hash.idPosilek=Posilek.idPosilek AND Hash.Data=Dzien.Data AND Hash.idPoraDnia = PoraDnia.idPoraDnia AND Hash.idPoraDnia= '" + i + "' AND Hash.Data = '" + textViewData.getText().toString() + "'", null);
            if (k.getCount() == 0) {
                //  Toast.makeText(getContext(), "Brak danycyh", Toast.LENGTH_SHORT).show();
            } else {
                if (i == 1) {

                    int a = 0;
                    pickDinner(k, listItemSniadanie, adapterSniadanie, listViewSniadanie, cardViewSniadanie, textViewSniadanie, a, a);


                }
                if (i == 2) {

                    int a = 1;
                    pickDinner(k, listItemLunch, adapterLunch, listViewLunch, cardViewLunch, textViewLunch, a, a);


                }
                if (i == 3) {
                    int a = 2;
                    pickDinner(k, listItemObiad, adapterObiad, listViewObiad, cardViewObiad, textViewObiad, a, a);


                }
                if (i == 4) {
                    int a = 3;
                    pickDinner(k, listItemPrzekąska, adapterPrzekąska, listViewPrzekąska, cardViewPrzekąska, textViewPrzekąska, a, a);


                }
                if (i == 5) {
                    int a = 4;
                    pickDinner(k, listItemKolacja, adapterKolacja, listViewKolacja, cardViewKolacja, textViewKolacja, a, a);


                }
            }
        }


        baza.close();

    }

    public void pickDinner(Cursor k, ArrayList<String> listItem, ArrayAdapter adapter, ListView listView, CardView cardView, TextView textView, int a, int timeOfDay) {

        listItem = new ArrayList<>();

        while (k.moveToNext()) {


            listItem.add(k.getString(0) + " | Kcal: " + k.getString(1) + " | B: " + k.getString(2) + " | W: " + k.getString(3) + " | T: " + k.getString(4) + " | Błonnik: " + k.getString(5) + " | Ilość: " + k.getString(6));
            addValueCalories[a] = addValueCalories[a] + Double.valueOf(k.getString(1));
            addValueProtein[a] = addValueProtein[a] + Double.valueOf(k.getString(2));
            addValueCarb[a] = addValueCarb[a] + Double.valueOf(k.getString(3));
            addValueFat[a] = addValueFat[a] + Double.valueOf(k.getString(4));


        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItem) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.GRAY);
                return view;
            }
        };

        checkTimeOfDay(timeOfDay, addValueCalories[a], addValueProtein[a], addValueCarb[a], addValueFat[a]);
        checkAdapter(adapter, cardView, listView, textView);

    }

    public void addProduct(String i) {
        Intent AddProduct = new Intent(getContext(), AddProductActivity.class);
        startActivity(AddProduct);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PoraDnia", i); //InputString: from the EditText
        editor.commit();
    }


    public void hideListview(ListView listView) {
        listView.setVisibility(View.GONE);
    }

    public void hideAllListview() {
        listViewSniadanie.setVisibility(View.GONE);
        listViewLunch.setVisibility(View.GONE);
        listViewObiad.setVisibility(View.GONE);
        listViewPrzekąska.setVisibility(View.GONE);
        listViewKolacja.setVisibility(View.GONE);
    }


    public void showListview(ListView listView) {
        listView.setVisibility(View.VISIBLE);
    }

    public void checkAdapter(ArrayAdapter adapter, CardView cardView, ListView listView, TextView textView) {

        if (adapter.getCount() == 0) {
            cardView.setBackgroundColor(Color.rgb(255, 255, 255));
            textView.setTextColor(Color.rgb(88, 88, 88));
            listView.setAdapter(adapter);
            ResizeListView resizeListView = new ResizeListView();
            resizeListView.resize(listView);


        } else {
            cardView.setBackgroundColor(Color.rgb(232, 232, 232));
            textView.setTextColor(Color.rgb(8, 8, 8));
            listView.setAdapter(adapter);
            ResizeListView resizeListView = new ResizeListView();
            resizeListView.resize(listView);

        }

    }

    public void checkTimeOfDay(int timeOfDay, Double calories, Double protein, Double carb, Double Fat) {


        if (timeOfDay == 0) {
            textViewKcalS.setText(String.valueOf(calories));
            textViewWS.setText(String.valueOf(carb));
            textViewPS.setText(String.valueOf(protein));
            textViewTS.setText(String.valueOf(Fat));

        }
        if (timeOfDay == 1) {
            textViewKcalL.setText(String.valueOf(calories));
            textViewWL.setText(String.valueOf(carb));
            textViewPL.setText(String.valueOf(protein));
            textViewTL.setText(String.valueOf(Fat));
        }
        if (timeOfDay == 2) {
            textViewKcalO.setText(String.valueOf(calories));
            textViewWO.setText(String.valueOf(carb));
            textViewPO.setText(String.valueOf(protein));
            textViewTO.setText(String.valueOf(Fat));
        }
        if (timeOfDay == 3) {
            textViewKcalP.setText(String.valueOf(calories));
            textViewWP.setText(String.valueOf(carb));
            textViewPP.setText(String.valueOf(protein));
            textViewTP.setText(String.valueOf(Fat));
        }
        if (timeOfDay == 4) {
            textViewKcalK.setText(String.valueOf(calories));
            textViewWK.setText(String.valueOf(carb));
            textViewPK.setText(String.valueOf(protein));
            textViewTK.setText(String.valueOf(Fat));
        }


    }


    public void resetAllTextview() {

        for (int i = 0; i < 5; i++) {
            addValueCalories[i] = 0;
            addValueProtein[i] = 0;
            addValueFat[i] = 0;
            addValueCarb[i] = 0;
        }

        textViewKcalS.setText("0");
        textViewWS.setText("0");
        textViewPS.setText("0");
        textViewTS.setText("0");

        textViewKcalL.setText("0");
        textViewWL.setText("0");
        textViewPL.setText("0");
        textViewTL.setText("0");

        textViewKcalO.setText("0");
        textViewWO.setText("0");
        textViewPO.setText("0");
        textViewTO.setText("0");


        textViewKcalP.setText("0");
        textViewWP.setText("0");
        textViewPP.setText("0");
        textViewTP.setText("0");

        textViewKcalK.setText("0");
        textViewWK.setText("0");
        textViewPK.setText("0");
        textViewTK.setText("0");


        textViewAllCalories.setText("0");
        textViewAllProtein.setText("0");
        textViewAllCarb.setText("0");
        textViewAllFat.setText("0");

    }

    public void sumUpEverything() {

        DecimalFormat df = new DecimalFormat("#.#");

        double sumKcal = Double.valueOf(textViewKcalS.getText().toString()) + Double.valueOf(textViewKcalL.getText().toString()) + Double.valueOf(textViewKcalO.getText().toString()) + Double.valueOf(textViewKcalP.getText().toString()) + Double.valueOf(textViewKcalK.getText().toString());
        double sumP = Double.valueOf(textViewPS.getText().toString()) + Double.valueOf(textViewPL.getText().toString()) + Double.valueOf(textViewPO.getText().toString()) + Double.valueOf(textViewPP.getText().toString()) + Double.valueOf(textViewPK.getText().toString());
        double sumW = Double.valueOf(textViewWS.getText().toString()) + Double.valueOf(textViewWL.getText().toString()) + Double.valueOf(textViewWO.getText().toString()) + Double.valueOf(textViewWP.getText().toString()) + Double.valueOf(textViewWK.getText().toString());
        double sumT = Double.valueOf(textViewTS.getText().toString()) + Double.valueOf(textViewTL.getText().toString()) + Double.valueOf(textViewTO.getText().toString()) + Double.valueOf(textViewTP.getText().toString()) + Double.valueOf(textViewTK.getText().toString());


        String białkoS = (df.format(sumP)).replace(",", ".");
        String tłuszczeS = (df.format(sumT)).replace(",", ".");
        String węglowodanyS = (df.format(sumW)).replace(",", ".");


        textViewAllCalories.setText("Kcal: " + String.valueOf(Math.round(sumKcal)));
        textViewAllProtein.setText("B: " + białkoS);
        textViewAllCarb.setText("W: " + węglowodanyS);
        textViewAllFat.setText("T: " + tłuszczeS);
    }

}
