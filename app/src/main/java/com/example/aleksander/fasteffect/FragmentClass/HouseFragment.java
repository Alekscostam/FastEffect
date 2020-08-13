package com.example.aleksander.fasteffect.FragmentClass;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.Closer;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.DataHolder;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.ResizeListView;
import com.example.aleksander.fasteffect.ProductClasses.AddProductActivity;
import com.example.aleksander.fasteffect.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.AlertDialog.BUTTON_NEUTRAL;
import static android.app.AlertDialog.BUTTON_POSITIVE;
import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.DATABASE_FILE;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_HASH;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.deleteByIdHashFromHashTable;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.deleteElementByIdHashFromHash;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.getElementByIdTimeOfDayAndDataAndIdMealFromHash;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.getElementByIdTimeOfDayAndDataFromAllTables;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.getElementByNameAndValueFromPosilek;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.getMealIdByDataAndIdMealFromTableMeal;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.getMealIdByNameAndAmountFromTableMeal;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.insertElementIntoHashTable;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.insertElementIntoMealTable;
import static java.util.Objects.requireNonNull;

/**
 * {@link Fragment}
 * Klasa będąca głównym oknem aplikacji. Pobiera ona wszelkie dane z lokalnej bazy danych
 * Zakladka "Strona główna"
 */
public class HouseFragment extends Fragment {

    public static final String SHARED_PREFS = "shaaredPrefs";


    private int caloriesSummary = 0;
    private int[] hide = {0, 0, 0, 0, 0};
    private int[] addValueCalories = new int[5];
    private double[] addValueProtein = new double[5];
    private double[] addValueFat = new double[5];
    private double[] addValueCarb = new double[5];
    private double[] maxValue = new double[5];

    private double activity;
    private int kindOfSport;
    private int goal;

    private ProgressBar progressBarCalories;
    private ProgressBar progressBarProtein;
    private ProgressBar progressBarCarb;
    private ProgressBar progressBarFat;

    private TextView textViewData;

    //Sniadanie
    private TextView textViewKcalS;
    private TextView textViewPS;
    private TextView textViewWS;
    private TextView textViewTS;

    //lunch
    private TextView textViewKcalL;
    private TextView textViewPL;
    private TextView textViewWL;
    private TextView textViewTL;
    //obiad
    private TextView textViewKcalO;
    private TextView textViewPO;
    private TextView textViewWO;
    private TextView textViewTO;
    //przekasaka
    private TextView textViewKcalP;
    private TextView textViewPP;
    private TextView textViewWP;
    private TextView textViewTP;
    //kolacja
    private TextView textViewKcalK;
    private TextView textViewPK;
    private TextView textViewWK;
    private TextView textViewTK;

    private TextView textViewAllCalories;
    private TextView textViewAllProtein;
    private TextView textViewAllCarb;
    private TextView textViewAllFat;

    private ListView listViewBreakfast;
    private ListView listViewLunch;
    private ListView listViewDinner;
    private ListView listViewSnack;
    private ListView listViewSupper;

    private CardView cardViewBreakfast;
    private CardView cardViewLunch;
    private CardView cardViewDinner;
    private CardView cardViewSnack;
    private CardView cardViewSupper;

    private ArrayList<String> listItemBreakfast;
    private ArrayList<String> listItemLunch;
    private ArrayList<String> listItemDinner;
    private ArrayList<String> listItemSnack;
    private ArrayList<String> listItemSupper;

    List<TextView> textViews;

