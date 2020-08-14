package com.example.aleksander.fasteffect.ProductClasses;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.app.AlertDialog.BUTTON_POSITIVE;
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


/**
 * Klasa służąca do odbierania danych ze zdalnej bazy danych do lokalnej bazy danych
 */
public class AddProductActivity extends AppCompatActivity {

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

            final EditText input = new EditText(this);
            final String selectedItem = (String) adapterView.getItemAtPosition(i);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);
            builder.setTitle("Ile gram produktu chcesz wybrać?");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", (dialogInterface, positive) -> {

                String amount = input.getText().toString();
                arithmeticOperationsForVariables(selectedItem, amount);
                addProductToDatabase(amount);

            });
            builder.setNegativeButton("Anuluj", (dialogInterface, negative) -> dialogInterface.cancel());

            AlertDialog alertDialog = builder.create();
            input.setTextColor(Color.GRAY);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alertDialog.setView(input);
            alertDialog.show();
            alertDialog.getButton(BUTTON_POSITIVE).setTextColor(Color.GREEN);

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

        listViewProducts.setAdapter(arrayAdapter);

        editTextFilter.addTextChangedListener((TextWatcherFilter) (charSequence, start, count, after) -> (AddProductActivity.this)
                .arrayAdapter
                .getFilter()
                .filter(charSequence.toString()));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(this::executorServiceMethod);
        executorService.shutdown();

        textViewBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
        });

        imageButtonAdd.setOnClickListener(view -> {
            Intent addIntent = new Intent(getApplicationContext(), AddNewProductActivity.class);
            startActivity(addIntent);
        });

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
                    .append(" ")
                    .append(dataSnapshot.getKey())
                    .append(" ] ")
                    .append(dataSnapshot.getValue(Product.class));

            listModify.add(stringBuilderValue);

            linkedList.add(String.valueOf(listModify));
            arrayAdapter.notifyDataSetChanged();
            ResizeListView resizeListView = new ResizeListView();
            resizeListView.resize(listViewProducts);
        });
    }

    /**
     * Zapisuje produkt do lokalnej bazy danych
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

        try {
            Cursor k = sqLiteDatabase.query(TABLE_MEAL, columns, where, args, null, null, null);
            k.moveToFirst();
            rowHash.put(HASH_ID_MEAL, Integer.valueOf(k.getString(0)));
            sqLiteDatabase.insert(TABLE_HASH, null, rowHash);
            k.close();
        } catch (Exception ex) {
            sqLiteDatabase.insert(TABLE_MEAL, null, rowProduct);
            Cursor k = sqLiteDatabase.query(TABLE_MEAL, columns, where, args, null, null, null);
            k.moveToFirst();
            rowHash.put(HASH_ID_MEAL, Integer.valueOf(k.getString(0)));
            sqLiteDatabase.insert(TABLE_HASH, null, rowHash);
            k.close();
        } finally {
            sqLiteDatabase.close();
        }
    }

    /**
     * Operacje arytmetyczne na wartosciach wybranego produktu, ktore musza zostac wykonane przed ich zapisaniem do lokalnej bazy danych
     * @param selectedItem okresla wybrany produkt
     * @param amount ilosc wybranego produktu
     */
    private void arithmeticOperationsForVariables(String selectedItem, String amount) {
        double converterValue = (Double.parseDouble(amount) / 100);
        DecimalFormat df = new DecimalFormat("#.#");

        int indexCarbs = selectedItem.indexOf("W:") + 2;
        int indexProtein = selectedItem.indexOf("B:") + 2;
        int indexFat = selectedItem.indexOf("T:") + 2;
        int indexFibre = selectedItem.indexOf("Błonnik:") + 8;
        int indexCalories = selectedItem.indexOf("Kalorie:") + 8;

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

        stringOfName = selectedItem.substring(0, indexCalories);
        replaceCalories = (df.format(Math.round(calories))).replace(",", ".");
        replaceProtein = (df.format(protein)).replace(",", ".");
        replaceFat = (df.format(fat)).replace(",", ".");
        replaceCarb = (df.format(carb)).replace(",", ".");
        replaceFibre = (df.format(fibre)).replace(",", ".");
    }
}



