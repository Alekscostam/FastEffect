package com.example.aleksander.fasteffect.AuxiliaryClass;

public class Produkty {

    public Double Nazwa;
    public Double Kalorie;
    public Double Białko;
    public Double Tłuszcze;
    public Double Węglowodany;
    public Double Błonnik;
    public String Nietolerancje;


    public Produkty(Double Kalorie, Double Białko, Double Tłuszcze, Double Węglowodany, Double Błonnik,
                    String Nietolerancje) {
        this.Kalorie = Kalorie;
        this.Białko = Białko;
        this.Tłuszcze = Tłuszcze;
        this.Węglowodany = Węglowodany;
        this.Błonnik = Błonnik;
        this.Nietolerancje = Nietolerancje;

    }

    public Produkty() {

    }

    public Produkty(Double Kalorie, Double Białko, Double Tłuszcze, Double Węglowodany, Double Błonnik) {
        this.Kalorie = Kalorie;
        this.Białko = Białko;
        this.Tłuszcze = Tłuszcze;
        this.Węglowodany = Węglowodany;
        this.Błonnik = Błonnik;


    }

    public String toString() {

        String wynik = "";
        wynik = "| " + this.Kalorie + " kcal \nB: " + Białko + " | T: " + Tłuszcze + " | W: " + Węglowodany +
                " | Błonnik: " + Błonnik + " | Zawiera:" + Nietolerancje;

        return wynik;

    }


}
