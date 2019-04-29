package com.example.aleksander.fasteffect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputLayoutLogin;
    private TextInputEditText textInputLayoutPassword;
    private TextInputEditText textInputLayoutPasswordAgain;
    private TextInputEditText textInputLayoutEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        TextView textViewBack = (TextView) findViewById(R.id.textViewBack);
         textInputLayoutLogin = (TextInputEditText) findViewById(R.id.textInputEditTextLogin);
         textInputLayoutPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
         textInputLayoutPasswordAgain = (TextInputEditText) findViewById(R.id.textInputEditTextPasswordAgain);
         textInputLayoutEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);


        firebaseAuth = FirebaseAuth.getInstance();


        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(cofnij);
            }
        });


    }




    public void buttonRegister_Click(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this,"Proszę czekać...", "Rejestrowanie",true);
        (firebaseAuth.createUserWithEmailAndPassword(textInputLayoutEmail.getText().toString(),textInputLayoutPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Zarejestrowano", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Log.e("Error", task.getException().toString());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




}

