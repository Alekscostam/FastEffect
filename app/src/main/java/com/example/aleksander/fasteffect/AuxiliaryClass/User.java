package com.example.aleksander.fasteffect.AuxiliaryClass;

public class User {

    public String waga, wiek, wzrost, plec, email;

    public User() {

    }

    public User(String waga, String wiek, String wzrost, String plec, String email) {
        this.waga = waga;
        this.wiek = wiek;
        this.wzrost = wzrost;
        this.plec = plec;
        this.email = email;

    }

    public String getWaga() {
        return waga;
    }

    public void setWaga(String waga) {
        this.waga = waga;
    }

    public String getWiek() {
        return wiek;
    }

    public void setWiek(String wiek) {
        this.wiek = wiek;
    }

    public String getWzrost() {
        return wzrost;
    }

    public void setWzrost(String wzrost) {
        this.wzrost = wzrost;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
