package com.example.aleksander.fasteffect.FragmentClasses;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomSnackBars;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
import static java.util.Objects.requireNonNull;


/**
 * {@link Fragment}
 * Klasa wykorzystywana do ustawien dotyczacych zapotrzebowania kalorycznego dla użytkownika
 * Zakladka "Aktywność"
 */
public class SportFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public static final String SHARED_PREFS = "shaaredPrefs";

    private Button buttonSave;

    private Map<Integer, Integer> longIntegerMap;

    private LinearLayout linearLayoutAutomatically, linearLayoutManually;
    private TextInputEditText textInputEditTextCalories, textInputEditTextProtein, textInputEditTextCarb, textInputEditTextFat;
    private Spinner spinnerActivity, spinnerKindOfSport, spinnerGoal;


    private SharedPreferences sharedPreferences;
    protected String optionSelected;
    private  RadioGroup radioGroupChooser, radioGroupSport, radioGroupGoal, radioGroupActivity;

    private static String chooserSport;
    private static String chooserActivity;
    private static String chooserGoal;

    static {
        chooserSport = "chooserSport";
        chooserActivity = "chooserActivity";
        chooserGoal = "chooserGoal";
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

        String firstLogIn = "firstLogIn";
        boolean myFirstLog = sharedPreferences.getBoolean(firstLogIn, false);
        if (myFirstLog) {
            Toast.makeText(getContext(), "Pierwsze logowanie! Wybierz sposób wyliczenia kalorri i makroskładników", Toast.LENGTH_LONG).show();
        }

        getterChooser(view);
        radioInitGet(view);
        getMacro(sharedPreferences);

        optionSelected = String.valueOf(sharedPreferences.getString("optionSelected", "0"));

        boolean equals = optionSelected.equals("0");
        if (equals) setAutomatically();
        else setManually();

        adaptersInit();

        buttonSave.setEnabled(true);
        buttonSave.setOnClickListener(this);

        return view;
    }


    private void adaptersInit() {

        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(requireNonNull(getActivity()), R.array.spinnerActivities, R.layout.spinner_item_my);
        setSpinners(spinnerActivity, adapterActivity, "spinnerAktywnosc");

        ArrayAdapter<CharSequence> adapterKindOfSport = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerTypeOfActivities, R.layout.spinner_item_my);
        setSpinners(spinnerKindOfSport, adapterKindOfSport, "spinnerRodzajSportu");

        ArrayAdapter<CharSequence> adapterGoal = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerGoals, R.layout.spinner_item_my);
        setSpinners(spinnerGoal, adapterGoal, "spinnerCel");
    }

    /**
     * Inicjuje komponenty tej klasy
     */
    private void initViews(View view) {

        longIntegerMap = new TreeMap<>();

        textInputEditTextCalories = view.findViewById(R.id.textInputEditTextKalorie);
        textInputEditTextProtein = view.findViewById(R.id.textInputEditTextProtein);
        textInputEditTextCarb = view.findViewById(R.id.textInputEditTextCarb);
        textInputEditTextFat = view.findViewById(R.id.textInputEditTextFat);
        linearLayoutAutomatically = view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutManually = view.findViewById(R.id.linearLayoutRęczne);

        spinnerActivity = view.findViewById(R.id.spinnerAktywnosc);
        spinnerKindOfSport = view.findViewById(R.id.spinnerRodzajSportu);
        spinnerGoal = view.findViewById(R.id.spinnerCel);

        buttonSave = view.findViewById(R.id.buttonSave);
    }

    /**
     * Ustawia wartosci dla wszystkich spinnerow
     *
     * @param spinner            przekazywany do ustawienia na nim wartosci
     * @param adapter            sluzy do ustawienie na niego specjalnego layoutu
     * @param stringValueSpinner wartosc do jakiej odwoluje sie schared preferences
     */
    private void setSpinners(Spinner spinner, ArrayAdapter<CharSequence> adapter, String stringValueSpinner) {
        adapter.setDropDownViewResource(R.layout.spinner_item_my);
        spinner.setAdapter(adapter);
        String spinnerValue = sharedPreferences.getString(stringValueSpinner, "0");
        assert spinnerValue != null;
        spinner.setSelection(Integer.parseInt(spinnerValue));
        spinner.setOnItemSelectedListener(this);
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

            List<Integer> keyList = new ArrayList<>(longIntegerMap.keySet());

            editor.putString("spinnerAktywnosc", requireNonNull(this.longIntegerMap.get(keyList.get(0))).toString()); //InputString: from the EditText
            spinnerActivity.setSelection(requireNonNull(longIntegerMap.get(keyList.get(0))));
            editor.putString("spinnerCel", requireNonNull(this.longIntegerMap.get(keyList.get(1))).toString()); //InputString: from the EditText
            spinnerGoal.setSelection(requireNonNull(longIntegerMap.get(keyList.get(1))));
            editor.putString("spinnerRodzajSportu", requireNonNull(this.longIntegerMap.get(keyList.get(2))).toString()); //InputString: from the EditText
            spinnerKindOfSport.setSelection(requireNonNull(longIntegerMap.get(keyList.get(2))));

            sharedPreferences.edit().putInt(SportFragment.chooserSport, checkedRadioButtonSportId).apply();
            sharedPreferences.edit().putInt(SportFragment.chooserActivity, checkedRadioButtonActivityId).apply();
            sharedPreferences.edit().putInt(SportFragment.chooserGoal, checkedRadioButtonGoalId).apply();

            CustomSnackBars.customSnackBarStandard("Zapisano!", getView()).show();
            editor.apply();
            complete = true;
        }
        if (!optionSelected.equals("0")) {
            optionSet("1");
            int protein = Integer.parseInt(requireNonNull(textInputEditTextProtein.getText()).toString());
            int carb = Integer.parseInt(requireNonNull(textInputEditTextCarb.getText()).toString());
            int fat = Integer.parseInt(requireNonNull(textInputEditTextFat.getText()).toString());

            if ((protein + carb + fat) == 100) {
                editor.putString("textProtein", String.valueOf(protein));
                editor.putString("textCarb", String.valueOf(carb));
                editor.putString("textFat", String.valueOf(fat));
            } else
                CustomSnackBars.customSnackBarStandard("Wartość procentowa nie jest równa 100%", getView()).show();

            int calories = Integer.parseInt(requireNonNull(textInputEditTextCalories.getText()).toString());
            if ((calories) < 100)
                CustomSnackBars.customSnackBarStandard("Wartość jest za mała", getView()).show();
            else {
                editor.putString("textCalories", String.valueOf(calories));
                CustomSnackBars.customSnackBarStandard("Zapisano!", getView()).show();
                complete = true;
            }
        }

        if (complete) {
            editor = sharedPreferences.edit().putInt("chooserOption", checkedRadioButtonId);
            editor.apply();
            editor.commit();
        }

        buttonSave.setEnabled(true);
    }

    /**
     * Inicjacja radio buttonow
     */
    private void radioInitGet(View view) {

        final int idRadioEndurance = R.id.radioEndurance;
        final int idRadioStrength = R.id.radioStrength;
        final int idRadioMixed = R.id.radioMixed;
        final int idRadioNoKindOfSport = R.id.radioNoKindOfSport;

        final int idRadioKeepWeight = R.id.radioKeepWeight;
        final int idRadioIncreaseWeight = R.id.radioIncreaseWeight;
        final int idRadioReductionWeight = R.id.radioReductionWeight;

        final int idRadioNoActivity = R.id.radioNoActivity;
        final int idRadioLowActivity = R.id.radioLowActivity;
        final int idRadioMediumActivity = R.id.radioMediumActivity;
        final int idRadioHighActivity = R.id.radioHighActivity;
        final int idRadioVeryHighActivity = R.id.radioVeryHighActivity;

        RadioButton radioEndurance = view.findViewById(R.id.radioEndurance);
        RadioButton radioStrength = view.findViewById(R.id.radioStrength);
        RadioButton radioNoKindOfSport = view.findViewById(R.id.radioNoKindOfSport);
        RadioButton radioMixed = view.findViewById(R.id.radioMixed);


        RadioButton radioKeepWeight = view.findViewById(R.id.radioKeepWeight);
        RadioButton radioIncreaseWeight = view.findViewById(R.id.radioIncreaseWeight);
        RadioButton radioReductionWeight = view.findViewById(R.id.radioReductionWeight);


        RadioButton radioNoActivity = view.findViewById(R.id.radioNoActivity);
        RadioButton radioLowActivity = view.findViewById(R.id.radioLowActivity);
        RadioButton radioMediumActivity = view.findViewById(R.id.radioMediumActivity);
        RadioButton radioHighActivity = view.findViewById(R.id.radioHighActivity);
        RadioButton radioVeryHighActivity = view.findViewById(R.id.radioVeryHighActivity);

        int chooserSport = sharedPreferences.getInt(SportFragment.chooserSport, idRadioNoKindOfSport);
        int chooserActivity = sharedPreferences.getInt(SportFragment.chooserActivity, idRadioNoKindOfSport);
        int chooserGoal = sharedPreferences.getInt(SportFragment.chooserGoal, idRadioNoKindOfSport);

        switch (chooserSport) {
            case idRadioEndurance:
                radioEndurance.setChecked(true);
                break;
            case idRadioStrength:
                radioStrength.setChecked(true);
                break;
            case idRadioMixed:
                radioMixed.setChecked(true);
                break;
            case idRadioNoKindOfSport:
                radioNoKindOfSport.setChecked(true);
                break;
            default:
                break;
        }

        switch (chooserActivity) {
            case idRadioNoActivity:
                radioNoActivity.setChecked(true);
                break;
            case idRadioLowActivity:
                radioLowActivity.setChecked(true);
                break;
            case idRadioMediumActivity:
                radioMediumActivity.setChecked(true);
                break;
            case idRadioHighActivity:
                radioHighActivity.setChecked(true);
                break;
            case idRadioVeryHighActivity:
                radioVeryHighActivity.setChecked(true);
                break;
            default:
                break;
        }

        switch (chooserGoal) {
            case idRadioKeepWeight:
                radioKeepWeight.setChecked(true);
                break;
            case idRadioIncreaseWeight:
                radioIncreaseWeight.setChecked(true);
                break;
            case idRadioReductionWeight:
                radioReductionWeight.setChecked(true);
                break;
            default:
                break;
        }
    }


    private void getterChooser(View view) {
        int radioAutomatic = R.id.radioAutomatic;

        RadioButton radioButtonAutomatic = view.findViewById(R.id.radioAutomatic);
        RadioButton radioButtonAManually = view.findViewById(R.id.radioManually);

        int chooserOption = sharedPreferences.getInt("chooserOption", radioAutomatic);

        if (chooserOption == radioAutomatic) {
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
        textInputEditTextCalories.setText(sharedPreferences.getString("textCalories", "0"));
        textInputEditTextProtein.setText(sharedPreferences.getString("textProtein", "0"));
        textInputEditTextCarb.setText(sharedPreferences.getString("textCarb", "0")); //no id: default value
        textInputEditTextFat.setText(sharedPreferences.getString("textFat", "0"));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        longIntegerMap.put(parent.getId(), position);
        buttonSave.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // empty method from AdapterView.OnItemSelectedListener
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
