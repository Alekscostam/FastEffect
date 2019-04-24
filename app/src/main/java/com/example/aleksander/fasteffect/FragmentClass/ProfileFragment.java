package com.example.aleksander.fasteffect.FragmentClass;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aleksander.fasteffect.MainActivity;
import com.example.aleksander.fasteffect.R;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor

    }


    private EditText editTextWiek;
    private EditText editTextWzrost;
    private EditText editTextWaga;

    private Button buttonZapisz;
    private Button buttonWczytaj;
    private RadioGroup radioButtonPlec;
    private RadioButton radioButtonM;
    private RadioButton radioButtonW;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        editTextWiek = (EditText) view.findViewById(R.id.editTextWiek);
        editTextWzrost = (EditText) view.findViewById(R.id.editTextWzrost);
        editTextWaga = (EditText) view.findViewById(R.id.editTextWaga);
        radioButtonPlec = (RadioGroup) view.findViewById(R.id.RadioPlec);
        radioButtonM = (RadioButton) view.findViewById(R.id.radioButtonM);
        radioButtonW = (RadioButton) view.findViewById(R.id.radioButtonW);


        //  radioButtonM.setClickable(false);
        //  radioButtonW.setClickable(false);


        final String[] plec = {""};

        radioButtonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plec[0] = "M";
            }
        });
        radioButtonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plec[0] = "W";
            }
        });


        try {
            String nazwa_pliku = "uzytkownik.txt";

            String[] wartosc = new String[20];

            //input do odczytu
            try {
                int a = 0;
                FileInputStream plik1 = getActivity().openFileInput(nazwa_pliku);
                DataInputStream dataIO = new DataInputStream(plik1);
                String odczyt_l = "", odczyt = "";
                while ((odczyt_l = dataIO.readLine()) != null) {

                    odczyt = odczyt + "," + odczyt_l;
                    wartosc[a] = odczyt_l;
                    a = a + 1;
                }
                plik1.close();
                editTextWaga.setText(wartosc[0]);
                editTextWzrost.setText(wartosc[1]);
                editTextWiek.setText(wartosc[2]);

                String wybor = wartosc[3];

                if (wybor.equals("M")) {
                    radioButtonM.setChecked(true);
                    radioButtonW.setChecked(false);

                } else if (wybor.equals("W")) {

                    radioButtonW.setChecked(true);
                    radioButtonM.setChecked(false);
                }

                // Toast.makeText(getActivity(), odczyt, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "brak danych", Toast.LENGTH_SHORT).show();
        }

        buttonZapisz = (Button) view.findViewById(R.id.buttonSave);


        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nazwa_pliku = "uzytkownik.txt";

                //output do zapisu
                try {
                    String zawartosc = editTextWaga.getText().toString() + "\r\n"
                            + editTextWiek.getText().toString() + "\r\n"
                            + editTextWzrost.getText().toString() + "\r\n"
                            + plec[0].toString() + "\r\n";

                    FileOutputStream plik = getActivity().openFileOutput(nazwa_pliku, MODE_PRIVATE);
                    plik.write(zawartosc.getBytes());
                    plik.close();
                    Toast.makeText(getActivity(), "Zapisano dane", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;


    }

}
