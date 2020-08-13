package com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses;

import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa wykorzystywana do komunikacji/serializacji aplikacji z baza zdalna
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Zmienne musza odwzorowyac nazwy takie jakie wystepuja w zdalnej bazie danych
     */
   private Double Kalorie;
   private Double Białko;
   private Double Tłuszcze;
   private Double Węglowodany;
   private Double Błonnik;

    @NonNull
    @Override
    public String toString() {
        return
                "  Kalorie: " + Kalorie +
                ", B: " + Białko +
                ", T: " + Tłuszcze +
                ", W: " + Węglowodany +
                ", Błonnik: " + Błonnik ;
    }
}
