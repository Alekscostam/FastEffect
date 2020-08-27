package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class HideSoftKeyboard {

    public static final String TAG = "com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses";

    private HideSoftKeyboard() {
    }

    public static void hideSoftKeyboard(Activity activity) {

        Log.i(TAG,"hideSoftKeyboard - ukrycie Keyboard");

        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
