package com.example.aleksander.fasteffect.ProductClasses;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomAdapter;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.DataHolder;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.ResizeListView;
import com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.Product;
import com.example.aleksander.fasteffect.AdditionalClasses.Interfaces.OnChildAddedEventListener;
import com.example.aleksander.fasteffect.AdditionalClasses.Interfaces.TextWatcherFilter;
import com.example.aleksander.fasteffect.MainActivity;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.DATABASE_FILE;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.HASH_DATA;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.HASH_ID_MEAL;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.HASH_ID_TIME_OF_DAY;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_AMOUNT_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_CALORIES_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_CARB_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_FAT_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_FIBRE_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_ID_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_NAME_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_PROTEIN_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_HASH;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_MEAL;
import static java.util.Objects.requireNonNull;


/**
 * Klasa służąca do odbierania danych ze zdalnej bazy danych do lokalnej bazy danych
 */
public class AddProductActivity extends AppCompatActivity {

    public static final String TAG= "com.example.aleksander.fasteffect.ProductClasses";

    private String dateOpen;
    private String timeOfDay;

    private String replaceCalories;
    private String replaceProtein;
    private String replaceFat;
    private String replaceCarb;
    private String replaceFibre;
    private String stringOfName;

    private DatabaseReference databaseReference;
    private List<String> linkedList = new LinkedList<>();

    ListView listViewProducts;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Produkty");

        TextView textViewBack = findViewById(R.id.textViewOpis);
        EditText editTextFilter = findViewById(R.id.editTextSearch);

        ImageButton imageButtonAdd = findViewById(R.id.buttonAdd);

