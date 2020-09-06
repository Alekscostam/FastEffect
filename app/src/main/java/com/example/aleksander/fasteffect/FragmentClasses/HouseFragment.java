package com.example.aleksander.fasteffect.FragmentClasses;

import android.app.AlertDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.Closer;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomAdapter;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.DataHolder;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.ResizeListView;
import com.example.aleksander.fasteffect.ProductClasses.AddProductActivity;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.DATABASE_FILE;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_HASH;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childAge;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childGender;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childHeight;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childWeight;
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
public class HouseFragment extends Fragment implements View.OnClickListener, ValueEventListener {

    public static final String TAG = "com.example.aleksander.fasteffect.FragmentClass";

    public static final String SHARED_PREFS = "shaaredPrefs";

    SharedPreferences sharedPreferences;

    private int caloriesSummary;
    private int[] hide;
    private int[] addValueCalories;
    private double[] addValueProtein;
    private double[] addValueFat;
    private double[] addValueCarb;
    private double[] maxValue;

    private String userGender;
    private String userAge;
    private String userWeight;
    private String userHeight;

    private double activity;
    private int kindOfSport;
    private int goal;
    private TextView textViewData;

    private ProgressBar progressBarCalories, progressBarProtein, progressBarCarb, progressBarFat;
    private List<TextView> breakfastListMacro, lunchListMacro, dinnerListMacro, snackListMacro, supperListMacro;
    private TextView textViewAllCalories, textViewAllProtein, textViewAllCarb, textViewAllFat;

