package com.example.aleksander.fasteffect;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.aleksander.fasteffect.AuxiliaryClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.FragmentClass.ExportFragment;
import com.example.aleksander.fasteffect.FragmentClass.HouseFragment;
import com.example.aleksander.fasteffect.FragmentClass.ProfileFragment;
import com.example.aleksander.fasteffect.FragmentClass.SportFragment;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String PREF_NAME = "prefs";
    String Login = "RememberMe";
    private DrawerLayout drawer;
    SharedPreferences sharedPreferencesLog;
    SharedPreferences.Editor editorRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sharedPreferencesLog= getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editorRemember = sharedPreferencesLog.edit();
        SharedPreferences.Editor editorRemember = sharedPreferencesLog.edit();
        editorRemember.putBoolean(Login, true);
        editorRemember.commit();


        SQLiteDatabase baza = openOrCreateDatabase(BazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);

        baza.execSQL("DROP TABLE IF EXISTS PoraDnia");
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'PoraDnia'" +
                "( idPoraDnia INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Pora TEXT)");


        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Posilek'" +
                "( idPosilek INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nazwa TEXT,Bialko REAL,Weglowodany REAL,Tluszcze REAL, Błonnik REAL, Kalorie REAL, Ilość INTEGER NOT NULL)");


        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Hash'" +
                "(idHash INTEGER PRIMARY KEY AUTOINCREMENT, Data NUMERIC NOT NULL," +
                " idPosilek INTEGER NOT NULL,idPoraDnia INTEGER NOT NULL," +
                "CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek)," +
                "CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))");


        ContentValues contentValuesŚniadanie = new ContentValues();
        ContentValues contentValuesLunch = new ContentValues();
        ContentValues contentValuesObiad = new ContentValues();
        ContentValues contentValuesPrzekąska = new ContentValues();
        ContentValues contentValuesKolacja = new ContentValues();

        ContentValues[] contentValues = new ContentValues[]{contentValuesŚniadanie,contentValuesLunch,contentValuesObiad,contentValuesPrzekąska,contentValuesKolacja};

        String[] timeOfDay = new String[]{"Śniadanie","Lunch","Obiad","Przekąska","Kolacja"};


        for (int i = 0; i <timeOfDay.length ; i++) {
            contentValues[i].put(BazaDanychStruktura.BazaTabelaPora,timeOfDay[i]);
            baza.insert(BazaDanychStruktura.TabelaPoraDnia,null, contentValues[i]);
        }

        baza.close();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HouseFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_house);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_house:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HouseFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_activity:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportFragment()).commit();
                break;
            case R.id.nav_importExport:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExportFragment()).commit();
                break;
            case R.id.nav_logOut:
                logout();

                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        editorRemember = sharedPreferencesLog.edit();
        SharedPreferences.Editor editorRemember = sharedPreferencesLog.edit();
        editorRemember.putBoolean(Login, false);
        editorRemember.commit();
        finish();
    }
}

