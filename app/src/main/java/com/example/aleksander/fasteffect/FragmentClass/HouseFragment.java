package com.example.aleksander.fasteffect.FragmentClass;


import android.app.Activity;
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
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AddProductActivity;
import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.AuxiliaryClass.DataHolder;
import com.example.aleksander.fasteffect.AuxiliaryClass.InformationProduct;
import com.example.aleksander.fasteffect.AuxiliaryClass.ResizeListView;
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

    public static final String SHARED_PREFS = "shaaredPrefs";


    int kcalSum = 0;
    int wybor = 0;
    int[] hide = {0, 0, 0, 0, 0};
    int[] addValueCalories = new int[5];
    double[] addValueProtein = new double[5];
    double[] addValueFat = new double[5];
    double[] addValueCarb = new double[5];
    double[] maxValue = new double[5];
    String Login = "";


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

    public HouseFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_house, container, false);
        hideSoftKeyboard(getActivity());

        progressBarCalories = view.findViewById(R.id.progressBarCalories);
        progressBarProtein = view.findViewById(R.id.progressBarProtein);
        progressBarCarb = view.findViewById(R.id.progressBarCarb);
        progressBarFat = view.findViewById(R.id.progressBarFat);


        textViewAllCalories = view.findViewById(R.id.textViewAllCalories);
        textViewAllProtein = view.findViewById(R.id.textViewAllProtein);
        textViewAllCarb = view.findViewById(R.id.textViewAllCarb);
        textViewAllFat = view.findViewById(R.id.textViewAllFat);

        textViewKcalS = view.findViewById(R.id.KcalS);
        textViewPS = view.findViewById(R.id.PS);
        textViewWS = view.findViewById(R.id.WS);
        textViewTS = view.findViewById(R.id.TS);

        textViewKcalL = view.findViewById(R.id.KcalL);
        textViewPL = view.findViewById(R.id.PL);
        textViewWL = view.findViewById(R.id.WL);
        textViewTL = view.findViewById(R.id.TL);

        textViewKcalO = view.findViewById(R.id.KcalO);
        textViewPO = view.findViewById(R.id.PO);
        textViewWO = view.findViewById(R.id.WO);
        textViewTO = view.findViewById(R.id.TO);

        textViewKcalP = view.findViewById(R.id.KcalP);
        textViewPP = view.findViewById(R.id.PP);
        textViewWP = view.findViewById(R.id.WP);
        textViewTP = view.findViewById(R.id.TP);

        textViewKcalK = view.findViewById(R.id.KcalK);
        textViewPK = view.findViewById(R.id.PK);
        textViewWK = view.findViewById(R.id.WK);
        textViewTK = view.findViewById(R.id.TK);

        listItemSniadanie = new ArrayList<>();
        listItemLunch = new ArrayList<>();
        listItemObiad = new ArrayList<>();
        listItemPrzekąska = new ArrayList<>();
        listItemKolacja = new ArrayList<>();


        textViewSniadanie = view.findViewById(R.id.textViewSniadanie);
        textViewLunch = view.findViewById(R.id.textViewLunch);
        textViewObiad = view.findViewById(R.id.textViewObiad);
        textViewPrzekąska = view.findViewById(R.id.textViewPrzekąska);
        textViewKolacja = view.findViewById(R.id.textViewKolacja);

        cardViewSniadanie = view.findViewById(R.id.cardViewSniadanie);
        cardViewLunch = view.findViewById(R.id.cardViewLunch);
        cardViewObiad = view.findViewById(R.id.cardViewObiad);
        cardViewPrzekąska = view.findViewById(R.id.cardViewPrzekąska);
        cardViewKolacja = view.findViewById(R.id.cardViewKolacja);

        listViewSniadanie = view.findViewById(R.id.ListViewSniadanie);
        listViewLunch = view.findViewById(R.id.ListViewLunch);
        listViewObiad = view.findViewById(R.id.ListViewObiad);
        listViewPrzekąska = view.findViewById(R.id.ListViewPrzekąska);
        listViewKolacja = view.findViewById(R.id.ListViewKolacja);

        ImageButton buttonAddProduct = view.findViewById(R.id.buttonAddProduct);
        ImageButton buttonAddProduct2 = view.findViewById(R.id.buttonAddProduct2);
        ImageButton buttonAddProduct3 = view.findViewById(R.id.buttonAddProduct3);
        ImageButton buttonAddProduct4 = view.findViewById(R.id.buttonAddProduct4);
        ImageButton buttonAddProduct5 = view.findViewById(R.id.buttonAddProduct5);
        textViewData = view.findViewById(R.id.textViewData);


        {
            listViewSniadanie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    new InformationProduct((String) adapterView.getItemAtPosition(i), (String) adapterView.getItemAtPosition(i));
                    String nazwa = InformationProduct.getNazwa();
                    String ilosc = InformationProduct.getIlosc();
                    int poraDnia = 1;

                    alert(poraDnia, ilosc, nazwa);

                }
            });
            listViewLunch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    new InformationProduct((String) adapterView.getItemAtPosition(i), (String) adapterView.getItemAtPosition(i));
                    String nazwa = InformationProduct.getNazwa();
                    String ilosc = InformationProduct.getIlosc();
                    int poraDnia = 2;

                    alert(poraDnia, ilosc, nazwa);
                }
            });
            listViewObiad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    new InformationProduct((String) adapterView.getItemAtPosition(i), (String) adapterView.getItemAtPosition(i));
                    String nazwa = InformationProduct.getNazwa();
                    String ilosc = InformationProduct.getIlosc();
                    int poraDnia = 3;
                    alert(poraDnia, ilosc, nazwa);
                }
            });
            listViewPrzekąska.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    new InformationProduct((String) adapterView.getItemAtPosition(i), (String) adapterView.getItemAtPosition(i));
                    String nazwa = InformationProduct.getNazwa();
                    String ilosc = InformationProduct.getIlosc();
                    int poraDnia = 4;
                    alert(poraDnia, ilosc, nazwa);
                }
            });
            listViewKolacja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    new InformationProduct((String) adapterView.getItemAtPosition(i), (String) adapterView.getItemAtPosition(i));
                    String nazwa = InformationProduct.getNazwa();
                    String ilosc = InformationProduct.getIlosc();
                    int poraDnia = 5;
                    alert(poraDnia, ilosc, nazwa);
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


                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        refreshApp();
                        refreshAfterDbChanged();
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
                editor.putString("DataSend", dateSend);
                editor.commit();
                DataHolder.getInstance().setData(dateSend);
                refreshApp();
                refreshAfterDbChanged();
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

        refreshApp();

        return view;
    }


    public void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(today);
        textViewData.setText(date);
        addToDatabse(date);
        DataHolder.getInstance().setData(date);
    }

    public void addToDatabse(String dateSend) {
        SQLiteDatabase baza = getActivity().openOrCreateDatabase(
                BazaDanychStruktura.BazaPlik,
                android.content.Context.MODE_PRIVATE,
                null
        );
        ContentValues rekord = new ContentValues();
        rekord.put(BazaDanychStruktura.TabelaHash, dateSend);
        baza.insert(BazaDanychStruktura.TabelaHash, null, rekord);
        baza.close();
    }


    public void viewDatabase() {

        listItemSniadanie.clear();
        adapterSniadanie = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemSniadanie);
        listViewSniadanie.setAdapter(adapterSniadanie);

        listItemLunch.clear();
        adapterLunch = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemLunch);
        listViewLunch.setAdapter(adapterLunch);

        listItemObiad.clear();
        adapterObiad = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemObiad);
        listViewObiad.setAdapter(adapterObiad);

        listItemPrzekąska.clear();
        adapterPrzekąska = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemPrzekąska);
        listViewPrzekąska.setAdapter(adapterPrzekąska);

        listItemKolacja.clear();
        adapterKolacja = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemKolacja);
        listViewKolacja.setAdapter(adapterKolacja);


        SQLiteDatabase baza = getActivity().openOrCreateDatabase(BazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);

        for (int i = 1; i <= 5; i++) {

            Cursor k = baza.rawQuery(
                    "SELECT Posilek.Nazwa, Posilek.Kalorie, Posilek.Bialko, Posilek.Weglowodany, Posilek.Tluszcze, Posilek.Błonnik, Posilek.Ilość " +
                            "FROM  Hash, PoraDnia,Posilek " +
                            "WHERE Hash.idPosilek=Posilek.idPosilek " +
                            "AND Hash.idPoraDnia = PoraDnia.idPoraDnia " +
                            "AND Hash.idPoraDnia= '" + i + "'" +
                            "AND Hash.Data = '" +
                            textViewData.getText().toString() + "'",
                    null);

            if (k.getCount() == 0) {
            } else {
                if (i == 1) {
                    int a = 0;
                    pickDinner(k, listItemSniadanie, listViewSniadanie, cardViewSniadanie, textViewSniadanie, a);
                }
                if (i == 2) {
                    int a = 1;
                    pickDinner(k, listItemLunch, listViewLunch, cardViewLunch, textViewLunch, a);
                }
                if (i == 3) {
                    int a = 2;
                    pickDinner(k, listItemObiad, listViewObiad, cardViewObiad, textViewObiad, a);
                }
                if (i == 4) {
                    int a = 3;
                    pickDinner(k, listItemPrzekąska, listViewPrzekąska, cardViewPrzekąska, textViewPrzekąska, a);
                }
                if (i == 5) {
                    int a = 4;
                    pickDinner(k, listItemKolacja, listViewKolacja, cardViewKolacja, textViewKolacja, a);
                }
            }
        }
        baza.close();
    }

    public void pickDinner(Cursor k, ArrayList<String> listItem, ListView listView, CardView cardView,
                           TextView textView, int timeOfDay) {

        ArrayAdapter adapter;
        while (k.moveToNext()) {

            listItem.add(
                    k.getString(0) + " | Kcal: " +
                            k.getString(1) + " | B: " +
                            k.getString(2) + " | W: " +
                            k.getString(3) + " | T: " +
                            k.getString(4) + " | Błonnik: " +
                            k.getString(5) + " | Ilość: " +
                            k.getString(6)
            );

            addValueCalories[timeOfDay] = addValueCalories[timeOfDay] + Math.round(Integer.valueOf(k.getString(1)));
            addValueProtein[timeOfDay] = addValueProtein[timeOfDay] + Double.valueOf(k.getString(2));
            addValueCarb[timeOfDay] = addValueCarb[timeOfDay] + Double.valueOf(k.getString(3));
            addValueFat[timeOfDay] = addValueFat[timeOfDay] + Double.valueOf(k.getString(4));

        }

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItem) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.rgb(72, 72, 72));
                return view;
            }
        };
        checkTimeOfDay(timeOfDay, addValueCalories[timeOfDay], addValueProtein[timeOfDay], addValueCarb[timeOfDay], addValueFat[timeOfDay]);
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

        if (timeOfDay == 0) {
            textViewKcalS.setText(String.valueOf(calories));
            textViewWS.setText(String.valueOf(formaterDouble(carb)));
            textViewPS.setText(String.valueOf(formaterDouble(protein)));
            textViewTS.setText(String.valueOf(formaterDouble(Fat)));

        }
        if (timeOfDay == 1) {

            textViewKcalL.setText(String.valueOf(calories));
            textViewWL.setText(String.valueOf(formaterDouble(carb)));
            textViewPL.setText(String.valueOf(formaterDouble(protein)));
            textViewTL.setText(String.valueOf(formaterDouble(Fat)));
        }
        if (timeOfDay == 2) {

            textViewKcalO.setText(String.valueOf(calories));
            textViewWO.setText(String.valueOf(formaterDouble(carb)));
            textViewPO.setText(String.valueOf(formaterDouble(protein)));
            textViewTO.setText(String.valueOf(formaterDouble(Fat)));
        }
        if (timeOfDay == 3) {

            textViewKcalP.setText(String.valueOf(calories));
            textViewWP.setText(String.valueOf(formaterDouble(carb)));
            textViewPP.setText(String.valueOf(formaterDouble(protein)));
            textViewTP.setText(String.valueOf(formaterDouble(Fat)));
        }
        if (timeOfDay == 4) {

            textViewKcalK.setText(String.valueOf(calories));
            textViewWK.setText(String.valueOf(formaterDouble(carb)));
            textViewPK.setText(String.valueOf(formaterDouble(protein)));
            textViewTK.setText(String.valueOf(formaterDouble(Fat)));
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

        String resetText = "0";
        textViewKcalS.setText(resetText);
        textViewWS.setText(resetText);
        textViewPS.setText(resetText);
        textViewTS.setText(resetText);

        textViewKcalL.setText(resetText);
        textViewWL.setText(resetText);
        textViewPL.setText(resetText);
        textViewTL.setText(resetText);

        textViewKcalO.setText(resetText);
        textViewWO.setText(resetText);
        textViewPO.setText(resetText);
        textViewTO.setText(resetText);

        textViewKcalP.setText(resetText);
        textViewWP.setText(resetText);
        textViewPP.setText(resetText);
        textViewTP.setText(resetText);

        textViewKcalK.setText(resetText);
        textViewWK.setText(resetText);
        textViewPK.setText(resetText);
        textViewTK.setText(resetText);

        textViewAllCalories.setText(resetText);
        textViewAllProtein.setText(resetText);
        textViewAllCarb.setText(resetText);
        textViewAllFat.setText(resetText);

    }

    public void sumUpEverything() {

        int optionSportFragment;

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String dataSportFragment = sharedPreferences.getString("optionSelected", "0"); //no id: default value
        optionSportFragment = Integer.valueOf(dataSportFragment);

        int sumKcal = Integer.valueOf(textViewKcalS.getText().toString()) + Integer.valueOf(textViewKcalL.getText().toString()) + Integer.valueOf(textViewKcalO.getText().toString()) + Integer.valueOf(textViewKcalP.getText().toString()) + Integer.valueOf(textViewKcalK.getText().toString());
        double sumP = Double.valueOf(textViewPS.getText().toString()) + Double.valueOf(textViewPL.getText().toString()) + Double.valueOf(textViewPO.getText().toString()) + Double.valueOf(textViewPP.getText().toString()) + Double.valueOf(textViewPK.getText().toString());
        double sumW = Double.valueOf(textViewWS.getText().toString()) + Double.valueOf(textViewWL.getText().toString()) + Double.valueOf(textViewWO.getText().toString()) + Double.valueOf(textViewWP.getText().toString()) + Double.valueOf(textViewWK.getText().toString());
        double sumT = Double.valueOf(textViewTS.getText().toString()) + Double.valueOf(textViewTL.getText().toString()) + Double.valueOf(textViewTO.getText().toString()) + Double.valueOf(textViewTP.getText().toString()) + Double.valueOf(textViewTK.getText().toString());

        double białkoS = formaterDouble(sumP);
        double tłuszczeS = formaterDouble(sumT);
        double węglowodanyS = formaterDouble(sumW);


        if (optionSportFragment == 1) {

            String dataCalories = sharedPreferences.getString("textCalories", "0");
            String dataProtein = sharedPreferences.getString("textProtein", "0");
            String dataCarb = sharedPreferences.getString("textCarb", "0");
            String dataFat = sharedPreferences.getString("textFat", "0");
            maxValue[0] = (mathematicalFormulas(dataCalories, dataProtein, 4));
            maxValue[1] = (mathematicalFormulas(dataCalories, dataCarb, 4));
            maxValue[2] = (mathematicalFormulas(dataCalories, dataFat, 9));
            kcalSum = Integer.valueOf(dataCalories);

        }

        if (optionSportFragment == 0) {

            String dataWaga = sharedPreferences.getString("optionWaga", "0");
            String dataWiek = sharedPreferences.getString("optionWiek", "0");
            String dataWzrost = sharedPreferences.getString("optionWzrost", "0");
            String dataPlec = sharedPreferences.getString("optionPlec", "0");
            String dataAktywnosc = sharedPreferences.getString("spinnerAktywnosc", "0");
            String dataRodzajSportu = sharedPreferences.getString("spinnerRodzajSportu", "0");
            String dataCel = sharedPreferences.getString("spinnerCel", "0");
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
            kcalSum = mathematicalForSecondOption(waga, wzrost, wiek, dataPlec, cel, aktywnosc);
        }

        textViewAllCalories.setText("Kcal:\n " + kcalSum + "/" + Math.round(sumKcal));
        textViewAllProtein.setText("Białko:\n " + maxValue[0] + "/" + białkoS);
        textViewAllCarb.setText("Węglowodany:\n " + maxValue[1] + "/" + węglowodanyS);
        textViewAllFat.setText("Tłuszcze:\n " + maxValue[2] + "/" + tłuszczeS);

        setProgressBar(Integer.valueOf(kcalSum), sumKcal, progressBarCalories);
        setProgressBar(doubleToInt(maxValue[0]), doubleToInt(sumP), progressBarProtein);
        setProgressBar(doubleToInt(maxValue[1]), doubleToInt(sumW), progressBarCarb);
        setProgressBar(doubleToInt(maxValue[2]), doubleToInt(sumT), progressBarFat);

    }

    public double mathematicalFormulas(String kcal, String macro, int division) {

        double converterStringKcal = Double.valueOf(kcal);
        double converterStringMacro = Double.valueOf(macro);
        Double mathematicalFormulas = ((converterStringKcal * converterStringMacro) / 100) / division;
        double result = formaterDouble(mathematicalFormulas);
        return result;

    }

    public void setProgressBar(int maxValue, int currentValue, ProgressBar progressBar) {

        progressBar.setMax(maxValue);
        progressBar.setProgress(currentValue);
        if (currentValue > maxValue + 5) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FF0000"), android.graphics.PorterDuff.Mode.SRC_IN);
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

    public void alert(final int poraDnia, final String ilosc, final String nazwa) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.Dialog);
        builder.setTitle("Zmień ilość bądź usuń produkt");
        builder.setCancelable(true);
        final EditText input = new EditText(getActivity());

        builder.setNeutralButton("Edytuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int wartoscGram = Integer.valueOf(input.getText().toString());
                if (wartoscGram == 0) {
                    Toast.makeText(getContext(), "Wartość nie może wynosić 0!", Toast.LENGTH_SHORT).show();
                } else {
                    editProductInDatabase(wartoscGram, poraDnia, ilosc, nazwa);
                }

            }
        });
        builder.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFromDatabase(poraDnia, ilosc, nazwa);

            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        input.setTextColor(Color.GRAY);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(input);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.GREEN);

    }

    public void deleteFromDatabase(int poraDnia, String ilosc, String nazwa) {

        SQLiteDatabase baza = getActivity().openOrCreateDatabase(BazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);
        int iloscCON = Integer.valueOf(ilosc.replaceAll("\\s+", ""));
        String nazwaCON = nazwa.replaceAll("\\s+$", "");
        Cursor idPosilek = baza.rawQuery(
                " SELECT " + BazaDanychStruktura.BazaTabelaidPosilek +
                        " FROM " + BazaDanychStruktura.TabelaPosilek +
                        " WHERE " + BazaDanychStruktura.BazaTabelaNazwa +
                        " LIKE '" + nazwaCON + "%' " +
                        " AND " + BazaDanychStruktura.BazaTabelaIlość + " = " + iloscCON +
                        " LIMIT 1 ",
                null);

        idPosilek.moveToFirst();
        Cursor idHashu = baza.rawQuery(
                "SELECT Hash.idHash " +
                        "FROM  Hash " +
                        "WHERE Hash.idPoraDnia = '" + poraDnia + "' " +
                        "AND Hash.Data = '" + textViewData.getText().toString() + "' " +
                        "AND Hash.idPosilek ='" + idPosilek.getString(0) + "'",
                null);

        idHashu.moveToFirst();
        Cursor usun = baza.rawQuery(
                "DELETE  " +
                        "FROM Hash " +
                        "WHERE Hash.idHash='" + idHashu.getString(0) + "' ",
                null);

        usun.moveToFirst();
        baza.close();
        refreshApp();
        refreshAfterDbChanged();
    }

    public void editProductInDatabase(int wartoscGram, int poraDnia, String ilosc, String nazwa) {

        int kcal, il;
        double b, w, t, bl;

        SQLiteDatabase baza = getActivity().openOrCreateDatabase(BazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);
        int iloscCON = Integer.valueOf(ilosc.replaceAll("\\s+", ""));
        String nazwaCON = nazwa.replaceAll("\\s+$", "");

        Cursor idPosilek = baza.rawQuery(
                " SELECT " + BazaDanychStruktura.BazaTabelaidPosilek +
                        " FROM " + BazaDanychStruktura.TabelaPosilek +
                        " WHERE " + BazaDanychStruktura.BazaTabelaNazwa +
                        " LIKE '" + nazwaCON + "%' " +
                        " AND " + BazaDanychStruktura.BazaTabelaIlość + " = " + iloscCON +
                        " LIMIT 1 ",
                null);

        idPosilek.moveToFirst();

        Cursor daneProduktu = baza.rawQuery(
                "SELECT Posilek.Bialko, Posilek.Błonnik, Posilek.Kalorie " + ",Posilek.Tluszcze,Posilek.Weglowodany, Posilek.Ilość, Posilek.Nazwa " +
                        "FROM Posilek , Hash, PoraDnia " +
                        "WHERE Hash.Data = '" + textViewData.getText().toString() + "' " +
                        "AND Hash.idPoraDnia = PoraDnia.idPoraDnia " +
                        "AND Hash.idPosilek = Posilek.idPosilek " + " " +
                        "AND Posilek.idPosilek='" + idPosilek.getString(0) + "'",
                null);

        daneProduktu.moveToFirst();

        Cursor idHashu = baza.rawQuery(
                "SELECT Hash.idHash " +
                        "FROM  Hash " +
                        "WHERE Hash.idPoraDnia = '" + poraDnia + "' " +
                        "AND Hash.Data = '"   + textViewData.getText().toString() + "' " +
                        "AND Hash.idPosilek ='" + idPosilek.getString(0) + "'",
                null);

        idHashu.moveToFirst();

        {
            {
                b = Double.valueOf(daneProduktu.getString(0));
                bl = Double.valueOf(daneProduktu.getString(1));
                kcal = Integer.valueOf(daneProduktu.getString(2));
                t = Double.valueOf(daneProduktu.getString(3));
                w = Double.valueOf(daneProduktu.getString(4));
                il = Integer.valueOf(daneProduktu.getString(5));
            }
            {
                b = formaterDouble((wartoscGram * b) / il);
                bl = formaterDouble((wartoscGram * bl) / il);
                kcal = doubleToInt((wartoscGram * kcal) / il);
                t = formaterDouble((wartoscGram * t) / il);
                w = formaterDouble((wartoscGram * w) / il);
                il = wartoscGram;
            }

            try {
                Cursor checkInDatabse = baza.rawQuery(
                        "SELECT Posilek.idPosilek  " +
                                "FROM  Posilek " +
                                "WHERE Posilek.Nazwa " +
                                "like '" + daneProduktu.getString(6) + "' " +
                                "AND Posilek.Ilość='" + wartoscGram + "' ",
                        null);

                checkInDatabse.moveToFirst();
                Cursor usun = baza.rawQuery(
                        "DELETE " +
                                "FROM Hash " +
                                "WHERE Hash.idHash='" + idHashu.getString(0) + "' ",
                        null);
                usun.moveToFirst();
                Cursor insertToDatabaseHash = baza.rawQuery(

                        "INSERT INTO Hash(Data,idPosilek,idPoraDnia) " +
                                "VALUES ('" + textViewData.getText().toString() + "','" + checkInDatabse.getString(0) + "','" + poraDnia + "')",
                        null);

                insertToDatabaseHash.moveToFirst();

            } catch (Exception ex) {
                Cursor insertToDatabsePosilek = baza.rawQuery(
                        "INSERT INTO Posilek(Nazwa,Bialko,Weglowodany,Tluszcze,Błonnik,Kalorie,Ilość)" +
                                "VALUES('" + daneProduktu.getString(6) + "','" + b + "','" + w + "','" + t + "','" + bl + "','" + kcal + "','" + il + "')",
                        null);

                insertToDatabsePosilek.moveToFirst();
                Cursor checkInDatabse = baza.rawQuery(
                        "SELECT Posilek.idPosilek  " +
                                "FROM  Posilek " +
                                "WHERE Posilek.Nazwa " +
                                "like '" + daneProduktu.getString(6)+ "' " +
                                "AND Posilek.Ilość='" + wartoscGram + "' ",
                        null);

                checkInDatabse.moveToFirst();
                Cursor usun = baza.rawQuery(

                        "DELETE " +
                                "FROM Hash " +
                                "WHERE Hash.idHash='" + idHashu.getString(0) + "' ",
                        null);
                usun.moveToFirst();
                Cursor insertToDatabaseHash = baza.rawQuery(
                        "INSERT INTO Hash(Data,idPosilek,idPoraDnia) " +
                                "VALUES ('" + textViewData.getText().toString() + "','" + checkInDatabse.getString(0) + "','" + poraDnia + "')",
                        null);

                insertToDatabaseHash.moveToFirst();
            }
            baza.close();
            refreshApp();
            refreshAfterDbChanged();
        }
    }

    public void refreshApp() {

        resetAllTextview();
        hideAllListview();
        viewDatabase();
        sumUpEverything();
    }

    public double formaterDouble(double variables) {
        DecimalFormat df = new DecimalFormat("#.#");
        Double doubleFormat = Double.valueOf(df.format(variables).replace(",", "."));
        return doubleFormat;
    }

    public void refreshAfterDbChanged() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
