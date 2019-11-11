package com.example.aleksander.fasteffect.FragmentClass;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportFragment extends Fragment {

    int option =1;



    public SportFragment() {
        // Required empty public constructor
    }

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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sport, container, false);

        Button buttonAutomatycznie = (Button) view.findViewById(R.id.buttonAutomatycznie);
        Button buttonRecznie = (Button) view.findViewById(R.id.buttonRęczne);

        linearLayoutAutomatyczne = (LinearLayout) view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutReczne = (LinearLayout) view.findViewById(R.id.linearLayoutRęczne);

        SharedPreferences SharedPreferencesOptionSelected = PreferenceManager.getDefaultSharedPreferences(getContext());
        String optionSelected = SharedPreferencesOptionSelected.getString("optionSelected", "0"); //no id: default value
        final String[] currentValue = {optionSelected};


       /* Toast.makeText(getContext(), currentValue[0], Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), optionSelected, Toast.LENGTH_SHORT).show();*/

        option=Integer.valueOf(currentValue[0]);
        setPref(option);


        textInputEditTextCalories = (TextInputEditText) view.findViewById(R.id.textInputEditTextKalorie);
        textInputEditTextProtein = (TextInputEditText) view.findViewById(R.id.textInputEditTextProtein);
        textInputEditTextCarb = (TextInputEditText) view.findViewById(R.id.textInputEditTextCarb);
        textInputEditTextFat = (TextInputEditText) view.findViewById(R.id.textInputEditTextFat);


        SharedPreferences SharedPreferencesCalories = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataCalories = SharedPreferencesCalories.getString("textCalories", "0"); //no id: default value
        textInputEditTextCalories.setText(dataCalories);


        SharedPreferences SharedPreferencesProtein = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataProtein = SharedPreferencesProtein.getString("textProtein", "0"); //no id: default value
        textInputEditTextProtein.setText(dataProtein);


        SharedPreferences sharedPreferencesCarb = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataCarb = sharedPreferencesCarb.getString("textCarb", "0"); //no id: default value
        textInputEditTextCarb.setText(dataCarb);


        SharedPreferences sharedPreferencesFat = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataFat = sharedPreferencesFat.getString("textFat", "0"); //no id: default value
        textInputEditTextFat.setText(dataFat);


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


        buttonSave = (Button) view.findViewById(R.id.buttonSave);

        final Spinner spinnerAktywnosc = (Spinner) view.findViewById(R.id.spinnerAktywnosc);
        ArrayAdapter<CharSequence> adapterAktywnosc = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerActivities, R.layout.spinner_item_my);
        adapterAktywnosc.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerAktywnosc.setAdapter(adapterAktywnosc);

        buttonSave.setEnabled(true);


        SharedPreferences sharedPreferencesAktywnosc = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataAktywnosc = sharedPreferencesAktywnosc.getString("spinnerAktywnosc", "0"); //no id: default value
        spinnerAktywnosc.setSelection(Integer.valueOf(dataAktywnosc));


        spinnerAktywnosc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                String position = String.valueOf(spinnerAktywnosc.getSelectedItemPosition());


                wartoscAktywnosc = Integer.valueOf(position);
                buttonSave.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Spinner spinnerRodzajSportu = (Spinner) view.findViewById(R.id.spinnerRodzajSportu);
        ArrayAdapter<CharSequence> adapterRodzajSportu = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerTypeOfActivities, R.layout.spinner_item_my);
        adapterRodzajSportu.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerRodzajSportu.setAdapter(adapterRodzajSportu);


        SharedPreferences sharedPreferencesRodzajSportu = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataRodzajSportu = sharedPreferencesRodzajSportu.getString("spinnerRodzajSportu", "0"); //no id: default value
        spinnerRodzajSportu.setSelection(Integer.valueOf(dataRodzajSportu));

        spinnerRodzajSportu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();

                String position = String.valueOf(spinnerRodzajSportu.getSelectedItemPosition());


                wartoscRodzajSportu = Integer.valueOf(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Spinner spinnerCel = (Spinner) view.findViewById(R.id.spinnerCel);
        ArrayAdapter<CharSequence> adapterCel = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerGoals, R.layout.spinner_item_my);
        adapterCel.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerCel.setAdapter(adapterCel);


        SharedPreferences sharedPreferencesCel = PreferenceManager.getDefaultSharedPreferences(getContext());
        String dataCel = sharedPreferencesCel.getString("spinnerCel", "0"); //no id: default value
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

                if (currentValue[0].equals("0")) {
                    optionSet("0");
                    SharedPreferences prefsAktywnosc = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editorAktywnosc = prefsAktywnosc.edit();
                    editorAktywnosc.putString("spinnerAktywnosc", String.valueOf(wartoscAktywnosc)); //InputString: from the EditText
                    editorAktywnosc.commit();
                    spinnerAktywnosc.setSelection(wartoscAktywnosc);

                    SharedPreferences prefsRodzaj = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editorRodzaj = prefsRodzaj.edit();
                    editorRodzaj.putString("spinnerRodzajSportu", String.valueOf(wartoscRodzajSportu)); //InputString: from the EditText
                    editorRodzaj.commit();
                    spinnerRodzajSportu.setSelection(wartoscRodzajSportu);

                    SharedPreferences prefsCel = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editorCel = prefsCel.edit();
                    editorCel.putString("spinnerCel", String.valueOf(wartoscCel)); //InputString: from the EditText
                    editorCel.commit();
                    spinnerCel.setSelection(wartoscCel);



                }
                // druga opcja
                if(currentValue[0].equals("1")) {
                    optionSet("1");
                    int Protein = Integer.valueOf(textInputEditTextProtein.getText().toString());
                    int Carb = Integer.valueOf(textInputEditTextCarb.getText().toString());
                    int Fat = Integer.valueOf(textInputEditTextFat.getText().toString());

                    {
                        if ((Protein + Carb + Fat) == 100) {

                            SharedPreferences SharedPreferencesProtein = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editorProtein = SharedPreferencesProtein.edit();
                            editorProtein.putString("textProtein", String.valueOf(Protein));
                            editorProtein.commit();


                            SharedPreferences SharedPreferencesCarb = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editorCarb = SharedPreferencesCarb.edit();
                            editorCarb.putString("textCarb", String.valueOf(Carb));
                            editorCarb.commit();


                            SharedPreferences SharedPreferencesFat = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editorFat = SharedPreferencesFat.edit();
                            editorFat.putString("textFat", String.valueOf(Fat));
                            editorFat.commit();

                        } else {


                            Toast.makeText(getContext(), "Wartość procentowa nie jest równa 100%", Toast.LENGTH_SHORT).show();

                        }

                    }

                    {

                        int Calories = Integer.valueOf(textInputEditTextCalories.getText().toString());


                        if ((Calories) < 100) {
                            Toast.makeText(getContext(), "Wartosc za mala", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences SharedPreferencesCalories = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editorCalories = SharedPreferencesCalories.edit();
                            editorCalories.putString("textCalories", String.valueOf(Calories));
                            editorCalories.commit();
                        }
                    }


                }

                Toast.makeText(getContext(), "Zapisano", Toast.LENGTH_SHORT).show();
                buttonSave.setEnabled(true);

            }
        });

        return view;

    }


    public void optionSet( String option)
    {

        SharedPreferences SharedPreferencesOptionSelected = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editorSelected = SharedPreferencesOptionSelected.edit();
        editorSelected.putString("optionSelected", String.valueOf(option));
        editorSelected.commit();



    }
    public void setPref( int optionSelected)
    {
        if(optionSelected == 0)
        {
            ViewGroup.LayoutParams paramsReczne = linearLayoutReczne.getLayoutParams();
            paramsReczne.height = 0;
            paramsReczne.width = 0;
            linearLayoutReczne.setLayoutParams(paramsReczne);

            linearLayoutAutomatyczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            linearLayoutAutomatyczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
        if(optionSelected ==1)
        {
            ViewGroup.LayoutParams paramsAutomatyczne = linearLayoutAutomatyczne.getLayoutParams();
            paramsAutomatyczne.height = 0;
            paramsAutomatyczne.width = 0;
            linearLayoutAutomatyczne.setLayoutParams(paramsAutomatyczne);
            linearLayoutReczne.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            linearLayoutReczne.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
    }

}
