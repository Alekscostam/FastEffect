package com.example.aleksander.fasteffect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.aleksander.fasteffect.FragmentClass.BazaDanychStruktura;
import com.example.aleksander.fasteffect.FragmentClass.HouseFragment;
import com.example.aleksander.fasteffect.FragmentClass.ProfileFragment;
import com.example.aleksander.fasteffect.FragmentClass.SportFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ctrl +alt + l
        BazaDanychStruktura bazaDanychStruktura = new BazaDanychStruktura();

        SQLiteDatabase baza = openOrCreateDatabase(bazaDanychStruktura.BazaPlik, Context.MODE_PRIVATE, null);

        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Dzien'( idDzien INTEGER PRIMARY KEY AUTOINCREMENT,Data NUMERIC)");
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Posilek'( idPosilek INTEGER PRIMARY KEY AUTOINCREMENT,Nazwa TEXT,Bialko INTEGER,Weglowodany INTEGER,Tluszcze INTEGER)");
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'PoraDnia'( idPoraDnia INTEGER PRIMARY KEY AUTOINCREMENT,Pora TEXT)");
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'Hash'( idDzien INTEGER, idPosilek INTEGER,idPoraDnia INTEGER, CONSTRAINT fk_idDzien FOREIGN KEY(idDzien) REFERENCES Dzien(idDzien),CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek),CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))");


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


/*
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeeeee")));
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
*/
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        //First class open
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
//update
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


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