        dateOpen = DataHolder.getInstance().getData();
        SharedPreferences prefsTimeOfDay = PreferenceManager.getDefaultSharedPreferences(this);
        timeOfDay = prefsTimeOfDay.getString("PoraDnia", "no id"); //no id: default value
        listViewProducts = findViewById(R.id.listViewProdukty);
        listViewProducts.setOnItemClickListener((adapterView, view, i, l) -> {

            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.custom_alert_dialog_add_product, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText userInput = (EditText) promptsView.findViewById(R.id.inputNumber);
            final String selectedItem = (String) adapterView.getItemAtPosition(i);
            builder.setView(promptsView);

            builder
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialogInterface, positive) -> {
                        String amount = userInput.getText().toString();
                        arithmeticOperationsForVariables(selectedItem, amount);
                        addProductToDatabase(amount);
                    })
                    .setNegativeButton("Anuluj", (dialogInterface, negative) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            requireNonNull(alertDialog.getWindow()).setLayout(450, 350);
            alertDialog.show();
        });

        editTextFilter.addTextChangedListener((TextWatcherFilter) (charSequence, start, count, after) -> (AddProductActivity.this)
                .arrayAdapter
                .getFilter()
                .filter(charSequence.toString()));


        textViewBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
        });

        imageButtonAdd.setOnClickListener(view -> {
            Intent addIntent = new Intent(getApplicationContext(), AddNewProductActivity.class);
            startActivity(addIntent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart - Pobieranie wartosci ze zdalnej bazy danych");
        executorServiceMethod();
        adapterInit();

    }

    /**
     * ExecutorService, który wykonuję tę metode w celu asynchronicznego pobrania danych ze zdalenj bazy danych
     */
    private void executorServiceMethod() {

        databaseReference.addChildEventListener((OnChildAddedEventListener) (dataSnapshot, s) -> {
            StringBuilder stringBuilderValue = new StringBuilder();

            stringBuilderValue.setLength(0);
            final List<StringBuilder> listModify = new ArrayList<>();

            stringBuilderValue
                    .append(dataSnapshot.getKey())
                    .append(dataSnapshot.getValue(Product.class));

            listModify.add(stringBuilderValue);
            linkedList.add(String.valueOf(listModify.get(0)));
            arrayAdapter.notifyDataSetChanged();
            ResizeListView resizeListView = new ResizeListView();
            resizeListView.resize(listViewProducts);
        });
    }

    private void adapterInit() {
        arrayAdapter = new CustomAdapter(this, linkedList);
        listViewProducts.setAdapter(arrayAdapter);
    }

    /**
     * Zapisuje produkt do lokalnej bazy danych
     *
     * @param amount to ilosc produktu , który ma zostac dodany
     */
    private void addProductToDatabase(String amount) {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(DATABASE_FILE,
                android.content.Context.MODE_PRIVATE, null);

        ContentValues rowProduct = new ContentValues();
        ContentValues rowHash = new ContentValues();

        rowProduct.put(MEAL_NAME_COLUMN, stringOfName);
        rowProduct.put(MEAL_PROTEIN_COLUMN, replaceProtein);
        rowProduct.put(MEAL_CARB_COLUMN, replaceCarb);
        rowProduct.put(MEAL_FAT_COLUMN, replaceFat);
        rowProduct.put(MEAL_FIBRE_COLUMN, replaceFibre);
        rowProduct.put(MEAL_CALORIES_COLUMN, replaceCalories);
        rowProduct.put(MEAL_AMOUNT_COLUMN, Integer.valueOf(amount));

        rowHash.put(HASH_ID_TIME_OF_DAY, Integer.valueOf(timeOfDay));
        rowHash.put(HASH_DATA, dateOpen);

        String[] columns = {MEAL_ID_COLUMN, MEAL_NAME_COLUMN,
                MEAL_AMOUNT_COLUMN};

        String where = "Nazwa=? AND Ilość=?";
        String[] args = {stringOfName, amount};
        Cursor k = null;
        try {
            k = sqLiteDatabase.query(TABLE_MEAL, columns, where, args, null, null, null);
            k.moveToFirst();
            rowHash.put(HASH_ID_MEAL, Integer.valueOf(k.getString(0)));
            sqLiteDatabase.insert(TABLE_HASH, null, rowHash);
        } catch (Exception ex) {
            sqLiteDatabase.insert(TABLE_MEAL, null, rowProduct);
            k = sqLiteDatabase.query(TABLE_MEAL, columns, where, args, null, null, null);
            k.moveToFirst();
            rowHash.put(HASH_ID_MEAL, Integer.valueOf(k.getString(0)));
            sqLiteDatabase.insert(TABLE_HASH, null, rowHash);

        } finally {
            sqLiteDatabase.close();
            assert k != null;
            k.close();
        }
    }

    /**
     * Operacje arytmetyczne na wartosciach wybranego produktu, ktore musza zostac wykonane przed ich zapisaniem do lokalnej bazy danych
     *
     * @param selectedItem okresla wybrany produkt
     * @param amount       ilosc wybranego produktu
     */
    private void arithmeticOperationsForVariables(String selectedItem, String amount) {
        double converterValue = (Double.parseDouble(amount) / 100);
        DecimalFormat df = new DecimalFormat("#.#");

        int indexCarbs = selectedItem.indexOf("Węglowodany:") + 13;
        int indexProtein = selectedItem.indexOf("Białko:") + 8;
        int indexFat = selectedItem.indexOf("Tłuszcze:") + 10;
        int indexFibre = selectedItem.indexOf("Błonnik:") + 9;
        int indexCalories = selectedItem.indexOf("Kalorie:") + 9;

        int dotCarbIndex = selectedItem.indexOf(".", indexCarbs);
        int dotProteinIndex = selectedItem.indexOf(".", indexProtein);
        int dotFatIndex = selectedItem.indexOf(".", indexFat);
        int dotFibreIndex = selectedItem.indexOf(".", indexFibre);
        int dotCaloriesIndex = selectedItem.indexOf(".");

        String stringOfCarbs = selectedItem.substring(indexCarbs, dotCarbIndex);
        String stringOfProtein = selectedItem.substring(indexProtein, dotProteinIndex);
        String stringOfFat = selectedItem.substring(indexFat, dotFatIndex);
        String stringOfFibre = selectedItem.substring(indexFibre, dotFibreIndex);

        String stringOfCalories = selectedItem.substring(indexCalories, dotCaloriesIndex);

        double calories = Double.parseDouble(stringOfCalories) * converterValue;
        double protein = Double.parseDouble(stringOfProtein) * converterValue;
        double fat = Double.parseDouble(stringOfFat) * converterValue;
        double carb = Double.parseDouble(stringOfCarbs) * converterValue;
        double fibre = Double.parseDouble(stringOfFibre) * converterValue;


        stringOfName = selectedItem.substring(0, selectedItem.indexOf(","));
        replaceCalories = (df.format(Math.round(calories))).replace(",", ".");
        replaceProtein = (df.format(protein)).replace(",", ".");
        replaceFat = (df.format(fat)).replace(",", ".");
        replaceCarb = (df.format(carb)).replace(",", ".");
        replaceFibre = (df.format(fibre)).replace(",", ".");
    }
}



