package com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses;


/**
 * Struktura lokalnej bazy danych
 */
public class SQLDatabaseStructure {

    private SQLDatabaseStructure() {
        // prywatny konstruktor
    }

    /////// BAZA
    public static final String DATABASE_FILE = "bazaEksport.db";

    /////// Tabela Posilek
    public static final String TABLE_MEAL = "Posilek";
    public static final String MEAL_ID_COLUMN = "idPosilek";
    public static final String MEAL_NAME_COLUMN = "Nazwa";
    public static final String MEAL_PROTEIN_COLUMN = "Bialko";
    public static final String MEAL_CARB_COLUMN = "Weglowodany";
    public static final String MEAL_FAT_COLUMN = "Tluszcze";
    public static final String MEAL_FIBRE_COLUMN = "Błonnik";
    public static final String MEAL_CALORIES_COLUMN = "Kalorie";
    public static final String MEAL_AMOUNT_COLUMN = "Ilość";

    /////// Tabela Pora dnia
    public static final String TABLE_TIME_OF_DAY = "PoraDnia";
    public static final String TIME_COLUMN_TIME_OF_DAY = "Pora";

    /////// Tabela Hash
    public static  final String TABLE_HASH = "Hash";
    public static  final String HASH_ID_TIME_OF_DAY = "idPoraDnia";
    public static  final String HASH_ID_MEAL = "idPosilek";
    public static  final String HASH_DATA = "Data";

}

