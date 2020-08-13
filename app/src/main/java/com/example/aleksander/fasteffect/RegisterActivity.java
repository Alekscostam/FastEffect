package com.example.aleksander.fasteffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static android.app.ProgressDialog.show;


/**
 * Klasa będąca oknem rejestracji użytkownika
 */
public class RegisterActivity extends AppCompatActivity {

   private String sGender;
   private String sPassword;
   private String sPasswordAgain;
   private String sEmail;
   private String sAge;
   private String sWeight;
   private String sHeight;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        firebaseAuth = FirebaseAuth.getInstance();
        TextView textViewBack = findViewById(R.id.textViewBack);

        RadioButton radioButtonM = findViewById(R.id.radioButtonM);
        RadioButton radioButtonW = findViewById(R.id.radioButtonW);

        radioButtonM.setOnClickListener(m -> sGender = "M");
        radioButtonW.setOnClickListener(w -> sGender = "W");

        textViewBack.setOnClickListener(back -> {
            Intent backIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(backIntent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if (firebaseAuth.getCurrentUser() != null) {
        }*/
        firebaseAuth.getCurrentUser();
    }


    private void editTextInit() {
        TextInputEditText password =(findViewById(R.id.textInputEditTextPassword));
        TextInputEditText passwordAgain =(findViewById(R.id.textInputEditTextPasswordAgain));
        TextInputEditText email =(findViewById(R.id.textInputEditTextEmail));
        TextInputEditText weight =(findViewById(R.id.textInputEditTextWaga));
        TextInputEditText age =(findViewById(R.id.textInputEditTextWiek));
        TextInputEditText height =(findViewById(R.id.textInputEditTextWzrost));

        sPassword = Objects.requireNonNull(password.getText()).toString();
        sPasswordAgain = Objects.requireNonNull(passwordAgain.getText()).toString();
        sEmail = Objects.requireNonNull(email.getText()).toString();
        sWeight = Objects.requireNonNull(weight.getText()).toString();
        sAge = Objects.requireNonNull(age.getText()).toString();
        sHeight = Objects.requireNonNull(height.getText()).toString();
    }

    public void buttonRegisterClick(View view) {
        editTextInit();
        view.getId();

        if (!sEmail.isEmpty() && !sPassword.isEmpty() && !sPasswordAgain.isEmpty()) {
            (firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword))
                    .addOnCompleteListener(task -> {
                        show(RegisterActivity.this, "Proszę czekać...", "Rejestrowanie", true).dismiss();
                        if (task.isSuccessful()) {
                            User user = new User(sWeight, sAge, sHeight, sGender, sEmail);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(taskUser -> {
                                if (taskUser.isSuccessful()) {
                                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(taskLast -> {
                                        if (taskLast.isSuccessful())
                                            Toast.makeText(RegisterActivity.this, "Zarejestrowano! Sprawdź swój email w celu weryfikacji", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(taskLast.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    });

                                } else
                                    Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Log.e("Error", Objects.requireNonNull(task.getException()).toString());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(this, "Niekompletne dane!", Toast.LENGTH_SHORT).show();
    }
}

