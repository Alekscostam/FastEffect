package com.example.aleksander.fasteffect.FragmentClasses;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomSnackBars;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.R;

import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
import static java.util.Objects.requireNonNull;


/**
 * {@link Fragment}
 * Klasa wykorzystywana do ustawien dotyczacych zapotrzebowania kalorycznego dla użytkownika
 * Zakladka "Aktywność"
 */
public class SportFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public static final String TAG = "com.example.aleksander.fasteffect.FragmentClasses";

    public static final String SHARED_PREFS = "shaaredPrefs";

    private Button buttonSave;

    private LinearLayout linearLayoutAutomatically, linearLayoutManually;
    private TextInputEditText textInputEditTextCalories, textInputEditTextProtein, textInputEditTextCarb, textInputEditTextFat;


    private SharedPreferences sharedPreferences;
    protected String optionSelected;
    private RadioGroup radioGroupChooser, radioGroupSport, radioGroupGoal, radioGroupActivity;

    private static String chooserSport;
    private static String chooserActivity;
    private static String chooserGoal;
    private static String chooserOption;
    private static String textCalories;
    private static String textProtein;
    private static String textCarb;
    private static String textFat;
    private static String rbSport;
    private static String rbActivity;
    private static String rbGoal;

    static {
        chooserSport = "chooserSport";
        chooserActivity = "chooserActivity";
        chooserGoal = "chooserGoal";
        chooserOption = "chooserOption";
        textCalories = "textCalories";
        textProtein = "textProtein";
        textCarb = "textCarb";
        textFat = "textFat";
        rbSport = "rbSport";
        rbActivity = "rbActivity";
        rbGoal = "rbGoal";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sport, container, false);
        initViews(view);

        radioGroupChooser = view.findViewById(R.id.radioButtonChooser);
        radioGroupChooser.setOnCheckedChangeListener(this);
        radioGroupSport = view.findViewById(R.id.radioGroupSport);
        radioGroupGoal = view.findViewById(R.id.radioGroupGoal);
        radioGroupActivity = view.findViewById(R.id.radioGroupActivity);

        sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        getterChooser(view);
        radioInitGet(view);
        getMacro(sharedPreferences);

        optionSelected = String.valueOf(sharedPreferences.getString("optionSelected", "0"));

        boolean equals = optionSelected.equals("0");
        if (equals) setAutomatically();
        else setManually();

        buttonSave.setEnabled(true);
        buttonSave.setOnClickListener(this);

        return view;
    }

    /**
     * Inicjuje komponenty tej klasy
     */
    private void initViews(View view) {

        textInputEditTextCalories = view.findViewById(R.id.textInputEditTextKalorie);
        textInputEditTextProtein = view.findViewById(R.id.textInputEditTextProtein);
        textInputEditTextCarb = view.findViewById(R.id.textInputEditTextCarb);
        textInputEditTextFat = view.findViewById(R.id.textInputEditTextFat);
        linearLayoutAutomatically = view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutManually = view.findViewById(R.id.linearLayoutRęczne);

        buttonSave = view.findViewById(R.id.buttonSave);
    }

    /**
     * Uzytkownik dokonuje wybory dotyczacego okreslenie tego na jakiej podstawie maja byc przyznawane wartosci makroskladnikow
     */
    private void saveOptions() {
        SharedPreferences sharedPreferencesSharedPrefs = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSharedPrefs.edit();
        HideSoftKeyboard.hideSoftKeyboard(requireNonNull(getActivity()));

        int checkedRadioButtonId = radioGroupChooser.getCheckedRadioButtonId();

        int checkedRadioButtonSportId = radioGroupSport.getCheckedRadioButtonId();
        int checkedRadioButtonActivityId = radioGroupActivity.getCheckedRadioButtonId();
        int checkedRadioButtonGoalId = radioGroupGoal.getCheckedRadioButtonId();

        boolean complete = false;

        if (optionSelected.equals("0")) {
            optionSet("0");

            sharedPreferences.edit().putInt(SportFragment.chooserSport, checkedRadioButtonSportId).apply();
            sharedPreferences.edit().putInt(SportFragment.chooserActivity, checkedRadioButtonActivityId).apply();
            sharedPreferences.edit().putInt(SportFragment.chooserGoal, checkedRadioButtonGoalId).apply();

            CustomSnackBars.customSnackBarStandard("Zapisano!", getView()).show();
            editor.apply();
            radioInitGet(requireNonNull(getView()));

            complete = true;
        }
        if (!optionSelected.equals("0")) {
            optionSet("1");

            int protein = sharedPreferences.getInt(textProtein, 20);
            int carb = sharedPreferences.getInt(textCarb, 50);
            int fat = sharedPreferences.getInt(textFat, 30);
            int calories = sharedPreferences.getInt(textCalories, 2000);

            try {
                protein = Integer.parseInt(requireNonNull(textInputEditTextProtein.getText()).toString());
                carb = Integer.parseInt(requireNonNull(textInputEditTextCarb.getText()).toString());
                fat = Integer.parseInt(requireNonNull(textInputEditTextFat.getText()).toString());
                calories = Integer.parseInt(requireNonNull(textInputEditTextCalories.getText()).toString());
            } catch (NumberFormatException nfe) {
                Log.i(TAG, nfe.getMessage() + " saveOptions - nieprawidlowy format danych!");
                CustomSnackBars.customSnackBarStandard("Nie podano wszystkich wartości!", getView()).show();
            }

            if ((protein + carb + fat) == 100) {
                editor.putInt(textProtein, protein);
                editor.putInt(textCarb, carb);
                editor.putInt(textFat, fat);
            } else
                CustomSnackBars.customSnackBarStandard("Wartość procentowa nie jest równa 100%", getView()).show();
            if ((calories) < 100)
                CustomSnackBars.customSnackBarStandard("Wartość jest za mała", getView()).show();
            else {
                editor.putInt(textCalories, calories);
                editor.commit();
                editor.apply();
                CustomSnackBars.customSnackBarStandard("Zapisano!", getView()).show();
                complete = true;
            }
        }

        if (complete) {
            editor = sharedPreferences.edit().putInt(chooserOption, checkedRadioButtonId);
            editor.apply();
            editor.commit();
        }
        buttonSave.setEnabled(true);
    }

    /**
     * Inicjacja radio buttonow
     */
    private void radioInitGet(View view) {

        final int idRadioNoKindOfSport = R.id.radioNoKindOfSport;
        final int idRadioKeepWeight = R.id.radioKeepWeight;
        final int idRadioLowActivity = R.id.radioLowActivity;

        int chosenSport = sharedPreferences.getInt(SportFragment.chooserSport, idRadioNoKindOfSport);
        int chosenActivity = sharedPreferences.getInt(SportFragment.chooserActivity, idRadioLowActivity);
        int chosenGoal = sharedPreferences.getInt(SportFragment.chooserGoal, idRadioKeepWeight);

        RadioButton rbSport = view.findViewById(chosenSport);
        RadioButton rbActivity = view.findViewById(chosenActivity);
        RadioButton rbGoal = view.findViewById(chosenGoal);

        try {
            rbSport.setChecked(true);
            rbActivity.setChecked(true);
            rbGoal.setChecked(true);
            radiosChecker(rbSport, rbActivity, rbGoal);
        } catch (NullPointerException e) {
            Log.i(TAG, e.getMessage() + " Może być nullpointerException za pierwszym obejsciem");
        }
    }

    private void radiosChecker(RadioButton rbS, RadioButton rbA, RadioButton rbG) {

        sharedPreferences.edit()
                .putString(rbSport, rbS.getText().toString())
                .putString(rbActivity, rbA.getText().toString())
                .putString(rbGoal, rbG.getText().toString())
                .apply();
    }

    /**
     * Opcja wybory przydzialania kaloryki
     */
    private void getterChooser(View view) {
        int radioAutomatic = R.id.radioAutomatic;

        RadioButton radioButtonAutomatic = view.findViewById(R.id.radioAutomatic);
        RadioButton radioButtonAManually = view.findViewById(R.id.radioManually);

        int chooserOptionInt = sharedPreferences.getInt(chooserOption, radioAutomatic);

        if (chooserOptionInt == radioAutomatic) {
            radioButtonAutomatic.setChecked(true);
        } else
            radioButtonAManually.setChecked(true);
    }

    /**
     * Metoda sluzaca do ustawienia tekstu na textInput'ach
     *
     * @param sharedPreferences odczytuje wartosci z pamieci
     */
    private void getMacro(SharedPreferences sharedPreferences) {
        int anInt = sharedPreferences.getInt(textCalories, 2000);
        textInputEditTextCalories.setText(String.valueOf(sharedPreferences.getInt(textCalories, 2000)));
        textInputEditTextProtein.setText(String.valueOf(sharedPreferences.getInt(textProtein, 20)));
        textInputEditTextCarb.setText(String.valueOf(sharedPreferences.getInt(textCarb, 50)));
        textInputEditTextFat.setText(String.valueOf(sharedPreferences.getInt(textFat, 30)));
    }

    /**
     * Wybranie opcji dotyczacej przydzielenia makroskladnikow w diecie i zapisanie jej do pamieci sharedPreferences
     *
     * @param option wynosi 0 lub 1 w zaleznosci od wyboru uzytkownika
     */
    public void optionSet(String option) {
        sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("optionSelected", option);
        editor.apply();
    }

    /**
     * Przelaczenie na okno wyboru automatycznego przydzielenia makroskladnikow
     */
    public void setAutomatically() {
        ViewGroup.LayoutParams paramsManually = linearLayoutManually.getLayoutParams();
        paramsManually.height = 0;
        paramsManually.width = 0;
        linearLayoutManually.setLayoutParams(paramsManually);
        linearLayoutAutomatically.getLayoutParams().height = WRAP_CONTENT;
        linearLayoutAutomatically.getLayoutParams().width = MATCH_PARENT;
    }

    /**
     * Przelaczenie na okno wyboru manualnego przydzielenia makroskladnikow
     */
    public void setManually() {
        ViewGroup.LayoutParams paramsAutomatically = linearLayoutAutomatically.getLayoutParams();
        paramsAutomatically.height = 0;
        paramsAutomatically.width = 0;
        linearLayoutAutomatically.setLayoutParams(paramsAutomatically);
        linearLayoutManually.getLayoutParams().height = WRAP_CONTENT;
        linearLayoutManually.getLayoutParams().width = MATCH_PARENT;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSave) {
            saveOptions();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioManually:
                setManually();
                optionSelected = "1";
                break;
            case R.id.radioAutomatic:
                setAutomatically();
                optionSelected = "0";
                break;
            default:
                break;
        }
    }
}
