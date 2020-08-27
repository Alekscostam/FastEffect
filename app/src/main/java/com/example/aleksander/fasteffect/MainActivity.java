package com.example.aleksander.fasteffect;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure;
import com.example.aleksander.fasteffect.FragmentClasses.ExportFragment;
import com.example.aleksander.fasteffect.FragmentClasses.HouseFragment;
import com.example.aleksander.fasteffect.FragmentClasses.ProfileFragment;
import com.example.aleksander.fasteffect.FragmentClasses.SportFragment;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_TIME_OF_DAY;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TIME_COLUMN_TIME_OF_DAY;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.createTableHash;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.createTableMeal;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.createTableTimeOfDay;
import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.dropTable;


/**
 * Klasa główna która inicializuje bazę i wszelkie podtsawowe componenty
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG ="com.example.aleksander.fasteffect";

    private static final String PREF_NAME = "prefs";
    private String login = "RememberMe";
    private DrawerLayout drawer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate - inicjacja startowa po zalogowaniu uzytkownika");

        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorRemember = sharedPreferences.edit();

        String firstLogIn = "firstLogIn";
        boolean myFirstLog = sharedPreferences.getBoolean(firstLogIn, false);
        editorRemember.putBoolean(login, true);
        databaseInit();

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

        if(myFirstLog)
        {
            editorRemember.putBoolean(firstLogIn,false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportFragment()).commit();
        }
        editorRemember.apply();
    }

    @Override
    public void onBackPressed() {
        HideSoftKeyboard.hideSoftKeyboard(this);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        HideSoftKeyboard.hideSoftKeyboard(this);

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
            default:
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Dokonuje wylogowywania z aplikacji i przejscie na strone logowania
     */
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        SharedPreferences.Editor editorRemember = sharedPreferences.edit();
        editorRemember.putBoolean(login, false);
        editorRemember.apply();
        finish();
    }

    /**
     * Inicjuje baze danych
     */
    private void databaseInit() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(SQLDatabaseStructure.DATABASE_FILE, Context.MODE_PRIVATE, null);

        sqLiteDatabase.execSQL(dropTable(TABLE_TIME_OF_DAY));
        sqLiteDatabase.execSQL(createTableTimeOfDay());
        sqLiteDatabase.execSQL(createTableMeal());
        sqLiteDatabase.execSQL(createTableHash());

        ContentValues contentValues = new ContentValues();
        String[] timeOfDay = new String[]{"Śniadanie", "Lunch", "Obiad", "Przekąska", "Kolacja"};

        for (String s : timeOfDay) {
            contentValues.put(TIME_COLUMN_TIME_OF_DAY, s);
            sqLiteDatabase.insert(SQLDatabaseStructure.TABLE_TIME_OF_DAY, null, contentValues);
        }

        sqLiteDatabase.close();
    }
}

