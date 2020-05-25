package com.example.aleksander.fasteffect.AuxiliaryClass;

public class InformationProduct {

    private static String nazwa;
    private static String ilosc;


    public static String getNazwa() {

        return nazwa;
    }

    public InformationProduct(String nazwa, String ilosc) {

        setNazwa(nazwa);
        setIlosc(ilosc);

    }

    public static void setNazwa(String nazwa) {

        InformationProduct.nazwa = nazwa.substring(0, nazwa.indexOf("|"));
    }

    public  static String getIlosc() {

        return ilosc;
    }

    public  static void setIlosc(String ilosc) {

        String ciagIlosc = ilosc.substring(ilosc.indexOf("Ilość:") + 6);
        ciagIlosc.replaceAll("\\s+", "");
        InformationProduct.ilosc = ciagIlosc;
    }
}