    private ArrayAdapter<String> adapterBreakfast;
    private ArrayAdapter<String> adapterLunch;
    private ArrayAdapter<String> adapterDinner;
    private ArrayAdapter<String> adapterSnack;
    private ArrayAdapter<String> adapterSupper;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_house, container, false);

        hideSoftKeyboard(requireNonNull(getActivity()));

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

        listItemBreakfast = new ArrayList<>();
        listItemLunch = new ArrayList<>();
        listItemDinner = new ArrayList<>();
        listItemSnack = new ArrayList<>();
        listItemSupper = new ArrayList<>();


        textViews = Arrays.asList(
                view.findViewById(R.id.textViewSniadanie),
                view.findViewById(R.id.textViewLunch),
                view.findViewById(R.id.textViewObiad),
                view.findViewById(R.id.textViewPrzekąska),
                view.findViewById(R.id.textViewKolacja));


        cardViewBreakfast = view.findViewById(R.id.cardViewSniadanie);
        cardViewLunch = view.findViewById(R.id.cardViewLunch);
        cardViewDinner = view.findViewById(R.id.cardViewObiad);
        cardViewSnack = view.findViewById(R.id.cardViewPrzekąska);
        cardViewSupper = view.findViewById(R.id.cardViewKolacja);

        listViewBreakfast = view.findViewById(R.id.ListViewSniadanie);
        listViewLunch = view.findViewById(R.id.ListViewLunch);
        listViewDinner = view.findViewById(R.id.ListViewObiad);
        listViewSnack = view.findViewById(R.id.ListViewPrzekąska);
        listViewSupper = view.findViewById(R.id.ListViewKolacja);

        ImageButton buttonAddProductBreakfast = view.findViewById(R.id.buttonAddProductBreakfast);
        ImageButton buttonAddProductLunch = view.findViewById(R.id.buttonAddProductLunch);
        ImageButton buttonAddProductDinner = view.findViewById(R.id.buttonAddProductDinner);
        ImageButton buttonAddProductSnack = view.findViewById(R.id.buttonAddProductSnack);
        ImageButton buttonAddProductSupper = view.findViewById(R.id.buttonAddProductSupper);
        textViewData = view.findViewById(R.id.textViewData);

        /*
         List view wywołują metody dotyczace wybrania konkretnych operacji na danych
         */
        listViewBreakfast.setOnItemClickListener((adapterView, breakfast, positionValue, l) ->
                alertOperation(1, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewLunch.setOnItemClickListener((adapterView, lunch, positionValue, l) ->
                alertOperation(2, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewDinner.setOnItemClickListener((adapterView, dinner, positionValue, l) ->
                alertOperation(3, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewSnack.setOnItemClickListener((adapterView, snack, positionValue, l) ->
                alertOperation(4, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewSupper.setOnItemClickListener((adapterView, supper, positionValue, l) ->
                alertOperation(5, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));

        /*
        Klikniecie konkretnego cardView wywołuje metode umożliwiającą ukrycie badz pokazanie danej listy po jej kliknieciu
         */
        cardViewBreakfast.setOnClickListener(cardBreakfast -> cardViewShowOrHide(0, listViewBreakfast));
        cardViewLunch.setOnClickListener(cardLunch -> cardViewShowOrHide(1, listViewLunch));
        cardViewDinner.setOnClickListener(cardDinner -> cardViewShowOrHide(2, listViewDinner));
        cardViewSnack.setOnClickListener(cardSnack -> cardViewShowOrHide(3, listViewSnack));
        cardViewSupper.setOnClickListener(cardSupper -> cardViewShowOrHide(4, listViewSupper));

        /*
        Ustawia date jeżeli jest pusta
         */
        if (DataHolder.getInstance().getData().equals(""))
            setDate();
        else
            textViewData.setText(DataHolder.getInstance().getData());

        /*
        Pokazanie daty za pomoca za pomocą kalendarza
         */
        textViewData.setOnClickListener(dataView -> setOnViewDataField());

        /*
        pozwala ustawic date po jej wybraniu w kalendarzu
         */
        mDateSetListener = this::dataSetListener;

        /*
        Pozwala na przejscie do okna dodawnai produktów
         */
        buttonAddProductBreakfast.setOnClickListener(add -> addProduct("1"));
        buttonAddProductLunch.setOnClickListener(add -> addProduct("2"));
        buttonAddProductDinner.setOnClickListener(add -> addProduct("3"));
        buttonAddProductSnack.setOnClickListener(add -> addProduct("4"));
        buttonAddProductSupper.setOnClickListener(add -> addProduct("5"));

        refreshApp();

        return view;
    }

    private void setOnViewDataField() {

        Calendar calendarDate = Calendar.getInstance();
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(
                requireNonNull(getContext()),
                THEME_DEVICE_DEFAULT_LIGHT,
                mDateSetListener,
                year, month, day);

        dialog.setOnCancelListener(dialogInterface -> {
            refreshApp();
            refreshAfterDbChanged();
        });

            /*
            Ustawienie wygladu dla komponentow
             */
        checkAdapter(adapterBreakfast, cardViewBreakfast, listViewBreakfast, textViews.get(0));
        checkAdapter(adapterLunch, cardViewLunch, listViewLunch, textViews.get(1));
        checkAdapter(adapterDinner, cardViewDinner, listViewDinner, textViews.get(2));
        checkAdapter(adapterSnack, cardViewSnack, listViewSnack, textViews.get(3));
        checkAdapter(adapterSupper, cardViewSupper, listViewSupper, textViews.get(4));

        dialog.show();
    }

    private void dataSetListener(DatePicker datePicker, int year, int month, int day) {
        month = month + 1;
        String monthString = String.valueOf(month);
        String dayString = String.valueOf(day);

        if (month < 10)
            monthString = "0" + month;
        if (day < 10)
            dayString = "0" + day;

        String dateSend = (dayString + "-" + monthString + "-" + year);
        textViewData.setText(dateSend);
        addToDatabase(dateSend);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DataSend", dateSend);
        editor.apply();
        DataHolder.getInstance().setData(dateSend);
        refreshApp();
        refreshAfterDbChanged();
    }

    private void cardViewShowOrHide(int index, ListView listView) {

        if (hide[index] == 0) {
            showListView(listView);
            hide[index] = 1;
        } else {
            hideOneListView(listView);
            hide[index] = 0;
        }
    }

    private String getAmountFromValue(String itemAtPosition) {
        String substringFromItemAtPosition = itemAtPosition.substring(itemAtPosition.indexOf("Ilość:") + 6);
        return substringFromItemAtPosition.replaceAll("\\s+", "");
    }

    private String getNameFromValue(String itemAtPosition) {
        return itemAtPosition.substring(0, itemAtPosition.indexOf('|'));
    }

    private void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = sdfDate.format(today);
        textViewData.setText(date);
        addToDatabase(date);
        DataHolder.getInstance().setData(date);
    }

    private void addToDatabase(String dateSend) {
        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, android.content.Context.MODE_PRIVATE, null);

        ContentValues content = new ContentValues();
        content.put(TABLE_HASH, dateSend);
        sqLiteDatabase.insert(TABLE_HASH, null, content);
        sqLiteDatabase.close();
    }

    private void viewDatabase() {

        clearAndSetAdapter();
        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, android.content.Context.MODE_PRIVATE, null);

        for (int id = 1; id <= 5; id++) {

            Cursor selectFromMeal = sqLiteDatabase.rawQuery(
                    getElementByIdTimeOfDayAndDataFromAllTables(id, textViewData.getText().toString()), null);

            if ((selectFromMeal.getCount() != 0)) {
                switch (id) {
                    case 1:
                        pickDinner(selectFromMeal, listItemBreakfast, listViewBreakfast, cardViewBreakfast, textViews.get(0), 0);
                        break;
                    case 2:
                        pickDinner(selectFromMeal, listItemLunch, listViewLunch, cardViewLunch, textViews.get(1), 1);
                        break;
                    case 3:
                        pickDinner(selectFromMeal, listItemDinner, listViewDinner, cardViewDinner, textViews.get(2), 2);
                        break;
                    case 4:
                        pickDinner(selectFromMeal, listItemSnack, listViewSnack, cardViewSnack, textViews.get(3), 3);
                        break;
                    default:
                        pickDinner(selectFromMeal, listItemSupper, listViewSupper, cardViewSupper, textViews.get(4), 4);
                }
            }
        }
        sqLiteDatabase.close();
    }

    private void clearAndSetAdapter() {

        listItemBreakfast.clear();
        adapterBreakfast = new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, listItemBreakfast);
        listViewBreakfast.setAdapter(adapterBreakfast);

        listItemLunch.clear();
        adapterLunch = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemLunch);
        listViewLunch.setAdapter(adapterLunch);

        listItemDinner.clear();
        adapterDinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemDinner);
        listViewDinner.setAdapter(adapterDinner);

        listItemSnack.clear();
        adapterSnack = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemSnack);
        listViewSnack.setAdapter(adapterSnack);

        listItemSupper.clear();
        adapterSupper = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItemSupper);
        listViewSupper.setAdapter(adapterSupper);
    }

    private void pickDinner(Cursor cursor, List<String> listItem, ListView listView, CardView cardView,
                            TextView textView, int timeOfDay) {

        ArrayAdapter<String> adapter;
        while (cursor.moveToNext()) {
            listItem.add(
                    cursor.getString(0) + " | Kcal: " +
                            cursor.getString(1) + " | B: " +
                            cursor.getString(2) + " | W: " +
                            cursor.getString(3) + " | T: " +
                            cursor.getString(4) + " | Błonnik: " +
                            cursor.getString(5) + " | Ilość: " +
                            cursor.getString(6)
            );

            addValueCalories[timeOfDay] = addValueCalories[timeOfDay] + Integer.parseInt(cursor.getString(1));
            addValueProtein[timeOfDay] = addValueProtein[timeOfDay] + Double.parseDouble(cursor.getString(2));
            addValueCarb[timeOfDay] = addValueCarb[timeOfDay] + Double.parseDouble(cursor.getString(3));
            addValueFat[timeOfDay] = addValueFat[timeOfDay] + Double.parseDouble(cursor.getString(4));
        }

        adapter = new ArrayAdapter<String>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, listItem) {
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.rgb(72, 72, 72));
                return view;
            }
        };
        checkTimeOfDay(timeOfDay, addValueCalories[timeOfDay], addValueProtein[timeOfDay], addValueCarb[timeOfDay], addValueFat[timeOfDay]);
        checkAdapter(adapter, cardView, listView, textView);
    }

    private void addProduct(String i) {
        Intent addProduct = new Intent(getContext(), AddProductActivity.class);
        startActivity(addProduct);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PoraDnia", i); //InputString: from the EditText
        editor.apply();
    }

    private void hideOneListView(ListView listView) {
        listView.setVisibility(GONE);
    }

    private void hideAllListViews() {
        listViewBreakfast.setVisibility(GONE);
        listViewLunch.setVisibility(GONE);
        listViewDinner.setVisibility(GONE);
        listViewSnack.setVisibility(GONE);
        listViewSupper.setVisibility(GONE);
    }

    private void showListView(ListView listView) {
        listView.setVisibility(VISIBLE);
    }

    private void checkAdapter(ArrayAdapter<String> adapter, CardView cardView, ListView listView, TextView textView) {

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

    private void checkTimeOfDay(int timeOfDay, int calories, Double protein, Double carb, Double fat) {

        switch (timeOfDay) {
            case 0:
                textViewKcalS.setText(String.valueOf(calories));
                textViewWS.setText(String.valueOf(doubleFormat(carb)));
                textViewPS.setText(String.valueOf(doubleFormat(protein)));
                textViewTS.setText(String.valueOf(doubleFormat(fat)));
                break;
            case 1:
                textViewKcalL.setText(String.valueOf(calories));
                textViewWL.setText(String.valueOf(doubleFormat(carb)));
                textViewPL.setText(String.valueOf(doubleFormat(protein)));
                textViewTL.setText(String.valueOf(doubleFormat(fat)));
                break;
            case 2:
                textViewKcalO.setText(String.valueOf(calories));
                textViewWO.setText(String.valueOf(doubleFormat(carb)));
                textViewPO.setText(String.valueOf(doubleFormat(protein)));
                textViewTO.setText(String.valueOf(doubleFormat(fat)));
                break;
            case 3:
                textViewKcalP.setText(String.valueOf(calories));
                textViewWP.setText(String.valueOf(doubleFormat(carb)));
                textViewPP.setText(String.valueOf(doubleFormat(protein)));
                textViewTP.setText(String.valueOf(doubleFormat(fat)));
                break;
            case 4:
                textViewKcalK.setText(String.valueOf(calories));
                textViewWK.setText(String.valueOf(doubleFormat(carb)));
                textViewPK.setText(String.valueOf(doubleFormat(protein)));
                textViewTK.setText(String.valueOf(doubleFormat(fat)));
                break;
            default:
                // code block
        }
    }
    /*
    Ustawia componenty do poczatkowych/startowych wartosci
     */
    private void resetAllComponents() {

        Arrays.fill(maxValue, 0);
        Arrays.fill(addValueCalories, 0);
        Arrays.fill(addValueProtein, 0);
        Arrays.fill(addValueFat, 0);
        Arrays.fill(addValueCarb, 0);

        progressBarCalories.getProgressDrawable().setColorFilter(
                Color.parseColor("#551A8B"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarProtein.getProgressDrawable().setColorFilter(
                Color.parseColor("#41AECF"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarCarb.getProgressDrawable().setColorFilter(
                Color.parseColor("#00C853"), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarFat.getProgressDrawable().setColorFilter(
                Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_IN);

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

    /*
    Sumuje wszystko we wszystkich komponentach
     */
    private void sumUpEverything() {

        SharedPreferences sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String dataSportFragment = sharedPreferences.getString("optionSelected", "0"); //no id: default value
        assert dataSportFragment != null;
        int optionSportFragment = Integer.parseInt(dataSportFragment);

        int sumCalories = Integer.parseInt(textViewKcalS.getText().toString()) + Integer.parseInt(textViewKcalL.getText().toString()) + Integer.parseInt(textViewKcalO.getText().toString()) + Integer.parseInt(textViewKcalP.getText().toString()) + Integer.parseInt(textViewKcalK.getText().toString());
        double sumP = Double.parseDouble(textViewPS.getText().toString()) + Double.parseDouble(textViewPL.getText().toString()) + Double.parseDouble(textViewPO.getText().toString()) + Double.parseDouble(textViewPP.getText().toString()) + Double.parseDouble(textViewPK.getText().toString());
        double sumW = Double.parseDouble(textViewWS.getText().toString()) + Double.parseDouble(textViewWL.getText().toString()) + Double.parseDouble(textViewWO.getText().toString()) + Double.parseDouble(textViewWP.getText().toString()) + Double.parseDouble(textViewWK.getText().toString());
        double sumT = Double.parseDouble(textViewTS.getText().toString()) + Double.parseDouble(textViewTL.getText().toString()) + Double.parseDouble(textViewTO.getText().toString()) + Double.parseDouble(textViewTP.getText().toString()) + Double.parseDouble(textViewTK.getText().toString());

        double proteinS = doubleFormat(sumP);
        double fatS = doubleFormat(sumT);
        double carbS = doubleFormat(sumW);

        if (optionSportFragment == 1) {

            String dataCalories = sharedPreferences.getString("textCalories", "0");
            String dataProtein = sharedPreferences.getString("textProtein", "0");
            String dataCarb = sharedPreferences.getString("textCarb", "0");
            String dataFat = sharedPreferences.getString("textFat", "0");
            maxValue[0] = (mathematicalFormulas(dataCalories, dataProtein, 4));
            maxValue[1] = (mathematicalFormulas(dataCalories, dataCarb, 4));
            maxValue[2] = (mathematicalFormulas(dataCalories, dataFat, 9));
            assert dataCalories != null;
            caloriesSummary = Integer.parseInt(dataCalories);
        }

        if (optionSportFragment == 0) {

            String dataWeight = sharedPreferences.getString("optionWaga", "0");
            String dataAge = sharedPreferences.getString("optionWiek", "0");
            String dataHeight = sharedPreferences.getString("optionWzrost", "0");
            String dataGender = sharedPreferences.getString("optionPlec", "0");
            String dataActivity = sharedPreferences.getString("spinnerAktywnosc", "0");
            String dataKindOfSport = sharedPreferences.getString("spinnerRodzajSportu", "0");
            String dataGoal = sharedPreferences.getString("spinnerCel", "0");

            assert dataKindOfSport != null;
            assert dataWeight != null;
            assert dataHeight != null;
            assert dataAge != null;
            assert dataActivity != null;
            assert dataGoal != null;
            assert dataGender != null;

            kindOfSport = Integer.parseInt(dataKindOfSport);
            double waga = Double.parseDouble(dataWeight);
            int wzrost = Integer.parseInt(dataHeight);
            int wiek = Integer.parseInt(dataAge);

            switch (dataActivity) {
                case "0":
                    activity = 1.2;
                    break;
                case "1":
                    activity = 1.3;
                    break;
                case "2":
                    activity = 1.5;
                    break;
                case "3":
                    activity = 1.7;
                    break;
                case "4":
                    activity = 1.9;
                    break;
                default:
            }

            switch (dataGoal) {
                case "0":
                    goal = 0;
                    break;
                case "1":
                    goal = 300;
                    break;
                case "2":
                    goal = -300;
                    break;
                default:
            }
            caloriesSummary = mathematicalForSecondOption(waga, wzrost, wiek, dataGender, goal, activity);
        }

        String calories = "Kcal:\n " + caloriesSummary + "/" + sumCalories;
        String protein = "Białko:\n " + maxValue[0] + "/" + proteinS;
        String carb = "Węglowodany:\n " + maxValue[1] + "/" + carbS;
        String fat = "Tłuszcze:\n " + maxValue[2] + "/" + fatS;

        textViewAllCalories.setText(calories);
        textViewAllProtein.setText(protein);
        textViewAllCarb.setText(carb);
        textViewAllFat.setText(fat);

        setProgressBar(caloriesSummary, sumCalories, progressBarCalories);
        setProgressBar((int) (maxValue[0]), (int) (sumP), progressBarProtein);
        setProgressBar((int) (maxValue[1]), (int) (sumW), progressBarCarb);
        setProgressBar((int) (maxValue[2]), (int) (sumT), progressBarFat);

    }

    private double mathematicalFormulas(String kcal, String macro, int division) {
        double mathematicalFormulas = ((Double.parseDouble(kcal) * Double.parseDouble(macro)) / 100) / division;
        return doubleFormat(mathematicalFormulas);
    }

    private void setProgressBar(int maxValue, int currentValue, ProgressBar progressBar) {
        progressBar.setMax(maxValue);
        progressBar.setProgress(currentValue);
        if (currentValue > maxValue + 5) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FF0000"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    private int mathematicalForSecondOption(double weight, int height, int age, String gender, int goal, double activity) {

        double mathematicalFormula = 0;
        int valueToConvert;

        if (gender.equals("M"))
            mathematicalFormula = ((66.5 + (13.7 * weight) + (5 * height) - (6.8 * age)) * activity) + goal;
        else if (gender.equals("W"))
            mathematicalFormula = ((655 + (9.6 * weight) + (1.85 * height) - (4.7 * age)) * activity) + goal;

        valueToConvert = (int) mathematicalFormula;

        switch (kindOfSport) {
            case 0:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(30), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(45), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(25), 9));
                break;
            case 1:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(20), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(65), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(15), 9));
                break;
            case 2:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(25), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(60), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(15), 9));
                break;
            case 3:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(15), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(55), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(30), 9));
                break;
            default:
        }
        return valueToConvert;
    }

    private void alertOperation(final int poraDnia, final String ilosc, final String nazwa) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(requireNonNull(getContext()), R.style.Dialog);
        builder.setTitle("Zmień ilość bądź usuń produkt");
        builder.setCancelable(true);
        final EditText input = new EditText(getActivity());

        builder.setNeutralButton("Edytuj", (dialogInterface, i) -> {
            int wartoscGram = Integer.parseInt(input.getText().toString());
            if (wartoscGram == 0) {
                Toast.makeText(getContext(), "Wartość nie może wynosić 0!", Toast.LENGTH_SHORT).show();
            } else {
                editProductInDatabase(wartoscGram, poraDnia, ilosc, nazwa);
            }

        });
        builder.setPositiveButton("Usuń", (dialogInterface, i) -> deleteFromDatabase(poraDnia, ilosc, nazwa));
        builder.setNegativeButton("Anuluj", (dialogInterface, i) -> dialogInterface.cancel());

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        input.setTextColor(Color.GRAY);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(input);
        alertDialog.show();
        alertDialog.getButton(BUTTON_POSITIVE).setTextColor(Color.RED);
        alertDialog.getButton(BUTTON_NEUTRAL).setTextColor(Color.GREEN);
    }

    private void deleteFromDatabase(int idTimeOfDay, String ilosc, String nazwa) {

        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, Context.MODE_PRIVATE, null);
        int iloscCON = Integer.parseInt(ilosc.replaceAll("\\s+", ""));
        String nazwaCON = nazwa.replaceAll("\\s+$", "");

        Cursor idMeal = sqLiteDatabase.rawQuery(getMealIdByNameAndAmountFromTableMeal(nazwaCON,iloscCON),null);
        idMeal.moveToFirst();

        Cursor idHash = sqLiteDatabase.rawQuery(getElementByIdTimeOfDayAndDataAndIdMealFromHash(idTimeOfDay,textViewData.getText().toString(),idMeal.getString(0)), null);
        idHash.moveToFirst();

        Cursor delete = sqLiteDatabase.rawQuery(deleteByIdHashFromHashTable(idHash.getString(0)),null);
        delete.moveToFirst();

        idMeal.close();
        idHash.close();
        delete.close();
        sqLiteDatabase.close();
        refreshApp();
        refreshAfterDbChanged();
    }

    private void editProductInDatabase(int wartoscGram, int idTimeOfDay, String ilosc, String nazwa) {

        SQLiteDatabase baza = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, Context.MODE_PRIVATE, null);
        int iloscCON = Integer.parseInt(ilosc.replaceAll("\\s+", ""));
        String nazwaCON = nazwa.replaceAll("\\s+$", "");

        Cursor idMeal = baza.rawQuery(getMealIdByNameAndAmountFromTableMeal(nazwaCON,iloscCON),null);
        idMeal.moveToFirst();

        Cursor dataProduct = baza.rawQuery(getMealIdByDataAndIdMealFromTableMeal(textViewData.getText().toString(),idMeal.getString(0)), null);
        dataProduct.moveToFirst();

        Cursor idHash = baza.rawQuery(getElementByIdTimeOfDayAndDataAndIdMealFromHash(idTimeOfDay,textViewData.getText().toString(),idMeal.getString(0)), null);
        idHash.moveToFirst();

        int  il = Integer.parseInt(dataProduct.getString(5));
        double b = doubleFormat((wartoscGram * Double.parseDouble(dataProduct.getString(0))) / il);
        double bl = doubleFormat((wartoscGram * Double.parseDouble(dataProduct.getString(1))) / il);
        int kcal = (wartoscGram * Integer.parseInt(dataProduct.getString(2))) / il;
        double t = doubleFormat((wartoscGram * Double.parseDouble(dataProduct.getString(3))) / il);
        double w = doubleFormat((wartoscGram * Double.parseDouble(dataProduct.getString(4))) / il);
        il = wartoscGram;

        try {
            Cursor checkInMealTable = baza.rawQuery(getElementByNameAndValueFromPosilek(dataProduct.getString(6), wartoscGram), null);
            checkInMealTable.moveToFirst();

            Cursor deleteFromHashTable = baza.rawQuery(deleteElementByIdHashFromHash(idHash.getString(0)), null);
            deleteFromHashTable.moveToFirst();

            Cursor insertToHashTable = baza.rawQuery(insertElementIntoHashTable(textViewData.getText().toString(), checkInMealTable.getString(0), idTimeOfDay), null);
            insertToHashTable.moveToFirst();

            Closer.closeCursors(checkInMealTable, insertToHashTable, deleteFromHashTable);

        } catch (Exception ex) {
            Cursor insertToMealTable = baza.rawQuery(insertElementIntoMealTable(dataProduct.getString(6), b, w, t, bl, kcal, il), null);
            insertToMealTable.moveToFirst();

            Cursor checkInMealTable = baza.rawQuery(getElementByNameAndValueFromPosilek(dataProduct.getString(6), wartoscGram), null);
            checkInMealTable.moveToFirst();

            Cursor deleteFromHashTable = baza.rawQuery(deleteElementByIdHashFromHash(idHash.getString(0)), null);
            deleteFromHashTable.moveToFirst();

            Cursor insertToHashTable = baza.rawQuery(insertElementIntoHashTable(textViewData.getText().toString(), checkInMealTable.getString(0), idTimeOfDay), null);
            insertToHashTable.moveToFirst();

            Closer.closeCursors(checkInMealTable, insertToMealTable, insertToHashTable, deleteFromHashTable);
        }

        Closer.closeCursors(idHash, dataProduct, idMeal);
        baza.close();
        refreshApp();
        refreshAfterDbChanged();
    }

    private void refreshApp() {
        resetAllComponents();
        hideAllListViews();
        viewDatabase();
        sumUpEverything();
    }

    private double doubleFormat(double variables) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(variables).replace(",", "."));
    }

    private void refreshAfterDbChanged() {
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
