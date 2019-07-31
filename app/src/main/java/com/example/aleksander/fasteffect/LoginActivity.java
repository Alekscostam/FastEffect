package com.example.aleksander.fasteffect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.FragmentClass.HouseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private AutoCompleteTextView autoCompleteTextViewEmail;
    private AutoCompleteTextView autoCompleteTextViewPassword;
    private FirebaseAuth firebaseAuth;

    //Zapamietaj
    private static final String  PREF_NAME="prefs";
    private static final String  KEY_REMEMBER="remember";
    private static final String  KEY_USERNAME="username";
    private static final String  KEY_PASS="password";
    SharedPreferences sharedPreferencesRemember;
    SharedPreferences.Editor editorRemember;
    CheckBox checkBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        final int[] password_show = {0};

        autoCompleteTextViewEmail = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewEmail);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        autoCompleteTextViewPassword = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewPassword);
        ImageButton imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonPassword);
        TextView textViewRegister = (TextView) findViewById(R.id.textViewRegister);


        sharedPreferencesRemember= getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        editorRemember = sharedPreferencesRemember.edit();
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);


        if(sharedPreferencesRemember.getBoolean(KEY_REMEMBER,false))
        {
            checkBoxRememberMe.setChecked(true);
        }
        else
            checkBoxRememberMe.setChecked(false);


        autoCompleteTextViewEmail.setText(sharedPreferencesRemember.getString(KEY_USERNAME,""));
        autoCompleteTextViewPassword.setText(sharedPreferencesRemember.getString(KEY_PASS,""));

        autoCompleteTextViewEmail.addTextChangedListener( this);
        autoCompleteTextViewPassword.addTextChangedListener( this);


        checkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                managePrefs();
            }
        });




        firebaseAuth = FirebaseAuth.getInstance();

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


    public void buttonLogin_Click(View v) {
        String sEmail = autoCompleteTextViewEmail.getText().toString();
        String sPassword = autoCompleteTextViewPassword.getText().toString();

        if (!sEmail.matches("") && !sPassword.matches("")) {


            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Proszę czekać...", "Uwierzytelnianie", true);
            (firebaseAuth.signInWithEmailAndPassword(autoCompleteTextViewEmail.getText().toString(), autoCompleteTextViewPassword.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Zalogowano", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                                i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);
                            } else {
                                Log.e("Error", task.getException().toString());
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
        else {Toast.makeText(this, "Niekompletne dane!", Toast.LENGTH_SHORT).show();}
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }








    private void managePrefs()
    {
        if(checkBoxRememberMe.isChecked())
        {
            editorRemember.putString(KEY_USERNAME,autoCompleteTextViewEmail.getText().toString().trim());
            editorRemember.putString(KEY_PASS,autoCompleteTextViewPassword.getText().toString().trim());
            editorRemember.putBoolean(KEY_REMEMBER,true);
            editorRemember.apply();

        }
        else
        {
            editorRemember.putBoolean(KEY_REMEMBER,false);
            editorRemember.remove(KEY_PASS);
            editorRemember.remove(KEY_USERNAME);
            editorRemember.apply();
        }
    }

}



