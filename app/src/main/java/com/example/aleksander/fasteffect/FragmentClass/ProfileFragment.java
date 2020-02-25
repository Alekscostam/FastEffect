package com.example.aleksander.fasteffect.FragmentClass;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.MainActivity;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
//

    public ProfileFragment() {
        // Required empty public constructor
//
    }

    public static final String SHARED_PREFS = "shaaredPrefs";

    private TextInputEditText TextInputEditTextWiek;
    private TextInputEditText TextInputEditTextWaga;
    private TextInputEditText TextInputEditTextWzrost;

    private Button buttonZapisz;
    private Button buttonWczytaj;
    private RadioGroup radioButtonPlec;
    private RadioButton radioButtonM;
    private RadioButton radioButtonW;

    FirebaseUser user;
    String uid;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    String referenceName = "Users";
    String user_plec;
    String childWaga = "waga";
    String childWiek = "wiek";
    String childWzrost = "wzrost";
    String childEmial = "email";
    String childPlec = "plec";

    String user_email;
    String user_wiek;
    String user_waga;
    String user_wzrost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextInputEditTextWiek = (TextInputEditText) view.findViewById(R.id.TextInputEditTextWiek);
        TextInputEditTextWzrost = (TextInputEditText) view.findViewById(R.id.TextInputEditTextWzrost);
        TextInputEditTextWaga = (TextInputEditText) view.findViewById(R.id.TextInputEditTextWaga);
        radioButtonPlec = (RadioGroup) view.findViewById(R.id.RadioPlec);
        radioButtonM = (RadioButton) view.findViewById(R.id.radioButtonM);
        radioButtonW = (RadioButton) view.findViewById(R.id.radioButtonW);
        final TextView textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference(referenceName);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_email = dataSnapshot.child(uid).child(childEmial).getValue(String.class);
                user_wiek = dataSnapshot.child(uid).child(childWiek).getValue(String.class);
                user_waga = dataSnapshot.child(uid).child(childWaga).getValue(String.class);
                user_wzrost = dataSnapshot.child(uid).child(childWzrost).getValue(String.class);
                user_plec = dataSnapshot.child(uid).child(childPlec).getValue(String.class);

                textViewEmail.setText(user_email);

                TextInputEditTextWiek.setText(user_wiek);
                TextInputEditTextWaga.setText(user_waga);
                TextInputEditTextWzrost.setText(user_wzrost);

                if (user_plec.equals("M")) {
                    radioButtonM.setChecked(true);
                } else if (user_plec.equals("W")) {
                    radioButtonW.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Double.valueOf(TextInputEditTextWaga.getText().toString()) > 200) {
                    Toast.makeText(getContext(), "Nieprawidłowa waga", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference(referenceName);

                    if (Double.valueOf(TextInputEditTextWaga.getText().toString()) > 200)

                        Toast.makeText(getContext(), TextInputEditTextWaga.getText().toString(), Toast.LENGTH_SHORT).show();

                    user_waga = TextInputEditTextWaga.getText().toString();
                    user_wiek = TextInputEditTextWiek.getText().toString();
                    user_wzrost = TextInputEditTextWzrost.getText().toString();

                    if (radioButtonM.isChecked()) {
                        user_plec = "M";

                    } else if (radioButtonW.isChecked()) {
                        user_plec = "W";

                    }

                    databaseReference.child(uid).child(childWaga).setValue(user_waga);
                    databaseReference.child(uid).child(childWiek).setValue(user_wiek);
                    databaseReference.child(uid).child(childWzrost).setValue(user_wzrost);
                    databaseReference.child(uid).child(childPlec).setValue(user_plec);

                    setShared(TextInputEditTextWaga.getText().toString(), TextInputEditTextWiek.getText().toString(), TextInputEditTextWzrost.getText().toString(), user_plec);

                    Toast.makeText(getContext(), "Zapisano zmiany!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    public void setShared(String waga, String wiek, String wzrost, String plec) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("optionWaga", waga);
        editor.putString("optionWiek", wiek);
        editor.putString("optionWzrost", wzrost);
        editor.putString("optionPlec", plec);

        editor.commit();


    }

}
