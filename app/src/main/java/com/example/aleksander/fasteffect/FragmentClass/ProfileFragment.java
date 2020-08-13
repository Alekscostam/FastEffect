package com.example.aleksander.fasteffect.FragmentClass;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


/**
 * {@link Fragment}
 * Klasa posiada podstawowe informacje o użytkowniku
 * Zakladka "Profil"
 */
public class ProfileFragment extends Fragment {

    public static final String SHARED_PREFS = "shaaredPrefs";

    private String uid;
    private String referenceName = "Users";

    private String childWeight = "weight";
    private String childAge = "age";
    private String childHeight = "height";
    private String childEmail = "email";
    private String childGender = "gender";

    private TextInputEditText textInputEditTextAge;
    private TextInputEditText textInputEditTextHeight;
    private TextInputEditText textInputEditTextWeight;
    private RadioButton radioButtonM;
    private RadioButton radioButtonW;
    private TextView textViewEmail;

    private String userGender;
    private String userAge;
    private String userWeight;
    private String userHeight;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        databaseReference = database.getReference(referenceName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textInputEditTextAge = view.findViewById(R.id.TextInputEditTextWiek);
        textInputEditTextHeight = view.findViewById(R.id.TextInputEditTextWzrost);
        textInputEditTextWeight = view.findViewById(R.id.TextInputEditTextWaga);
        radioButtonM = view.findViewById(R.id.radioButtonM);
        radioButtonW = view.findViewById(R.id.radioButtonW);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        assert user != null;
        uid = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onLoadRef(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });

        buttonSave.setOnClickListener(save -> onSaveRef());
        return view;
    }

    private void onSaveRef() {
        if (Double.parseDouble(String.valueOf(textInputEditTextWeight.getText())) > 200)
            Toast.makeText(getContext(), "Nieprawidłowa waga", Toast.LENGTH_SHORT).show();
        else {
            databaseReference = FirebaseDatabase.getInstance().getReference(referenceName);

            if (Double.parseDouble(String.valueOf(textInputEditTextWeight.getText())) > 200)
                Toast.makeText(getContext(), String.valueOf(textInputEditTextWeight.getText()), Toast.LENGTH_SHORT).show();

            userWeight = String.valueOf(textInputEditTextWeight.getText());
            userAge = String.valueOf(textInputEditTextAge.getText());
            userHeight = String.valueOf(textInputEditTextHeight.getText());

            if (radioButtonM.isChecked()) userGender = "M";
            else if (radioButtonW.isChecked()) userGender = "W";

            databaseReference.child(uid).child(childWeight).setValue(userWeight);
            databaseReference.child(uid).child(childAge).setValue(userAge);
            databaseReference.child(uid).child(childHeight).setValue(userHeight);
            databaseReference.child(uid).child(childGender).setValue(userGender);

            setShared(
                    Objects.requireNonNull(textInputEditTextWeight.getText()).toString(),
                    Objects.requireNonNull(textInputEditTextAge.getText()).toString(),
                    Objects.requireNonNull(textInputEditTextHeight.getText()).toString(),
                    userGender);

            Toast.makeText(getContext(), "Zapisano zmiany!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoadRef(DataSnapshot dataSnapshot) {
        String userEmail = dataSnapshot.child(uid).child(childEmail).getValue(String.class);
        userAge = dataSnapshot.child(uid).child(childAge).getValue(String.class);
        userWeight = dataSnapshot.child(uid).child(childWeight).getValue(String.class);
        userHeight = dataSnapshot.child(uid).child(childHeight).getValue(String.class);
        userGender = String.valueOf(dataSnapshot.child(uid).child(childGender).getValue(String.class));

        textViewEmail.setText(userEmail);
        textInputEditTextAge.setText(userAge);
        textInputEditTextWeight.setText(userWeight);
        textInputEditTextHeight.setText(userHeight);

        if (userGender.equals("M")) {
            radioButtonM.setChecked(true);
        } else if (userGender.equals("W")) {
            radioButtonW.setChecked(true);
        }
    }

    public void setShared(String weight, String age, String height, String gender) {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("optionWaga", weight);
        editor.putString("optionWiek", age);
        editor.putString("optionWzrost", height);
        editor.putString("optionPlec", gender);
        editor.apply();
    }
}
