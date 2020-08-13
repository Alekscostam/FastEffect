package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import lombok.Getter;
import lombok.Setter;

/**
 * Klaa pomocnicza do otrzymywania i operowania na dacie
 */
@Getter
@Setter
public class DataHolder {
    private String data = "";

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }

}
