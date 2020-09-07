package com.example.aleksander.fasteffect.FragmentClasses;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.CustomSnackBars;
import com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses.HideSoftKeyboard;
import com.example.aleksander.fasteffect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.aleksander.fasteffect.AdditionalClasses.User.childAge;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childGender;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childHeight;
import static com.example.aleksander.fasteffect.AdditionalClasses.User.childWeight;


/**
 * {@link Fragment}
 * Klasa posiada podstawowe informacje o użytkowniku
 * Zakladka "Profil"
 */
public class ProfileFragment extends Fragment implements ValueEventListener {

    public static final String TAG = "com.example.aleksander.fasteffect.FragmentClass";

    private String uid;
    private String referenceName = "Users";

    private TextInputEditText textInputEditTextAge, textInputEditTextHeight, textInputEditTextWeight;
    private RadioButton radioButtonM, radioButtonW;
    private TextView textViewEmail;

    private String userGender;
    private String userAge;
    private String userWeight;
    private String userHeight;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);

        databaseReference = database.getReference(referenceName);
        databaseReference.addValueEventListener(ProfileFragment.this);

        Button buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(save -> onSaveRef());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        uid = user.getUid();

        return view;
    }

    /**
     * Zaladowanie informacji o uzytkowniku ze zdalnej bazy danych po wejsciu w zakładke "Profil"
     */
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String childEmail = "email";
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

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        databaseError.getMessage();
    }

    private void initViews(View view) {

        textInputEditTextAge = view.findViewById(R.id.TextInputEditTextWiek);
        textInputEditTextHeight = view.findViewById(R.id.TextInputEditTextWzrost);
        textInputEditTextWeight = view.findViewById(R.id.TextInputEditTextWaga);
        radioButtonM = view.findViewById(R.id.radioButtonM);
        radioButtonW = view.findViewById(R.id.radioButtonW);
        textViewEmail = view.findViewById(R.id.textViewEmail);
    }

    /**
     * Zmiana w zdalnej bazie danych po kliknieciu przycisku "zapisz"
     */
    private void onSaveRef() {

        Log.i(TAG, "onSaveRef - zimiana danych dla uzytkownika");
        HideSoftKeyboard.hideSoftKeyboard(Objects.requireNonNull(getActivity()));

        if (Double.parseDouble(String.valueOf(textInputEditTextWeight.getText())) > 200)
            CustomSnackBars.customSnackBarStandard("Nieprawidłowa waga", getView()).show();
         else {
            databaseReference = FirebaseDatabase.getInstance().getReference(referenceName);

            userWeight = String.valueOf(textInputEditTextWeight.getText());
            userAge = String.valueOf(textInputEditTextAge.getText());
            userHeight = String.valueOf(textInputEditTextHeight.getText());

            if (radioButtonM.isChecked()) userGender = "M";
            else if (radioButtonW.isChecked()) userGender = "W";

            if(userAge.isEmpty()||userHeight.isEmpty()||userWeight.isEmpty()) {
                Toast.makeText(getContext(), "Wartości nie mogą być puste!", Toast.LENGTH_LONG).show();
            }
            else{
                databaseReference.child(uid).child(childWeight).setValue(userWeight);
                databaseReference.child(uid).child(childAge).setValue(userAge);
                databaseReference.child(uid).child(childHeight).setValue(userHeight);
                databaseReference.child(uid).child(childGender).setValue(userGender);
                CustomSnackBars.customSnackBarStandard("Zapisano zmiany!", getView()).show();
            }

        }
    }

}
