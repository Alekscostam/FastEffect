package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.database.Cursor;
import android.util.Log;

import java.util.Arrays;

/**
 * Klasa służaca do zamykania wszelkich obiektów
 */
public class Closer {

    public static final String TAG ="com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses";

    private Closer() {
    }

    public static void closeCursors(Cursor... cursors) {
        Log.i(TAG,"closeCursors - zamykanie wszystkich kursorow");
        Arrays.stream(cursors).forEach(Cursor::close);
    }
}
