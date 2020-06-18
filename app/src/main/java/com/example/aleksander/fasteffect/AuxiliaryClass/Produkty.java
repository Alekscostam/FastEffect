package com.example.aleksander.fasteffect.AuxiliaryClass;

public class Produkty {


    public Double Kalorie;
    public Double Białko;
    public Double Tłuszcze;
    public Double Węglowodany;
    public Double Błonnik;



    public Produkty() {
    }

    public Produkty(Double Kalorie, Double Białko, Double Tłuszcze, Double Węglowodany, Double Błonnik) {
        this.Kalorie = Kalorie;
        this.Białko = Białko;
        this.Tłuszcze = Tłuszcze;
        this.Węglowodany = Węglowodany;
        this.Błonnik = Błonnik;

    }


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
