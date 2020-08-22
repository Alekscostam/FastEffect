package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class HideSoftKeyboard {

    private HideSoftKeyboard() {
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
