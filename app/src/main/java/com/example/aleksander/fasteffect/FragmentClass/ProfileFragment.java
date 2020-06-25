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

import androidx.annotation.NonNull;

import com.example.aleksander.fasteffect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
//

    public ProfileFragment() {}

    public static final String SHARED_PREFS = "shaaredPrefs";

    private TextInputEditText TextInputEditTextWiek;
    private TextInputEditText TextInputEditTextWaga;
    private TextInputEditText TextInputEditTextWzrost;

    private RadioButton radioButtonM;
    private RadioButton radioButtonW;

    FirebaseUser user;
    String uid;
    DatabaseReference databaseReference;
    String referenceName = "Users";

    String childWaga = "waga";
    String childWiek = "wiek";
    String childWzrost = "wzrost";
    String childEmial = "email";
    String childPlec = "plec";

    String user_plec;
    String user_email;
    String user_wiek;
    String user_waga;
    String user_wzrost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextInputEditTextWiek = view.findViewById(R.id.TextInputEditTextWiek);
        TextInputEditTextWzrost = view.findViewById(R.id.TextInputEditTextWzrost);
        TextInputEditTextWaga = view.findViewById(R.id.TextInputEditTextWaga);

        radioButtonM = view.findViewById(R.id.radioButtonM);
        radioButtonW = view.findViewById(R.id.radioButtonW);
        final TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        Button buttonSave = view.findViewById(R.id.buttonSave);

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
