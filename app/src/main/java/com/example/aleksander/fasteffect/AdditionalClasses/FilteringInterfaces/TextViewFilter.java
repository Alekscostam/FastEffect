package com.example.aleksander.fasteffect.AdditionalClasses.FilteringInterfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Interfejs rozszerzajacy interfejs TextWatcher
 * Ogranicza się do implementacji tylko onTextChanged z interfejsu TextWatcher
 */
public interface TextViewFilter extends TextWatcher {
    @Override
    void beforeTextChanged(CharSequence s, int start, int count, int after);

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    default void afterTextChanged(Editable s) {
    }


}
