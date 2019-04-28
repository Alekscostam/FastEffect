package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
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

        final EditText editTextPassword = (EditText) findViewById(R.id.autoCompleteTextViewPassword) ;
        ImageButton imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonPassword);
        TextView textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(register);
            }
        });


        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        imageButtonShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(password_show[0] ==0)
                {


                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password_show[0] =1;
                    editTextPassword.setSelection(editTextPassword.length());

                }
                else if(password_show[0] ==1)
                {


                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_show[0] =0;
                    editTextPassword.setSelection(editTextPassword.length());
                }



            }
        });








    }

}
