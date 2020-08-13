package com.example.aleksander.fasteffect.ProductClasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.Product;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.util.Objects.requireNonNull;


/**
 * Klasa przeznaczona do dodawania nowego produktu do zdalnej bazy danych
 */
public class AddNewProductActivity extends Activity {

    private String productsName;
    private double productsProtein;
    private double productsFibre;
    private double productsCalories;
    private double productsFat;
    private double productsCarbohydrates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproduct_main);

        TextInputEditText textInputEditTextProteins = findViewById(R.id.textInputEditTextProtein);
        TextInputEditText textInputEditTextFibre = findViewById(R.id.textInputEditTextFiber);
        TextInputEditText textInputEditTextCarbohydrates = findViewById(R.id.textInputEditTextCarb);
        TextInputEditText textInputEditTextFats = findViewById(R.id.textInputEditTextFat);
        TextInputEditText textInputEditTextCalories = findViewById(R.id.textInputEditTextKcal);
        TextInputEditText textInputEditTextName = findViewById(R.id.textInputEditTextName);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Produkty");

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view -> {
            try {

                productsProtein = Double.parseDouble(String.valueOf(textInputEditTextProteins.getText()));
                productsFibre = Double.parseDouble(requireNonNull(textInputEditTextFibre.getText()).toString());
                productsCalories = Double.parseDouble(requireNonNull(textInputEditTextCalories.getText()).toString());
                productsFat = Double.parseDouble(requireNonNull(textInputEditTextFats.getText()).toString());
                productsCarbohydrates = Double.parseDouble(requireNonNull(textInputEditTextCarbohydrates.getText()).toString());
                productsName = requireNonNull(textInputEditTextName.getText()).toString();

                databaseReference.child(productsName).setValue(new Product(productsCalories, productsProtein,
                        productsFat, productsCarbohydrates, productsFibre));

                Toast.makeText(AddNewProductActivity.this,
                        "Dodano nowy produkt!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(AddNewProductActivity.this,
                        "Nie podano wszystkich wartości!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView textViewBack = findViewById(R.id.textViewBack);
        textViewBack.setOnClickListener(v -> {
            Intent cofnij = new Intent(getApplicationContext(), AddProductActivity.class);
            startActivity(cofnij);
        });
    }
}