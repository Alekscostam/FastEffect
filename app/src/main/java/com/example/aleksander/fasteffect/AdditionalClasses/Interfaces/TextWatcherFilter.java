package com.example.aleksander.fasteffect.AdditionalClasses.Interfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Interfejs rozszerzajacy interfejs TextWatcher
 * Ogranicza się do implementacji tylko onTextChanged z interfejsu TextWatcher
 */
@FunctionalInterface
public interface TextWatcherFilter extends TextWatcher {

    @Override
    void beforeTextChanged(CharSequence s, int start, int count, int after);

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    default void afterTextChanged(Editable s) {
    }

}
