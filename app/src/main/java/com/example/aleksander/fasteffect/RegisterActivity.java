package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        TextView textViewBack = (TextView) findViewById(R.id.textViewBack);
        TextInputLayout textInputLayoutLogin= (TextInputLayout) findViewById(R.id.textInputLayoutLogin);
        TextInputLayout textInputLayoutPassword= (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        TextInputLayout textInputLayoutPasswordAgain= (TextInputLayout) findViewById(R.id.textInputLayoutPasswordAgain);
        TextInputLayout textInputLayoutEmail= (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);



        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(cofnij);
            }
        });


    }
}