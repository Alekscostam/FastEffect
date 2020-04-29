package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aleksander.fasteffect.AuxiliaryClass.Produkty;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewProductActivity extends AppCompatActivity {

    Produkty noweProdukty;
    DatabaseReference ref;

    String referenceName = "Produkty";

    String produkty_nazwa;
    double produkty_białko;
    double produkty_błonnik;
    double produkty_kcal;
    double produkty_tłuszcze;
    double produkty_węglowodany;

    TextView textViewBack;

    TextInputEditText textInputEditTextBiałko;
    TextInputEditText textInputEditTextBłonnik;
    TextInputEditText textInputEditTextWęglowodany;
    TextInputEditText textInputEditTextTłuszcze;
    TextInputEditText textInputEditTextKalorie;
    TextInputEditText textInputEditTextNazwa;

   Button ButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproduct_main);

        textInputEditTextBiałko = findViewById(R.id.textInputEditTextProtein);
        textInputEditTextWęglowodany = findViewById(R.id.textInputEditTextCarb);
        textInputEditTextTłuszcze = findViewById(R.id.textInputEditTextFat);
        textInputEditTextBłonnik = findViewById(R.id.textInputEditTextFiber);
        textInputEditTextNazwa = findViewById(R.id.textInputEditTextName);
        textInputEditTextKalorie = findViewById(R.id.textInputEditTextKcal);

        ref = FirebaseDatabase.getInstance().getReference(referenceName);
        noweProdukty = new Produkty();

        ButtonSave = findViewById(R.id.buttonSave);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                produkty_białko = Double.valueOf(textInputEditTextBiałko.getText().toString()) ;
                produkty_błonnik = Double.valueOf(textInputEditTextBłonnik.getText().toString());
                produkty_kcal = Double.valueOf(textInputEditTextKalorie.getText().toString());
                produkty_tłuszcze = Double.valueOf(textInputEditTextTłuszcze.getText().toString());
                produkty_węglowodany = Double.valueOf(textInputEditTextWęglowodany.getText().toString());
                produkty_nazwa = textInputEditTextNazwa.getText().toString();
               ref.child(produkty_nazwa).setValue(new Produkty(produkty_kcal,produkty_białko,produkty_tłuszcze,produkty_węglowodany,produkty_błonnik));
                Toast.makeText(AddNewProductActivity.this, "Dodano produkt!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                    Toast.makeText(AddNewProductActivity.this, "Nie podano wszystkich wartości!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewBack = findViewById(R.id.textViewBack);
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(cofnij);
            }
        });

    }


}