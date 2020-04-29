package com.example.aleksander.fasteffect.AuxiliaryClass;

public class InformationProduct {

    private static String nazwa;
    private static String ilosc;


    public static String getNazwa() {

        return nazwa;
    }

    public static void setNazwa(String nazwa) {
        int znakNazwaKalorie = nazwa.indexOf("|");
        String ciagNazwa = nazwa.substring(0, znakNazwaKalorie);
        InformationProduct.nazwa = ciagNazwa;
    }

    public static String getIlosc() {

        return ilosc;
    }

    public static void setIlosc(String ilosc) {

        int indexIlosc = ilosc.indexOf("Ilość:") + 6;
        String ciagIlosc = ilosc.substring(indexIlosc);
        ciagIlosc.replaceAll("\\s+", "");
        InformationProduct.ilosc = ciagIlosc;
    }
}
