package com.example.aleksander.fasteffect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AuxiliaryClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextPasswordAgain;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextWaga;
    private TextInputEditText textInputEditTextWiek;
    private TextInputEditText textInputEditTextWzrost;
    private RadioButton radioButtonM;
    private RadioButton radioButtonW;
    final String[] plec = {""};

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        firebaseAuth = FirebaseAuth.getInstance();
        TextView textViewBack = findViewById(R.id.textViewBack);

        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextPasswordAgain = findViewById(R.id.textInputEditTextPasswordAgain);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);

        textInputEditTextWaga = findViewById(R.id.textInputEditTextWaga);
        textInputEditTextWiek = findViewById(R.id.textInputEditTextWiek);
        textInputEditTextWzrost = findViewById(R.id.textInputEditTextWzrost);

        radioButtonM = findViewById(R.id.radioButtonM);
        radioButtonW = findViewById(R.id.radioButtonW);

        radioButtonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plec[0] = "M";
                String test = plec[0];
                Toast.makeText(RegisterActivity.this, test, Toast.LENGTH_SHORT).show();
            }
        });
        radioButtonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plec[0] = "W";
                String test = plec[0];
                Toast.makeText(RegisterActivity.this, test, Toast.LENGTH_SHORT).show();

            }
        });

        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cofnij = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(cofnij);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
        }
    }

    public void buttonRegister_Click(View v) {

        String sPassword = textInputEditTextPassword.getText().toString();
        String sPasswordAgain = textInputEditTextPasswordAgain.getText().toString();
        final String sEmail = textInputEditTextEmail.getText().toString();
        final String sAge = textInputEditTextWiek.getText().toString();
        final String sWeight = textInputEditTextWaga.getText().toString();
        final String sHeight = textInputEditTextWzrost.getText().toString();
        final String sGender = plec[0];

        if (!sEmail.matches("") && !sPassword.matches("") && !sPasswordAgain.matches("")) {

            final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Proszę czekać...",
                    "Rejestrowanie", true);
            (firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                User user = new User(
                                        sWeight,
                                        sAge,
                                        sHeight,
                                        sGender,
                                        sEmail
                                );
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){

                                                        Toast.makeText(RegisterActivity.this, "Zarejestrowano, sprawdź swój email w celu weryfikacji weryfikacji", Toast.LENGTH_SHORT).show();

                                                    }else
                                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {
                                            Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                              //  Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                             //   startActivity(i);

                            } else {
                                Log.e("Error", task.getException().toString());
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Niekompletne dane!", Toast.LENGTH_SHORT).show();
        }


    }


}

