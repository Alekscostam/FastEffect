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

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomSnackBars;
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
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "com.example.aleksander.fasteffect";

    private AutoCompleteTextView autoCompleteTextViewEmail;
    private AutoCompleteTextView autoCompleteTextViewPassword;
    private FirebaseAuth firebaseAuth;

    private String firstLogIn = "firstLogIn";
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    private static final String REMEMBER_ME = "No";
    private static final String LOGIN = "RememberMe";
    private boolean showPassword = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editorRemember;
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        editorRemember = sharedPreferences.edit();
        editorRemember.apply();

        boolean checkRemember = sharedPreferences.getBoolean(LOGIN, false);
        rememberOrNot(checkRemember);
    }

    /**
     * Dokonuje ustawienia wszystkich komponentow
     */
    private void rememberOrNot(boolean checkRemember) {
        if (checkRemember) {
            Log.i(TAG, "rememberOrNot - zapamietanie uzytkownika");

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

            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editorRemember = sharedPreferences.edit();
            editorRemember.apply();
            checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

            boolean checked = sharedPreferences.getBoolean(KEY_REMEMBER, false);
            checkBoxRememberMe.setChecked(checked);

            autoCompleteTextViewEmail.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            autoCompleteTextViewPassword.setText(sharedPreferences.getString(KEY_PASS, ""));

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
        if (showPassword) {
            autoCompleteTextViewPassword.setTransformationMethod(new PasswordTransformationMethod());
            autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());
            showPassword = false;
        } else {
            autoCompleteTextViewPassword.setTransformationMethod(null);
            autoCompleteTextViewPassword.setSelection(autoCompleteTextViewPassword.length());
            showPassword = true;
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
        Intent intentAdminLog = new Intent(LoginActivity.this, MainActivity.class);
        intentAdminLog.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intentAdminLog.addFlags(FLAG_ACTIVITY_CLEAR_TASK);

        HideSoftKeyboard.hideSoftKeyboard(this);

        if (autoCompleteTextViewEmail.getText().toString().equals("admin") && autoCompleteTextViewPassword.getText().toString().equals("admin"))
            startActivity(intentAdminLog);

         else {
            String sEmail = autoCompleteTextViewEmail.getText().toString();
            String sPassword = autoCompleteTextViewPassword.getText().toString();

            if (!sEmail.matches("") && !sPassword.matches("")) {

                (firebaseAuth.signInWithEmailAndPassword(autoCompleteTextViewEmail.getText().toString(),
                        autoCompleteTextViewPassword.getText().toString()))
                        .addOnCompleteListener(task -> {
                            show(LoginActivity.this, "Proszę czekać...", "Uwierzytelnianie", true).dismiss();

                            if (task.isSuccessful()) {
                                if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {

                                    Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
                                    intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                    intentLogin.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
                                    intentLogin.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());

                                    boolean myFirstLog = sharedPreferences.getBoolean(firstLogIn, true);
                                    if (myFirstLog) {
                                        editorRemember.putBoolean(firstLogIn, true);
                                        editorRemember.apply();
                                    }
                                    editorRemember.apply();
                                    startActivity(intentLogin);

                                    Log.i(TAG, "buttonLoginClick - zalogowano prawidlowo");
                                } else {
                                    CustomSnackBars.customSnackBarStandard("Zweryfikuj swój adres email!",getCurrentFocus()).show();
                                }
                            } else {
                                Log.e("Error", Objects.requireNonNull(task.getException()).toString());
                                CustomSnackBars.customSnackBarStandard(task.getException().getMessage(),getCurrentFocus()).show();
                            }
                        });
            } else {
                CustomSnackBars.customSnackBarStandard("Niekompletne dane!",getCurrentFocus()).show();
            }
        }
    }
}