    private List<ListView> listViewList;
    private List<CardView> cardViewList;
    private List<TextView> textViews;
    private List<ArrayList<String>> arrayItemList;
    private List<ArrayAdapter<String>> arrayAdaptersForList;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_house, container, false);


        String referenceName = "Users";
        DatabaseReference databaseReference = database.getReference(referenceName);
        databaseReference.addValueEventListener(this);

        initViews(view);

        ImageButton buttonAddProductBreakfast = view.findViewById(R.id.buttonAddProductBreakfast);
        buttonAddProductBreakfast.setOnClickListener(this);
        ImageButton buttonAddProductLunch = view.findViewById(R.id.buttonAddProductLunch);
        buttonAddProductLunch.setOnClickListener(this);
        ImageButton buttonAddProductDinner = view.findViewById(R.id.buttonAddProductDinner);
        buttonAddProductDinner.setOnClickListener(this);
        ImageButton buttonAddProductSnack = view.findViewById(R.id.buttonAddProductSnack);
        buttonAddProductSnack.setOnClickListener(this);
        ImageButton buttonAddProductSupper = view.findViewById(R.id.buttonAddProductSupper);
        buttonAddProductSupper.setOnClickListener(this);

        listViewList.get(0).setOnItemClickListener((adapterView, breakfast, positionValue, l) ->
                alertOperation(1, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewList.get(1).setOnItemClickListener((adapterView, lunch, positionValue, l) ->
                alertOperation(2, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewList.get(2).setOnItemClickListener((adapterView, dinner, positionValue, l) ->
                alertOperation(3, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewList.get(3).setOnItemClickListener((adapterView, snack, positionValue, l) ->
                alertOperation(4, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));
        listViewList.get(4).setOnItemClickListener((adapterView, supper, positionValue, l) ->
                alertOperation(5, getAmountFromValue((String) adapterView.getItemAtPosition(positionValue)), getNameFromValue((String) adapterView.getItemAtPosition(positionValue))));

        cardViewList.get(0).setOnClickListener(cardBreakfast -> cardViewShowOrHide(0, listViewList.get(0)));
        cardViewList.get(1).setOnClickListener(cardLunch -> cardViewShowOrHide(1, listViewList.get(1)));
        cardViewList.get(2).setOnClickListener(cardDinner -> cardViewShowOrHide(2, listViewList.get(2)));
        cardViewList.get(3).setOnClickListener(cardSnack -> cardViewShowOrHide(3, listViewList.get(3)));
        cardViewList.get(4).setOnClickListener(cardSupper -> cardViewShowOrHide(4, listViewList.get(4)));

        if (DataHolder.getInstance().getData().equals(""))
            setDate();
        else
            textViewData.setText(DataHolder.getInstance().getData());

        textViewData.setOnClickListener(dataView -> setOnViewDataField());

        mDateSetListener = this::dataSetListener;

        return view;
    }


    /**
     * Decyduje do jakiej pory maja byc zapisywane produkty
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddProductBreakfast:
                addProduct("1");
                break;
            case R.id.buttonAddProductLunch:
                addProduct("2");
                break;
            case R.id.buttonAddProductDinner:
                addProduct("3");
                break;
            case R.id.buttonAddProductSnack:
                addProduct("4");
                break;
            case R.id.buttonAddProductSupper:
                addProduct("5");
                break;
            default:
                break;
        }
    }

    /**
     * Funkcja pokazuje kalendarz w wybranym formacie daty
     */
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

        for (int i = 0; i < arrayAdaptersForList.size(); i++) {
            checkAdapter(arrayAdaptersForList.get(i), cardViewList.get(i), listViewList.get(i), textViews.get(i));
        }
        dialog.show();
    }

    /**
     * Zapamietuje wybraną date przez uzytkownika do pamieci telefonu aby po zamkniecu aplikacji nie zmieniała sie na aktualną
     */
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

    /**
     * Metoda, która decyduje o pokazaniu lub ukryciu listView
     */
    private void cardViewShowOrHide(int index, ListView listView) {
        if (hide[index] == 0) {
            listView.setVisibility(VISIBLE);
            hide[index] = 1;
        } else {
            listView.setVisibility(GONE);
            hide[index] = 0;
        }
    }

    /**
     * Pobiera ilosc produktu ze stringa
     */
    private String getAmountFromValue(String itemAtPosition) {
        String substringFromItemAtPosition = itemAtPosition.substring(itemAtPosition.indexOf("Ilość:") + 6);
        return substringFromItemAtPosition.replaceAll("\\s+", "");
    }

    /**
     * Pobiera nazwe produktu ze stringa
     */
    private String getNameFromValue(String itemAtPosition) {
        return itemAtPosition.substring(0, itemAtPosition.indexOf(","));
    }

    /**
     * Ustawia date w odpowiednim formacie i dodaje ją do bazy danych
     */
    private void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = sdfDate.format(today);
        textViewData.setText(date);
        addToDatabase(date);
        DataHolder.getInstance().setData(date);
    }

    /**
     * Dodaje date do bazy danych
     */
    private void addToDatabase(String dateSend) {
        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, android.content.Context.MODE_PRIVATE, null);
        ContentValues content = new ContentValues();
        content.put(TABLE_HASH, dateSend);
        sqLiteDatabase.insert(TABLE_HASH, null, content);
        sqLiteDatabase.close();
    }

    /**
     * Sluzy do odczytu produktow z bazy danych do okna głównego aplikacji
     */
    private void viewDatabase() {

        Log.i(TAG, "viewDatabase - odczyt produktów z bazy danych");

        clearListAndSetAdapter(arrayAdaptersForList);

        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, android.content.Context.MODE_PRIVATE, null);

        for (int id = 1; id <= 5; id++) {

            Cursor selectFromMeal = sqLiteDatabase.rawQuery(
                    getElementByIdTimeOfDayAndDataFromAllTables(id, textViewData.getText().toString()), null);

            if ((selectFromMeal.getCount() != 0)) {
                switch (id) {
                    case 1:
                        pickDinner(selectFromMeal, arrayItemList.get(0), listViewList.get(0), cardViewList.get(0), textViews.get(0), 0);
                        break;
                    case 2:
                        pickDinner(selectFromMeal, arrayItemList.get(1), listViewList.get(1), cardViewList.get(1), textViews.get(1), 1);
                        break;
                    case 3:
                        pickDinner(selectFromMeal, arrayItemList.get(2), listViewList.get(2), cardViewList.get(2), textViews.get(2), 2);
                        break;
                    case 4:
                        pickDinner(selectFromMeal, arrayItemList.get(3), listViewList.get(3), cardViewList.get(3), textViews.get(3), 3);
                        break;
                    default:
                        pickDinner(selectFromMeal, arrayItemList.get(4), listViewList.get(4), cardViewList.get(4), textViews.get(4), 4);
                }
            }
        }
        sqLiteDatabase.close();
    }

    /**
     * Clearuje liste i ustawia adapter
     *
     * @param adapter ktory zostaje ustawiony na liscie
     */
    private void clearListAndSetAdapter(List<ArrayAdapter<String>> adapter) {
        for (int i = 0; i < arrayItemList.size(); i++) {
            arrayItemList.get(i).clear();
            listViewList.get(i).setAdapter(adapter.get(i));
        }
    }

    /**
     * Podstawowa metoda sluzaca do wybrania i wykonania operacji na konkretnym produkcie
     *
     * @param cursor    okresla elementy z bazy
     * @param listItem  lista elementow
     * @param listView  lista wyswietalania produktow
     * @param cardView  na nim pokazuja sie wszystkie elementy
     * @param textView  elementy na cardView
     * @param timeOfDay pora dnia wybranego danego elementu
     */
    private void pickDinner(Cursor cursor, List<String> listItem, ListView listView, CardView cardView,
                            TextView textView, int timeOfDay) {

        ArrayAdapter<String> adapter;
        while (cursor.moveToNext()) {

            listItem.add(
                    cursor.getString(0) + ",  Kalorie: " +
                            cursor.getString(1) + ",  Białko: " +
                            cursor.getString(2) + ",  Węglowodany: " +
                            cursor.getString(3) + ",  Tłuszcze: " +
                            cursor.getString(4) + ",  Błonnik: " +
                            cursor.getString(5) + ",  Ilość: " +
                            cursor.getString(6));

            addValueCalories[timeOfDay] = addValueCalories[timeOfDay] + Integer.parseInt(cursor.getString(1));
            addValueProtein[timeOfDay] = addValueProtein[timeOfDay] + Double.parseDouble(cursor.getString(2));
            addValueCarb[timeOfDay] = addValueCarb[timeOfDay] + Double.parseDouble(cursor.getString(3));
            addValueFat[timeOfDay] = addValueFat[timeOfDay] + Double.parseDouble(cursor.getString(4));
        }

        adapter = new CustomAdapter(requireNonNull(getContext()), listItem);
        setMacroForChosenTimeOfDay(timeOfDay, addValueCalories[timeOfDay], addValueProtein[timeOfDay], addValueCarb[timeOfDay], addValueFat[timeOfDay]);
        checkAdapter(adapter, cardView, listView, textView);
    }

    /**
     * Sluzy do przejscia na strone dodawania produktu
     *
     * @param index okresla pore dnia do ktorej zostanie dodany produkt
     */
    private void addProduct(String index) {
        Intent addProduct = new Intent(getContext(), AddProductActivity.class);
        startActivity(addProduct);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PoraDnia", index); //InputString: from the EditText
        editor.apply();
    }


    /**
     * Sprawdza adapter na podstawie jego zawartosci i okresla odpowiedni wyglad dla cardView i textView
     */
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

    /**
     * Wywoluje metode setListMacro dla konkretnej pory dnia i przekazuje do niej makroskladniki
     */
    private void setMacroForChosenTimeOfDay(int timeOfDay, int calories, Double protein, Double carb, Double fat) {

        switch (timeOfDay) {
            case 0:
                setListMacro(breakfastListMacro, calories, protein, carb, fat);
                break;
            case 1:
                setListMacro(lunchListMacro, calories, protein, carb, fat);
                break;
            case 2:
                setListMacro(dinnerListMacro, calories, protein, carb, fat);
                break;
            case 3:
                setListMacro(snackListMacro, calories, protein, carb, fat);
                break;
            case 4:
                setListMacro(supperListMacro, calories, protein, carb, fat);
                break;
            default:
                break;
        }
    }

    /**
     * Wstawia text dla elementow listy
     */
    private void setListMacro(List<TextView> listMacro, int calories, Double protein, Double carb, Double fat) {
        listMacro.get(0).setText(String.valueOf(calories));
        listMacro.get(1).setText(String.valueOf(doubleFormatWithDot(carb)));
        listMacro.get(2).setText(String.valueOf(doubleFormatWithDot(protein)));
        listMacro.get(3).setText(String.valueOf(doubleFormatWithDot(fat)));
    }

    /**
     * Ustawia componenty do poczatkowych/startowych wartosci
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

        breakfastListMacro.forEach(element -> element.setText("0"));
        lunchListMacro.forEach(element -> element.setText("0"));
        dinnerListMacro.forEach(element -> element.setText("0"));
        snackListMacro.forEach(element -> element.setText("0"));
        supperListMacro.forEach(element -> element.setText("0"));

        textViewAllCalories.setText("0");
        textViewAllProtein.setText("0");
        textViewAllCarb.setText("0");
        textViewAllFat.setText("0");
    }

    /**
     * Sumuje wszystko we wszystkich komponentach
     */
    private void sumUpEverything() {

        Log.i(TAG, "sumUpEverything - sumowanie wszytskich komponentow");

        String dataSportFragment = sharedPreferences.getString("optionSelected", "0");
        assert dataSportFragment != null;
        int optionSportFragment = Integer.parseInt(dataSportFragment);

        int sumCalories = Integer.parseInt(breakfastListMacro.get(0).getText().toString()) + Integer.parseInt(lunchListMacro.get(0).getText().toString()) + Integer.parseInt(dinnerListMacro.get(0).getText().toString()) + Integer.parseInt(snackListMacro.get(0).getText().toString()) + Integer.parseInt(supperListMacro.get(0).getText().toString());
        double sumCarbs = Double.parseDouble(breakfastListMacro.get(1).getText().toString()) + Double.parseDouble(lunchListMacro.get(1).getText().toString()) + Double.parseDouble(dinnerListMacro.get(1).getText().toString()) + Double.parseDouble(snackListMacro.get(1).getText().toString()) + Double.parseDouble(supperListMacro.get(1).getText().toString());
        double sumProtein = Double.parseDouble(breakfastListMacro.get(2).getText().toString()) + Double.parseDouble(lunchListMacro.get(2).getText().toString()) + Double.parseDouble(dinnerListMacro.get(2).getText().toString()) + Double.parseDouble(snackListMacro.get(2).getText().toString()) + Double.parseDouble(supperListMacro.get(2).getText().toString());
        double sumFats = Double.parseDouble(breakfastListMacro.get(3).getText().toString()) + Double.parseDouble(lunchListMacro.get(3).getText().toString()) + Double.parseDouble(dinnerListMacro.get(3).getText().toString()) + Double.parseDouble(snackListMacro.get(3).getText().toString()) + Double.parseDouble(supperListMacro.get(3).getText().toString());

        double proteinS = doubleFormatWithDot(sumProtein);
        double fatS = doubleFormatWithDot(sumFats);
        double carbS = doubleFormatWithDot(sumCarbs);

        if (optionSportFragment == 1) {

            String dataCalories = sharedPreferences.getString("textCalories", "2000");
            String dataProtein = sharedPreferences.getString("textProtein", "20");
            String dataCarb = sharedPreferences.getString("textCarb", "50");
            String dataFat = sharedPreferences.getString("textFat", "30");
            maxValue[0] = (mathematicalFormulas(dataCalories, dataProtein, 4));
            maxValue[1] = (mathematicalFormulas(dataCalories, dataCarb, 4));
            maxValue[2] = (mathematicalFormulas(dataCalories, dataFat, 9));
            assert dataCalories != null;
            caloriesSummary = Integer.parseInt(dataCalories);
        }

        if (optionSportFragment == 0) {

            double weight = Double.parseDouble(userWeight);
            int height = Integer.parseInt(userHeight);
            int age = Integer.parseInt(userAge);

            optionsCheckerSport();
            optionsCheckerActivity();
            optionsCheckerGoal();

            caloriesSummary = mathematicalForSecondOption(weight, height, age, userGender, goal, activity);
        }
        String calories = "Kcal:\n " + sumCalories + "/" + caloriesSummary;
        String protein = "Białko:\n " + proteinS + "/" + maxValue[0];
        String carb = "Węglowodany:\n " + carbS + "/" + maxValue[1];
        String fat = "Tłuszcze:\n " + fatS + "/" + maxValue[2];

        textViewAllCalories.setText(calories);
        textViewAllProtein.setText(protein);
        textViewAllCarb.setText(carb);
        textViewAllFat.setText(fat);

        setProgressBar(caloriesSummary, sumCalories, progressBarCalories);
        setProgressBar((int) (maxValue[0]), (int) (sumProtein), progressBarProtein);
        setProgressBar((int) (maxValue[1]), (int) (sumCarbs), progressBarCarb);
        setProgressBar((int) (maxValue[2]), (int) (sumFats), progressBarFat);
    }

    /**
     * SharedPreferences dla rodzaju sportu
     */
    private void optionsCheckerSport() {
        String strengthSport = getResources().getString(R.string.strengthSport);
        String enduranceSport = getResources().getString(R.string.enduranceSport);
        String mixedSport = getResources().getString(R.string.mixedSport);
        String noSport = getResources().getString(R.string.noSport);

        String rbSport = sharedPreferences.getString("rbSport", strengthSport);

        assert rbSport != null;
        if (rbSport.equals(strengthSport))
            kindOfSport = 0;
        else if (rbSport.equals(enduranceSport))
            kindOfSport = 1;
        else if (rbSport.equals(mixedSport))
            kindOfSport = 2;
        else if (rbSport.equals(noSport))
            kindOfSport = 3;
    }

    /**
     * SharedPreferences dla aktywnosci ogolnej
     */
    private void optionsCheckerActivity() {
        String noActivity = getResources().getString(R.string.noActivity);
        String lowActivity = getResources().getString(R.string.lowActivity);
        String mediumActivity = getResources().getString(R.string.mediumActivity);
        String highActivity = getResources().getString(R.string.highActivity);
        String veryHighActivity = getResources().getString(R.string.veryHighActivity);

        String rbActivity = sharedPreferences.getString("rbActivity", lowActivity);

        assert rbActivity != null;
        if (rbActivity.equals(noActivity))
            activity = 1.2;
        else if (rbActivity.equals(lowActivity))
            activity = 1.3;
        else if (rbActivity.equals(mediumActivity))
            activity = 1.5;
        else if (rbActivity.equals(highActivity))
            activity = 1.7;
        else if (rbActivity.equals(veryHighActivity))
            activity = 1.9;
    }

    /**
     * SharedPreferences dla celu
     */
    private void optionsCheckerGoal() {
        String keepWeight = getResources().getString(R.string.keepWeight);
        String increaseWeight = getResources().getString(R.string.increaseWeight);
        String reductionWeight = getResources().getString(R.string.reductionWeight);

        String rbGoal = sharedPreferences.getString("rbGoal", keepWeight);

        assert rbGoal != null;
        if (rbGoal.equals(keepWeight))
            goal = 0;
        else if (rbGoal.equals(increaseWeight))
            goal = 300;
        else if (rbGoal.equals(reductionWeight))
            goal = -300;
    }

    /**
     * Funkcja pomocnicza, która dokonuje obliczen i zwraca wynik funkcji doubleFormatWithDot
     */
    private double mathematicalFormulas(String calories, String macro, int division) {
        double mathematicalFormulas = ((Double.parseDouble(calories) * Double.parseDouble(macro)) / 100) / division;
        return doubleFormatWithDot(mathematicalFormulas);
    }

    /**
     * Ustawienia dotyczace ProgressBar
     */
    private void setProgressBar(int maxValue, int currentValue, ProgressBar progressBar) {
        Log.i(TAG, "setProgressBar - ustawienie progressBar");

        progressBar.setMax(maxValue);
        progressBar.setProgress(currentValue);
        if (currentValue > maxValue + 5) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FF0000"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * Wzor na druga opcje wybrana przez uzytkownika w sekcji "Aktywnosc"
     */
    private int mathematicalForSecondOption(double weight, int height, int age, String gender, int goal, double activity) {
        Log.i(TAG, "mathematicalForSecondOption - druga opcja wyboru sposobu wyliaczania kalori i makroskładników");
        double mathematicalFormula = 0;
        int valueToConvert;

        if (gender.equals("M"))
            mathematicalFormula = ((66.5 + (13.7 * weight) + (5 * height) - (6.8 * age)) * activity) + goal;
        else if (gender.equals("W"))
            mathematicalFormula = ((655 + (9.6 * weight) + (1.85 * height) - (4.7 * age)) * activity) + goal;

        valueToConvert = (int) mathematicalFormula;

        switch (kindOfSport) {
            case 0:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(22), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(50), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(28), 9));
                break;
            case 1:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(17), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(61), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(22), 9));
                break;
            case 2:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(20), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(55), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(25), 9));
                break;
            case 3:
                maxValue[0] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(15), 4));
                maxValue[1] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(55), 4));
                maxValue[2] = (mathematicalFormulas(String.valueOf(valueToConvert), String.valueOf(30), 9));
                break;
            default:
                break;
        }
        return valueToConvert;
    }

    /**
     * Alert dotyczacy operacji po kliknieciu na dany produkt
     *
     * @param timeOfDay   dla ktorej ma zostac wybrana operacja
     * @param amount      ktorą, byc moze nalezy zmodyfikowac lub usunąc
     * @param productName produktu na ktorym ma zostac wybrana konkretna operacja
     */
    private void alertOperation(final int timeOfDay, final String amount, final String productName) {
        Log.i(TAG, "alertOperation - Wywołanie alertu");

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.custom_alert_dialog_house, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);

        final EditText input = promptsView.findViewById(R.id.inputNumber);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Usuń", (dialogInterface, delete) -> deleteFromDatabase(timeOfDay, amount, productName))
                .setNegativeButton("Anuluj", (dialogInterface, cancel) -> dialogInterface.cancel())
                .setNeutralButton("Edytuj", (dialogInterface, edit) -> {
                    try {
                        int amountValue = Integer.parseInt(input.getText().toString());
                        if (amountValue == 0) {
                            Toast.makeText(getContext(), "Wartość nie może wynosić 0!", Toast.LENGTH_LONG).show();
                        } else {
                            editProductInDatabase(amountValue, timeOfDay, amount, productName);
                        }
                    }catch (NumberFormatException nfe)
                    {
                        nfe.getMessage();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Funkcja slużaca do usuwania produktu z bazy danych, a w konsekwencji usuniecia jej z listy produktow z głównej strony aplikacji
     */
    private void deleteFromDatabase(int idTimeOfDay, String amount, String name) {

        Log.i(TAG, "deleteFromDatabase - usuwanie produktu z aplikacji");

        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, Context.MODE_PRIVATE, null);
        int amountReplace = Integer.parseInt(amount.replaceAll("\\s+", ""));
        String nameReplace = name.replaceAll("\\s+$", "");

        Cursor idMeal = sqLiteDatabase.rawQuery(getMealIdByNameAndAmountFromTableMeal(nameReplace, amountReplace), null);
        idMeal.moveToFirst();

        Cursor idHash = sqLiteDatabase.rawQuery(getElementByIdTimeOfDayAndDataAndIdMealFromHash(idTimeOfDay, textViewData.getText().toString(), idMeal.getString(0)), null);
        idHash.moveToFirst();

        Cursor delete = sqLiteDatabase.rawQuery(deleteByIdHashFromHashTable(idHash.getString(0)), null);
        delete.moveToFirst();

        Closer.closeCursors(idHash, idMeal, delete);
        sqLiteDatabase.close();
        refreshApp();
        refreshAfterDbChanged();
    }

    /**
     * Funkcja slużaca do edytowania produktu z bazy danych, a w konsekwencji zmodyfikwoania jej wartosci na liscie produktow
     */
    private void editProductInDatabase(int amountOfProduct, int idTimeOfDay, String ilosc, String nazwa) {

        Log.i(TAG, "editProductInDatabase - edytowanie produktu z aplikacji");

        SQLiteDatabase sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_FILE, Context.MODE_PRIVATE, null);
        int iloscCON = Integer.parseInt(ilosc.replaceAll("\\s+", ""));
        String nazwaCON = nazwa.replaceAll("\\s+$", "");

        Cursor idMeal = sqLiteDatabase.rawQuery(getMealIdByNameAndAmountFromTableMeal(nazwaCON, iloscCON), null);
        idMeal.moveToFirst();

        Cursor dataProduct = sqLiteDatabase.rawQuery(getMealIdByDataAndIdMealFromTableMeal(textViewData.getText().toString(), idMeal.getString(0)), null);
        dataProduct.moveToFirst();

        Cursor idHash = sqLiteDatabase.rawQuery(getElementByIdTimeOfDayAndDataAndIdMealFromHash(idTimeOfDay, textViewData.getText().toString(), idMeal.getString(0)), null);
        idHash.moveToFirst();

        int il = Integer.parseInt(dataProduct.getString(5));
        double b = doubleFormatWithDot((amountOfProduct * Double.parseDouble(dataProduct.getString(0))) / il);
        double bl = doubleFormatWithDot((amountOfProduct * Double.parseDouble(dataProduct.getString(1))) / il);
        int kcal = (amountOfProduct * Integer.parseInt(dataProduct.getString(2))) / il;
        double t = doubleFormatWithDot((amountOfProduct * Double.parseDouble(dataProduct.getString(3))) / il);
        double w = doubleFormatWithDot((amountOfProduct * Double.parseDouble(dataProduct.getString(4))) / il);
        il = amountOfProduct;

        try {
            Cursor checkInMealTable = sqLiteDatabase.rawQuery(getElementByNameAndValueFromPosilek(dataProduct.getString(6), amountOfProduct), null);
            checkInMealTable.moveToFirst();

            Cursor deleteFromHashTable = sqLiteDatabase.rawQuery(deleteElementByIdHashFromHash(idHash.getString(0)), null);
            deleteFromHashTable.moveToFirst();

            Cursor insertToHashTable = sqLiteDatabase.rawQuery(insertElementIntoHashTable(textViewData.getText().toString(), checkInMealTable.getString(0), idTimeOfDay), null);
            insertToHashTable.moveToFirst();

            Closer.closeCursors(checkInMealTable, insertToHashTable, deleteFromHashTable);

        } catch (Exception ex) {
            Cursor insertToMealTable = sqLiteDatabase.rawQuery(insertElementIntoMealTable(dataProduct.getString(6), b, w, t, bl, kcal, il), null);
            insertToMealTable.moveToFirst();

            Cursor checkInMealTable = sqLiteDatabase.rawQuery(getElementByNameAndValueFromPosilek(dataProduct.getString(6), amountOfProduct), null);
            checkInMealTable.moveToFirst();

            Cursor deleteFromHashTable = sqLiteDatabase.rawQuery(deleteElementByIdHashFromHash(idHash.getString(0)), null);
            deleteFromHashTable.moveToFirst();

            Cursor insertToHashTable = sqLiteDatabase.rawQuery(insertElementIntoHashTable(textViewData.getText().toString(), checkInMealTable.getString(0), idTimeOfDay), null);
            insertToHashTable.moveToFirst();

            Closer.closeCursors(checkInMealTable, insertToMealTable, insertToHashTable, deleteFromHashTable);
        }

        Closer.closeCursors(idHash, dataProduct, idMeal);
        sqLiteDatabase.close();
        refreshApp();
        refreshAfterDbChanged();
    }

    /**
     * Funkcja slużaca do refreshowania aplikacji po konkretnych operacjach
     */
    private void refreshApp() {
        resetAllComponents();
        listViewList.forEach(element -> element.setVisibility(GONE));
        try {
            viewDatabase();
        } catch (IllegalStateException | NullPointerException exceptions) {
            exceptions.getMessage();
        }
        sumUpEverything();
    }

    /**
     * Ustawia odpowiedni format i zamienia kropke na przecinek.
     */
    private double doubleFormatWithDot(double variables) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(variables).replace(",", "."));
    }

    /**
     * metoda która refresh'uje zawartosc w oknie głównym po zmianie wartosci w bazie danych
     */
    private void refreshAfterDbChanged() {
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    /**
     * Inicjuje komponenty tej klasy
     */
    private void initViews(View view) {

        sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        textViewData = view.findViewById(R.id.textViewData);

        caloriesSummary = 0;
        hide = new int[]{0, 0, 0, 0, 0};

        addValueCalories = new int[5];
        addValueProtein = new double[5];
        addValueFat = new double[5];
        addValueCarb = new double[5];
        maxValue = new double[5];

        progressBarCalories = view.findViewById(R.id.progressBarCalories);
        progressBarProtein = view.findViewById(R.id.progressBarProtein);
        progressBarCarb = view.findViewById(R.id.progressBarCarb);
        progressBarFat = view.findViewById(R.id.progressBarFat);

        textViewAllCalories = view.findViewById(R.id.textViewAllCalories);
        textViewAllProtein = view.findViewById(R.id.textViewAllProtein);
        textViewAllCarb = view.findViewById(R.id.textViewAllCarb);
        textViewAllFat = view.findViewById(R.id.textViewAllFat);

        breakfastListMacro = Arrays.asList(
                view.findViewById(R.id.KcalS),
                view.findViewById(R.id.WS),
                view.findViewById(R.id.PS),
                view.findViewById(R.id.TS));

        lunchListMacro = Arrays.asList(
                view.findViewById(R.id.KcalL),
                view.findViewById(R.id.WL),
                view.findViewById(R.id.PL),
                view.findViewById(R.id.TL));

        dinnerListMacro = Arrays.asList(
                view.findViewById(R.id.KcalO),
                view.findViewById(R.id.WO),
                view.findViewById(R.id.PO),
                view.findViewById(R.id.TO));

        snackListMacro = Arrays.asList(
                view.findViewById(R.id.KcalP),
                view.findViewById(R.id.WP),
                view.findViewById(R.id.PP),
                view.findViewById(R.id.TP));

        supperListMacro = Arrays.asList(
                view.findViewById(R.id.KcalK),
                view.findViewById(R.id.WK),
                view.findViewById(R.id.PK),
                view.findViewById(R.id.TK));

        arrayItemList = Arrays.asList(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        textViews = Arrays.asList(
                view.findViewById(R.id.textViewSniadanie),
                view.findViewById(R.id.textViewLunch),
                view.findViewById(R.id.textViewObiad),
                view.findViewById(R.id.textViewPrzekąska),
                view.findViewById(R.id.textViewKolacja));

        cardViewList = Arrays.asList(
                view.findViewById(R.id.cardViewSniadanie),
                view.findViewById(R.id.cardViewLunch),
                view.findViewById(R.id.cardViewObiad),
                view.findViewById(R.id.cardViewPrzekąska),
                view.findViewById(R.id.cardViewKolacja));

        listViewList = Arrays.asList(
                view.findViewById(R.id.ListViewSniadanie),
                view.findViewById(R.id.ListViewLunch),
                view.findViewById(R.id.ListViewObiad),
                view.findViewById(R.id.ListViewPrzekąska),
                view.findViewById(R.id.ListViewKolacja));

        arrayAdaptersForList = Arrays.asList(
                new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayItemList.get(0)),
                new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayItemList.get(1)),
                new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayItemList.get(2)),
                new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayItemList.get(3)),
                new ArrayAdapter<>(requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayItemList.get(4)));
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();

        userAge = dataSnapshot.child(uid).child(childAge).getValue(String.class);
        userWeight = dataSnapshot.child(uid).child(childWeight).getValue(String.class);
        userHeight = dataSnapshot.child(uid).child(childHeight).getValue(String.class);
        userGender = String.valueOf(dataSnapshot.child(uid).child(childGender).getValue(String.class));

        refreshApp();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        databaseError.getMessage();
    }
}
