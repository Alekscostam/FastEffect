package com.example.aleksander.fasteffect.FragmentClass;


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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.R;

import java.util.LinkedHashMap;
import java.util.Map;

import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
import static java.util.Objects.requireNonNull;


/**
 * {@link Fragment}
 * Klasa wykorzystywana do ustawien dotyczacych zapotrzebowania kalorycznego dla użytkownika
 * Zakladka "Aktywność"
 */
public class SportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String SHARED_PREFS = "shaaredPrefs";

    private Button buttonSave;

    private Map<Integer, Integer> longIntegerMap = new LinkedHashMap<>();

    private LinearLayout linearLayoutAutomatically;
    private LinearLayout linearLayoutManually;
    private TextInputEditText textInputEditTextCalories;
    private TextInputEditText textInputEditTextProtein;
    private TextInputEditText textInputEditTextCarb;
    private TextInputEditText textInputEditTextFat;
    private Spinner spinnerActivity;
    private Spinner spinnerKindOfSport;
    private Spinner spinnerGoal;
    private SharedPreferences sharedPreferences;
    protected String optionSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sport, container, false);


        textInputEditTextCalories = view.findViewById(R.id.textInputEditTextKalorie);
        textInputEditTextProtein = view.findViewById(R.id.textInputEditTextProtein);
        textInputEditTextCarb = view.findViewById(R.id.textInputEditTextCarb);
        textInputEditTextFat = view.findViewById(R.id.textInputEditTextFat);

        sharedPreferences =  requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        Button buttonAutomatically = view.findViewById(R.id.buttonAutomatycznie);
        Button buttonManually = view.findViewById(R.id.buttonRęczne);

        linearLayoutAutomatically = view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutManually = view.findViewById(R.id.linearLayoutRęczne);

        getMacro(sharedPreferences);

        optionSelected = String.valueOf(sharedPreferences.getString("optionSelected", "0"));

        boolean equals = optionSelected.equals("0");
        if(equals) setAutomatically();
        else setManually();

        buttonAutomatically.setOnClickListener(auto -> {
            setAutomatically();
            optionSelected = "0";
        });

        buttonManually.setOnClickListener(manually -> {
            setManually();
            optionSelected = "1";
        });

        buttonSave = view.findViewById(R.id.buttonSave);

        spinnerActivity = view.findViewById(R.id.spinnerAktywnosc);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(requireNonNull(getActivity()), R.array.spinnerActivities, R.layout.spinner_item_my);
        setSpinners(spinnerActivity, adapterActivity,"spinnerAktywnosc");

        spinnerKindOfSport = view.findViewById(R.id.spinnerRodzajSportu);
        ArrayAdapter<CharSequence> adapterKindOfSport = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerTypeOfActivities, R.layout.spinner_item_my);
        setSpinners(spinnerKindOfSport, adapterKindOfSport,"spinnerRodzajSportu");

        spinnerGoal = view.findViewById(R.id.spinnerCel);
        ArrayAdapter<CharSequence> adapterGoal = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerGoals, R.layout.spinner_item_my);
        setSpinners(spinnerGoal, adapterGoal,"spinnerCel");

        buttonSave.setEnabled(true);
        buttonSave.setOnClickListener(save -> saveOptions());
        return view;
    }

    /**
     * Ustawia wartosci dla wszystkich spinnerow
     * @param spinner   przekazywany do ustawienia na nim wartosci
     * @param adapter   sluzy do ustawienie na niego specjalnego layoutu
     * @param stringValueSpinner    wartosc do jakiej odwoluje sie schared preferences
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

        if (optionSelected.equals("0")) {
                editor.putString("spinnerAktywnosc", requireNonNull(this.longIntegerMap.get(2131296492)).toString()); //InputString: from the EditText
                spinnerActivity.setSelection(requireNonNull(longIntegerMap.get(2131296492)));
                editor.putString("spinnerRodzajSportu", requireNonNull(this.longIntegerMap.get(2131296494)).toString()); //InputString: from the EditText
                spinnerKindOfSport.setSelection(requireNonNull(longIntegerMap.get(2131296494)));
                editor.putString("spinnerCel", requireNonNull(this.longIntegerMap.get(2131296493)).toString()); //InputString: from the EditText
                spinnerGoal.setSelection(requireNonNull(longIntegerMap.get(2131296493)));
                editor.apply();
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
                editor.commit();
            } else
                Toast.makeText(getContext(), "Wartość procentowa nie jest równa 100%", Toast.LENGTH_SHORT).show();

            int calories = Integer.parseInt(requireNonNull(textInputEditTextCalories.getText()).toString());
            if ((calories) < 100)
                Toast.makeText(getContext(), "Wartość jest za mała", Toast.LENGTH_SHORT).show();
             else {
                editor.putString("textCalories", String.valueOf(calories));
                editor.commit();

                HideSoftKeyboard.hideSoftKeyboard(requireNonNull(getActivity()));
                Toast.makeText(getContext(), "Zapisano!", Toast.LENGTH_SHORT).show();
            }
        }


        buttonSave.setEnabled(true);
    }

    /**
     * Mtoda sluzaca do ustawienia tekstu na textInput'ach
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
    public void setAutomatically(){
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
    public void setManually(){
        ViewGroup.LayoutParams paramsAutomatically = linearLayoutAutomatically.getLayoutParams();
        paramsAutomatically.height = 0;
        paramsAutomatically.width = 0;
        linearLayoutAutomatically.setLayoutParams(paramsAutomatically);
        linearLayoutManually.getLayoutParams().height = WRAP_CONTENT;
        linearLayoutManually.getLayoutParams().width = MATCH_PARENT;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        longIntegerMap.put(parent.getId(),position);
        buttonSave.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // empty method from AdapterView.OnItemSelectedListener
    }
}
