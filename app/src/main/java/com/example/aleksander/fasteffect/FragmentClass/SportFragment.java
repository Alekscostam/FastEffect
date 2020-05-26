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

import com.example.aleksander.fasteffect.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportFragment extends Fragment {

    int option = 1;

    public SportFragment() {}

    public static final String SHARED_PREFS = "shaaredPrefs";

    Button buttonSave;
    int wartoscAktywnosc;
    int wartoscRodzajSportu;
    int wartoscCel;

    TextInputEditText textInputEditTextCalories;
    TextInputEditText textInputEditTextProtein;
    TextInputEditText textInputEditTextCarb;
    TextInputEditText textInputEditTextFat;
    LinearLayout linearLayoutAutomatyczne;
    LinearLayout linearLayoutReczne;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sport, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        Button buttonAutomatycznie = view.findViewById(R.id.buttonAutomatycznie);
        Button buttonRecznie = view.findViewById(R.id.buttonRęczne);

        linearLayoutAutomatyczne = view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutReczne = view.findViewById(R.id.linearLayoutRęczne);

        String optionSelected = sharedPreferences.getString("optionSelected", "0");
        final String[] currentValue = {optionSelected};

        option = Integer.valueOf(currentValue[0]);
        setPref(option);

        textInputEditTextCalories = view.findViewById(R.id.textInputEditTextKalorie);
        textInputEditTextProtein = view.findViewById(R.id.textInputEditTextProtein);
        textInputEditTextCarb = view.findViewById(R.id.textInputEditTextCarb);
        textInputEditTextFat = view.findViewById(R.id.textInputEditTextFat);

        String dataMacro = sharedPreferences.getString("textCalories", "0"); //no id: default value
        textInputEditTextCalories.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textProtein", "0"); //no id: default value
        textInputEditTextProtein.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textCarb", "0"); //no id: default value
        textInputEditTextCarb.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textFat", "0"); //no id: default value
        textInputEditTextFat.setText(dataMacro);

  /*      String dataCalories = sharedPreferences.getString("textCalories", "0"); //no id: default value
        textInputEditTextCalories.setText(dataCalories);

        String dataProtein = sharedPreferences.getString("textProtein", "0"); //no id: default value
        textInputEditTextProtein.setText(dataProtein);

        String dataCarb = sharedPreferences.getString("textCarb", "0"); //no id: default value
        textInputEditTextCarb.setText(dataCarb);

        String dataFat = sharedPreferences.getString("textFat", "0"); //no id: default value
        textInputEditTextFat.setText(dataFat);*/

        buttonAutomatycznie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams paramsReczne = linearLayoutReczne.getLayoutParams();
                paramsReczne.height = 0;
                paramsReczne.width = 0;
                linearLayoutReczne.setLayoutParams(paramsReczne);
                linearLayoutAutomatyczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                linearLayoutAutomatyczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;

                currentValue[0] = "0";

            }
        });

        buttonRecznie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams paramsAutomatyczne = linearLayoutAutomatyczne.getLayoutParams();
                paramsAutomatyczne.height = 0;
                paramsAutomatyczne.width = 0;
                linearLayoutAutomatyczne.setLayoutParams(paramsAutomatyczne);
                linearLayoutReczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                linearLayoutReczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;

                currentValue[0] = "1";

            }
        });


        buttonSave = view.findViewById(R.id.buttonSave);

        final Spinner spinnerAktywnosc = view.findViewById(R.id.spinnerAktywnosc);
        ArrayAdapter<CharSequence> adapterAktywnosc = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerActivities, R.layout.spinner_item_my);
        adapterAktywnosc.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerAktywnosc.setAdapter(adapterAktywnosc);

        buttonSave.setEnabled(true);

        String dataAktywnosc = sharedPreferences.getString("spinnerAktywnosc", "0");
        spinnerAktywnosc.setSelection(Integer.valueOf(dataAktywnosc));

        spinnerAktywnosc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String position = String.valueOf(spinnerAktywnosc.getSelectedItemPosition());

                wartoscAktywnosc = Integer.valueOf(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner spinnerRodzajSportu = view.findViewById(R.id.spinnerRodzajSportu);
        ArrayAdapter<CharSequence> adapterRodzajSportu = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerTypeOfActivities, R.layout.spinner_item_my);
        adapterRodzajSportu.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerRodzajSportu.setAdapter(adapterRodzajSportu);

        String dataRodzajSportu = sharedPreferences.getString("spinnerRodzajSportu", "0"); //no id: default value
        spinnerRodzajSportu.setSelection(Integer.valueOf(dataRodzajSportu));

        spinnerRodzajSportu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String position = String.valueOf(spinnerRodzajSportu.getSelectedItemPosition());

                wartoscRodzajSportu = Integer.valueOf(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final Spinner spinnerCel = view.findViewById(R.id.spinnerCel);
        ArrayAdapter<CharSequence> adapterCel = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerGoals, R.layout.spinner_item_my);
        adapterCel.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerCel.setAdapter(adapterCel);

        String dataCel = sharedPreferences.getString("spinnerCel", "0"); //no id: default value
        spinnerCel.setSelection(Integer.valueOf(dataCel));

        spinnerCel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String position = String.valueOf(spinnerCel.getSelectedItemPosition());
                wartoscCel = Integer.valueOf(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (currentValue[0].equals("0")) {
                    optionSet("0");

                    editor.putString("spinnerAktywnosc", String.valueOf(wartoscAktywnosc)); //InputString: from the EditText
                    spinnerAktywnosc.setSelection(wartoscAktywnosc);
                    editor.putString("spinnerRodzajSportu", String.valueOf(wartoscRodzajSportu)); //InputString: from the EditText
                    spinnerRodzajSportu.setSelection(wartoscRodzajSportu);
                    editor.putString("spinnerCel", String.valueOf(wartoscCel)); //InputString: from the EditText
                    spinnerCel.setSelection(wartoscCel);

                    editor.commit();

                }
                // druga opcja
                if (currentValue[0].equals("1")) {
                    optionSet("1");
                    int Protein = Integer.valueOf(textInputEditTextProtein.getText().toString());
                    int Carb = Integer.valueOf(textInputEditTextCarb.getText().toString());
                    int Fat = Integer.valueOf(textInputEditTextFat.getText().toString());

                    {
                        if ((Protein + Carb + Fat) == 100) {

                            editor.putString("textProtein", String.valueOf(Protein));
                            editor.putString("textCarb", String.valueOf(Carb));
                            editor.putString("textFat", String.valueOf(Fat));

                            editor.commit();

                        } else {
                            Toast.makeText(getContext(), "Wartość procentowa nie jest równa 100%", Toast.LENGTH_SHORT).show();

                        }

                    }
                    {
                        int Calories = Integer.valueOf(textInputEditTextCalories.getText().toString());
                        if ((Calories) < 100) {
                            Toast.makeText(getContext(), "Wartosc za mala", Toast.LENGTH_SHORT).show();
                        } else {

                            editor.putString("textCalories", String.valueOf(Calories));
                            editor.commit();
                        }
                    }

                }

                Toast.makeText(getContext(), "Zapisano", Toast.LENGTH_SHORT).show();
                buttonSave.setEnabled(true);
            }
        });

        return view;

    }

    public void optionSet(String option) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("optionSelected", String.valueOf(option));
        editor.commit();

    }

    public void setPref(int optionSelected) {
        if (optionSelected == 0) {
            ViewGroup.LayoutParams paramsReczne = linearLayoutReczne.getLayoutParams();
            paramsReczne.height = 0;
            paramsReczne.width = 0;
            linearLayoutReczne.setLayoutParams(paramsReczne);
            linearLayoutAutomatyczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            linearLayoutAutomatyczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
        if (optionSelected == 1) {
            ViewGroup.LayoutParams paramsAutomatyczne = linearLayoutAutomatyczne.getLayoutParams();
            paramsAutomatyczne.height = 0;
            paramsAutomatyczne.width = 0;
            linearLayoutAutomatyczne.setLayoutParams(paramsAutomatyczne);
            linearLayoutReczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            linearLayoutReczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
    }

}
