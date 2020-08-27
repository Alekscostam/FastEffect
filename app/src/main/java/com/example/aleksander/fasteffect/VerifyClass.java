package com.example.aleksander.fasteffect;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Klasa zabezpieczająca/weryfikująca użytkownika juz w panelu logowania
 * Sprawdza czy mail wyslany do uzytkownika zostal zweryfikowany
 */
public class VerifyClass extends Application {

    public static final String TAG="com.example.aleksander.fasteffect";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG,"onCreate - dodatkowa weryfikacja uzytkownika");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            Intent intent = new Intent(VerifyClass.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}