package com.example.aleksander.fasteffect;

public class Produkt {
    public String a;
    public String b;
    public String c;




    public  Produkt (String a , String b, String c )
    {
        this.a=a;
        this.b=b;
        this.c=c;
    }
    public Produkt ()
    {

    }


    public String toString()
    {
        return this.a+"." + b +"." + c;
    }

}
