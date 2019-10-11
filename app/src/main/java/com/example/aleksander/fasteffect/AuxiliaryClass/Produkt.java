package com.example.aleksander.fasteffect.AuxiliaryClass;

public class Produkt {
    public Double Kalorie;
    public Double Białko;
    public Double Tłuszcze;
    public Double Węglowodany;
    public Double Błonnik;
    public String Nietolerancje;


    public Produkt(Double Kalorie, Double Białko, Double Tłuszcze, Double Węglowodany, Double Błonnik, String Nietolerancje) {
        this.Kalorie = Kalorie;
        this.Białko = Białko;
        this.Tłuszcze = Tłuszcze;
        this.Węglowodany = Węglowodany;
        this.Błonnik = Błonnik;
        this.Nietolerancje = Nietolerancje;

    }

    public Produkt() {

    }

    public String toString() {


        return "| " + this.Kalorie + " kcal \nB: " + Białko + " | T: " + Tłuszcze + " | W: " + Węglowodany + " | Błonnik: " + Błonnik + " | Nietolerancje:"+Nietolerancje;

    }




}
