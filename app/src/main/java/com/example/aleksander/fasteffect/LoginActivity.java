package com.example.aleksander.fasteffect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.AdditionalClasses.Interfaces.TextWatcherFilter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.app.ProgressDialog.show;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Klasa będąca oknem logowania do aplikacji
 */
public class LoginActivity extends  AppCompatActivity{

    private AutoCompleteTextView autoCompleteTextViewEmail;
    private AutoCompleteTextView autoCompleteTextViewPassword;
    private FirebaseAuth firebaseAuth;

    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    private static final String REMEMBER_ME = "No";
    private static final String LOGIN = "RememberMe";
    private boolean showPassword=false;

    SharedPreferences sharedPreferencesRemember;
    SharedPreferences.Editor editorRemember;
    CheckBox checkBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        sharedPreferencesRemember = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editorRemember = sharedPreferencesRemember.edit();
        editorRemember.apply();

        boolean checkRemember = sharedPreferencesRemember.getBoolean(LOGIN, false);
        rememberOrNot(checkRemember);

    }

    /**
     * Dokonuje ustawienia wszystkich komponentow
     */
    private void rememberOrNot(boolean checkRemember) {
        if (checkRemember) {
            Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
            intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentLogin);

        } else {
            firebaseAuth = FirebaseAuth.getInstance();
            autoCompleteTextViewEmail = findViewById(R.id.autoCompleteTextViewEmail);
            autoCompleteTextViewPassword = findViewById(R.id.autoCompleteTextViewPassword);
            ImageButton imageButtonShowPassword = findViewById(R.id.imageButtonPassword);
            TextView textViewRegister = findViewById(R.id.textViewRegister);

            sharedPreferencesRemember = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editorRemember = sharedPreferencesRemember.edit();
            editorRemember.apply();
            checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

            boolean checked = sharedPreferencesRemember.getBoolean(KEY_REMEMBER, false);
            checkBoxRememberMe.setChecked(checked);

            autoCompleteTextViewEmail.setText(sharedPreferencesRemember.getString(KEY_USERNAME, ""));
            autoCompleteTextViewPassword.setText(sharedPreferencesRemember.getString(KEY_PASS, ""));

            autoCompleteTextViewEmail.addTextChangedListener((TextWatcherFilter) (charSequence, i, i1, i2) -> managePrefs());
            autoCompleteTextViewPassword.addTextChangedListener((TextWatcherFilter) (charSequence, i, i1, i2) -> managePrefs());

            checkBoxRememberMe.setOnCheckedChangeListener((compoundButton, b) -> managePrefs());
            textViewRegister.setOnClickListener(setRegister -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

            autoCompleteTextViewPassword.setTransformationMethod(new PasswordTransformationMethod());
            imageButtonShowPassword.setOnClickListener(password -> showOrHide());
        }
    }

    /**
     * Metoda która umozliwia pokazanie lub ukrycie hasla
     */
    private void showOrHide() {
        if(showPassword) {
            autoCompleteTextViewPassword.setTransformationMethod(new PasswordTransformationMethod());
            autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());
            showPassword=false;
        }
        else {
            autoCompleteTextViewPassword.setTransformationMethod(null);
            autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());
            showPassword=true;
        }
    }

    /**
     * Ustawienia dotyczace tego czy dane logowania użytkownika maja zostać zapisane
     */
    private void managePrefs() {
        if (checkBoxRememberMe.isChecked()) {
            editorRemember.putString(KEY_USERNAME, autoCompleteTextViewEmail.getText().toString().trim());
            editorRemember.putString(KEY_PASS, autoCompleteTextViewPassword.getText().toString().trim());
            editorRemember.putBoolean(KEY_REMEMBER, true);
            editorRemember.putString(REMEMBER_ME, "Yes");
        } else {
            editorRemember.putBoolean(KEY_REMEMBER, false);
            editorRemember.remove(KEY_PASS);
            editorRemember.remove(KEY_USERNAME);
            editorRemember.putString(REMEMBER_ME, "No");
        }
        editorRemember.apply();
    }
    /**
     * Uwierzytelnia użytkownika,
     * Dokonuje przejscia na główna strone aplikacji po wybraniu logowania
     */
    public void buttonLoginClick(View view) {

        view.getId();
        String sEmail = autoCompleteTextViewEmail.getText().toString();
        String sPassword = autoCompleteTextViewPassword.getText().toString();

        if (!sEmail.matches("") && !sPassword.matches("")) {

            (firebaseAuth.signInWithEmailAndPassword(autoCompleteTextViewEmail.getText().toString(),
                    autoCompleteTextViewPassword.getText().toString()))
                    .addOnCompleteListener(task -> {
                        show(LoginActivity.this, "Proszę czekać...", "Uwierzytelnianie", true).dismiss();

                        if (task.isSuccessful()) {
                            if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {
                                Toast.makeText(this, "Zalogowano", Toast.LENGTH_SHORT).show();
                                Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
                                intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
                                intentLogin.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                HideSoftKeyboard.hideSoftKeyboard(this);
                                startActivity(intentLogin);
                            } else {
                                Toast.makeText(this, "Zweryfikuj swój adres email!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("Error", Objects.requireNonNull(task.getException()).toString());
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(this, "Niekompletne dane!", Toast.LENGTH_SHORT).show();
        }
    }
}



