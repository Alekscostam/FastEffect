package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        final int[] password_show = {0};


        AutoCompleteTextView autoCompleteTextViewLogin = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewLogin);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final AutoCompleteTextView autoCompleteTextViewPassword = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewPassword);
        ImageButton imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonPassword);
        TextView textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });

        //  final Typeface tfArial = Typeface.createFromAsset(getAssets(), "arial.ttf");
        autoCompleteTextViewPassword.setTransformationMethod(new PasswordTransformationMethod());
        // editTextPassword.setTypeface(tfArial);

        imageButtonShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (password_show[0] == 0) {

                    autoCompleteTextViewPassword.setTransformationMethod(null);
                    password_show[0] = 1;
                    autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());


                } else if (password_show[0] == 1) {

                    autoCompleteTextViewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    password_show[0] = 0;
                    autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());

                }


            }
        });


    }

}
