package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Klasa toworzca wlasny SnackBar
 * Chwilowo tylko wersja standardowa
 */
public class CustomSnackBars {

    private CustomSnackBars() {
        //Prywatny konstruktor
    }

    /**
     * customSnackBarStandard - standardowa wersja z tekstem na środku
     */
    public static Snackbar customSnackBarStandard(String text, View view) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        View viewSnack = snackbar.getView();
        TextView textViewSnack = viewSnack.findViewById(android.support.design.R.id.snackbar_text);
        textViewSnack.setTextSize(15);
        textViewSnack.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return snackbar;
    }
}
