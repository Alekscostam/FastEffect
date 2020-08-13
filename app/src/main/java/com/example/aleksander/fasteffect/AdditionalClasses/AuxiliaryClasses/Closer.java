package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.database.Cursor;

import java.util.Arrays;

/**
 * Klasa służaca do zamykania wszelkich obiektów
 */
public class Closer {

    private Closer() {
    }

    public static void closeCursors(Cursor... cursors) {
        Arrays.stream(cursors).forEach(Cursor::close);
    }
}
