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
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    int kcalSum = 0;
    int wybor = 0;
    int[] hide = {0, 0, 0, 0, 0};
    int[] addValueCalories = new int[5];
    double[] addValueProtein = new double[5];
    double[] addValueFat = new double[5];
    double[] addValueCarb = new double[5];
    double[] maxValue = new double[5];

    double aktywnosc;
    int rodzajSportu;
    int cel;

    ProgressBar progressBarCalories;
    ProgressBar progressBarProtein;
    ProgressBar progressBarCarb;
    ProgressBar progressBarFat;

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


        progressBarCalories = (ProgressBar) view.findViewById(R.id.progressBarCalories);
        progressBarProtein = (ProgressBar) view.findViewById(R.id.progressBarProtein);
        progressBarCarb = (ProgressBar) view.findViewById(R.id.progressBarCarb);
        progressBarFat = (ProgressBar) view.findViewById(R.id.progressBarFat);

        setProgressBarColor(progressBarCalories, 1);
        setProgressBarColor(progressBarProtein, 2);
        setProgressBarColor(progressBarCarb, 3);
        setProgressBarColor(progressBarFat, 4);


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

        //listviewitemlistener
        {
            listViewSniadanie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int poraDnia = 1;
                    alert(poraDnia, i, adapterSniadanie, cardViewSniadanie, listViewSniadanie, textViewSniadanie);

                }
            });
            listViewLunch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int poraDnia = 2;
                    alert(poraDnia, i, adapterLunch, cardViewLunch, listViewLunch, textViewLunch);
                }
            });
            listViewObiad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int poraDnia = 3;
                    alert(poraDnia, i, adapterObiad, cardViewObiad, listViewObiad, textViewObiad);
                }
            });
            listViewPrzekąska.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int poraDnia = 4;
                    alert(poraDnia, i, adapterPrzekąska, cardViewPrzekąska, listViewPrzekąska, textViewPrzekąska);
                }
            });
            listViewKolacja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int poraDnia = 5;
                    alert(poraDnia, i, adapterKolacja, cardViewKolacja, listViewKolacja, textViewKolacja);
                }
            });
        }

        //cardviewlistener
        {
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
        }


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


            addValueCalories[a] = addValueCalories[a] + Math.round(Integer.valueOf(k.getString(1)));
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

                tv.setTextColor(Color.rgb(72, 72, 72));
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

    public void checkTimeOfDay(int timeOfDay, int calories, Double protein, Double carb, Double Fat) {

        DecimalFormat df = new DecimalFormat("#.#");

        if (timeOfDay == 0) {


            textViewKcalS.setText(String.valueOf(df.format(calories)).replace(",", "."));
            textViewWS.setText(String.valueOf(df.format(carb)).replace(",", "."));
            textViewPS.setText(String.valueOf(df.format(protein)).replace(",", "."));
            textViewTS.setText(String.valueOf(df.format(Fat)).replace(",", "."));

        }
        if (timeOfDay == 1) {


            textViewKcalL.setText(String.valueOf(df.format(calories)).replace(",", "."));
            textViewWL.setText(String.valueOf(df.format(carb)).replace(",", "."));
            textViewPL.setText(String.valueOf(df.format(protein)).replace(",", "."));
            textViewTL.setText(String.valueOf(df.format(Fat)).replace(",", "."));
        }
        if (timeOfDay == 2) {


            textViewKcalO.setText(String.valueOf(df.format(calories)).replace(",", "."));
            textViewWO.setText(String.valueOf(df.format(carb)).replace(",", "."));
            textViewPO.setText(String.valueOf(df.format(protein)).replace(",", "."));
            textViewTO.setText(String.valueOf(df.format(Fat)).replace(",", "."));
        }
        if (timeOfDay == 3) {


            textViewKcalP.setText(String.valueOf(df.format(calories)).replace(",", "."));
            textViewWP.setText(String.valueOf(df.format(carb)).replace(",", "."));
            textViewPP.setText(String.valueOf(df.format(protein)).replace(",", "."));
            textViewTP.setText(String.valueOf(df.format(Fat)).replace(",", "."));
        }
        if (timeOfDay == 4) {


            textViewKcalK.setText(String.valueOf(df.format(calories)).replace(",", "."));
            textViewWK.setText(String.valueOf(df.format(carb)).replace(",", "."));
            textViewPK.setText(String.valueOf(df.format(protein)).replace(",", "."));
            textViewTK.setText(String.valueOf(df.format(Fat)).replace(",", "."));
        }


    }


    public void resetAllTextview() {

        maxValue[0] = 0;
        maxValue[1] = 0;
        maxValue[2] = 0;


        progressBarCalories.getProgressDrawable().setColorFilter(
                Color.parseColor("#551A8B"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarProtein.getProgressDrawable().setColorFilter(
                Color.parseColor("#41AECF"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarCarb.getProgressDrawable().setColorFilter(
                Color.parseColor("#00C853"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarFat.getProgressDrawable().setColorFilter(
                Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_IN);


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


        int optionSportFragment;

        SharedPreferences SharedPreferencesOptionFromSportFragment = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataSportFragment = SharedPreferencesOptionFromSportFragment.getString("optionSelected", "0"); //no id: default value
        optionSportFragment = Integer.valueOf(dataSportFragment);

        int sumKcal = Integer.valueOf(textViewKcalS.getText().toString()) + Integer.valueOf(textViewKcalL.getText().toString()) + Integer.valueOf(textViewKcalO.getText().toString()) + Integer.valueOf(textViewKcalP.getText().toString()) + Integer.valueOf(textViewKcalK.getText().toString());
        double sumP = Double.valueOf(textViewPS.getText().toString()) + Double.valueOf(textViewPL.getText().toString()) + Double.valueOf(textViewPO.getText().toString()) + Double.valueOf(textViewPP.getText().toString()) + Double.valueOf(textViewPK.getText().toString());
        double sumW = Double.valueOf(textViewWS.getText().toString()) + Double.valueOf(textViewWL.getText().toString()) + Double.valueOf(textViewWO.getText().toString()) + Double.valueOf(textViewWP.getText().toString()) + Double.valueOf(textViewWK.getText().toString());
        double sumT = Double.valueOf(textViewTS.getText().toString()) + Double.valueOf(textViewTL.getText().toString()) + Double.valueOf(textViewTO.getText().toString()) + Double.valueOf(textViewTP.getText().toString()) + Double.valueOf(textViewTK.getText().toString());


        DecimalFormat df = new DecimalFormat("#.#");

        String białkoS = (df.format(sumP)).replace(",", ".");
        String tłuszczeS = (df.format(sumT)).replace(",", ".");
        String węglowodanyS = (df.format(sumW)).replace(",", ".");

        if (optionSportFragment == 1) {

            // Toast.makeText(getContext(), dataSportFragment, Toast.LENGTH_SHORT).show();

            SharedPreferences SharedPreferencesCalories = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataCalories = SharedPreferencesCalories.getString("textCalories", "0"); //no id: default value


            SharedPreferences SharedPreferencesProtein = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataProtein = SharedPreferencesProtein.getString("textProtein", "0"); //no id: default value


            SharedPreferences sharedPreferencesCarb = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataCarb = sharedPreferencesCarb.getString("textCarb", "0"); //no id: default value


            SharedPreferences sharedPreferencesFat = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataFat = sharedPreferencesFat.getString("textFat", "0"); //no id: default value


            maxValue[0] = (mathematicalFormulas(dataCalories, dataProtein, 4));
            maxValue[1] = (mathematicalFormulas(dataCalories, dataCarb, 4));
            maxValue[2] = (mathematicalFormulas(dataCalories, dataFat, 9));
            // maxValue[3]= (mathematicalFormulas(dataCalories, dataFat, 9));


            kcalSum = Integer.valueOf(dataCalories);


        }


        if (optionSportFragment == 0) {


            //profile fragment
            SharedPreferences SharedPreferencesWaga = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataWaga = SharedPreferencesWaga.getString("optionWaga", "0"); //no id: default value


            SharedPreferences SharedPreferencesWiek = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataWiek = SharedPreferencesWiek.getString("optionWiek", "0"); //no id: default value


            SharedPreferences SharedPreferencesWzrost = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataWzrost = SharedPreferencesWzrost.getString("optionWzrost", "0"); //no id: default value


            SharedPreferences SharedPreferencesPlec = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataPlec = SharedPreferencesPlec.getString("optionPlec", "0"); //no id: default value
            Toast.makeText(getContext(), String.valueOf(dataPlec), Toast.LENGTH_SHORT).show();


            //sport fragment
            SharedPreferences SharedPreferencesAktywnosc = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataAktywnosc = SharedPreferencesAktywnosc.getString("spinnerAktywnosc", "0"); //no id: default value


            SharedPreferences SharedPreferencesRodzajSportu = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataRodzajSportu = SharedPreferencesRodzajSportu.getString("spinnerRodzajSportu", "0"); //no id: default value


            SharedPreferences SharedPreferencesCel = PreferenceManager.getDefaultSharedPreferences(getContext());
            String dataCel = SharedPreferencesCel.getString("spinnerCel", "0"); //no id: default value

            //   Toast.makeText(getContext(), dataRodzajSportu, Toast.LENGTH_SHORT).show();

            rodzajSportu = Integer.valueOf(dataRodzajSportu);

            double waga = Double.valueOf(dataWaga);
            int wzrost = Integer.valueOf(dataWzrost);
            int wiek = Integer.valueOf(dataWiek);


            {
                if (dataAktywnosc.equals("0")) {
                    aktywnosc = 1.2;
                } else if (dataAktywnosc.equals("1")) {
                    aktywnosc = 1.3;
                } else if (dataAktywnosc.equals("2")) {
                    aktywnosc = 1.5;
                } else if (dataAktywnosc.equals("3")) {
                    aktywnosc = 1.7;
                } else if (dataAktywnosc.equals("4")) {
                    aktywnosc = 1.9;
                }

            }

            {
                if (dataCel.equals("0")) {
                    cel = 0;
                } else if (dataCel.equals("1")) {
                    cel = 300;
                } else if (dataCel.equals("2")) {
                    cel = -300;
                }
            }

            // Toast.makeText(getContext(), String.valueOf(y), Toast.LENGTH_SHORT).show();
            kcalSum = mathematicalForSecondOption(waga, wzrost, wiek, dataPlec, cel, aktywnosc);


        }


        textViewAllCalories.setText("Kcal:\n " + String.valueOf(kcalSum) + "/" + String.valueOf(Math.round(sumKcal)));
        textViewAllProtein.setText("Białko:\n " + String.valueOf(maxValue[0]) + "/" + białkoS);
        textViewAllCarb.setText("Węglowodany:\n " + String.valueOf(maxValue[1]) + "/" + węglowodanyS);
        textViewAllFat.setText("Tłuszcze:\n " + String.valueOf(maxValue[2]) + "/" + tłuszczeS);


        setProgressBar(Integer.valueOf(kcalSum), sumKcal, progressBarCalories);
        setProgressBar(doubleToInt(maxValue[0]), doubleToInt(sumP), progressBarProtein);
        setProgressBar(doubleToInt(maxValue[1]), doubleToInt(sumW), progressBarCarb);
        setProgressBar(doubleToInt(maxValue[2]), doubleToInt(sumT), progressBarFat);

    }

    public double mathematicalFormulas(String kcal, String macro, int division) {


        double converterStringKcal = Double.valueOf(kcal);
        double converterStringMacro = Double.valueOf(macro);
        Double mathematicalFormulas = ((converterStringKcal * converterStringMacro) / 100) / division;
        DecimalFormat df = new DecimalFormat("#.#");
        String result = (df.format(mathematicalFormulas)).replace(",", ".");
        double mathematicalFormulasResult = Double.valueOf(result);
        return mathematicalFormulasResult;


    }

    public void setProgressBar(int maxValue, int currentValue, ProgressBar progressBar) {

        progressBar.setMax(maxValue);
        progressBar.setProgress(currentValue);
        if (currentValue > maxValue + 5) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FF0000"), android.graphics.PorterDuff.Mode.SRC_IN);
        }


    }

    public void setProgressBarColor(ProgressBar progressBar, int i) {


        if (i == 1) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#551A8B"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (i == 2) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#41AECF"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (i == 3) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#00C853"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (i == 4) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }

    public int doubleToInt(double doubleVariables) {

        Double D = doubleVariables;
        int i = Integer.valueOf(D.intValue());
        return i;
    }


    public int mathematicalForSecondOption(double waga, int wzrost, int wiek, String plec, int cel, double aktywnosc) {

        double wzorMatematyczny;

        int i = 0;

        if (plec.equals("M")) {
            wzorMatematyczny = ((66.5 + (13.7 * waga) + (5 * wzrost) - (6.8 * wiek)) * aktywnosc) + cel;
            i = doubleToInt(wzorMatematyczny);

            if (rodzajSportu == 0) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(30), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(45), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(25), 9));

            } else if (rodzajSportu == 1) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(20), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(65), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 9));


            } else if (rodzajSportu == 2) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(25), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(60), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 9));


            } else if (rodzajSportu == 3) {

                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(55), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(30), 9));

            }


        } else if (plec.equals("W")) {
            wzorMatematyczny = ((655 + (9.6 * waga) + (1.85 * wzrost) - (4.7 * wiek)) * aktywnosc) + cel;
            i = doubleToInt(wzorMatematyczny);

            if (rodzajSportu == 0) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(30), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(45), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(25), 9));

            } else if (rodzajSportu == 1) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(20), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(65), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 9));


            } else if (rodzajSportu == 2) {
                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(25), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(60), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 9));

            } else if (rodzajSportu == 3) {

                maxValue[0] = (mathematicalFormulas(String.valueOf(i), String.valueOf(15), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(i), String.valueOf(55), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(i), String.valueOf(30), 9));
            }

        }
        return i;


    }

    public void alert(final int poraDnia, final int position, final ArrayAdapter adapter, final CardView cardView, final ListView listView, final TextView textView) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.Dialog);


        Toast.makeText(getContext(), String.valueOf(poraDnia), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

        builder.setTitle("Czy chcesz usunąc produkt?");
        builder.setCancelable(true);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFromDatabase(poraDnia, position, adapter, cardView, listView, textView);
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        //  alertDialog.setTitle("asd");
        alertDialog.show();

    }


    public void deleteFromDatabase(int poraDnia, int position, final ArrayAdapter adapter, final CardView cardView, final ListView listView, final TextView textView) {


        BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();
        SQLiteDatabase baza = getActivity().openOrCreateDatabase(bazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Hash'( Data NUMERIC NOT NULL, idPosilek INTEGER NOT NULL,idPoraDnia INTEGER NOT NULL,  CONSTRAINT fk_Data FOREIGN KEY(Data) REFERENCES Dzien(Data),CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek),CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))");

        Cursor k = baza.rawQuery("SELECT Hash.idPosilek  FROM  Hash WHERE Hash.idPoraDnia = '" + poraDnia + "' AND Hash.Data = '" + textViewData.getText().toString() + "'" + "LIMIT 1 OFFSET '" + position + "'", null);
        k.moveToFirst();
        Cursor usun = baza.rawQuery("DELETE FROM Hash WHERE Hash.idPosilek = '" + k.getString(0) + "' ", null);

        usun.moveToFirst();
        baza.close();

        resetAllTextview();

        viewDatabase();
        sumUpEverything();
        checkAdapter(adapter, cardView, listView, textView);


    }


}
