package com.example.aleksander.fasteffect.Repository;

import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_AMOUNT_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_ID_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.MEAL_NAME_COLUMN;
import static com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure.TABLE_MEAL;

/**
 * Interfejs posiadająca zapytania do bazy danych
 */
public interface DatabaseQuery {

    static String getElementByNameAndValueFromPosilek(String name, Integer value) {
        return "SELECT Posilek.idPosilek  " +
                "FROM  Posilek " +
                "WHERE Posilek.Nazwa " +
                "like '" + name + "' " +
                "AND Posilek.Ilość='" + value + "' ";
    }

    static String deleteElementByIdHashFromHash(String idHash) {
        return "DELETE " +
                "FROM Hash " +
                "WHERE Hash.idHash='" + idHash + "' ";
    }

    static String insertElementIntoHashTable(String data, String idMeal, Integer timeOfDay) {
        return "INSERT INTO Hash(Data,idPosilek,idPoraDnia) " +
                "VALUES ('" + data + "','" + idMeal + "','" + timeOfDay + "')";
    }

    static String insertElementIntoMealTable(String name, Double protein, Double carb, Double fat, Double fibre, Integer calories, Integer amount) {
        return "INSERT INTO Posilek(Nazwa,Bialko,Weglowodany,Tluszcze,Błonnik,Kalorie,Ilość)" +
                "VALUES('" + name + "','" + protein + "','" + carb + "','" + fat + "','" + fibre + "','" + calories + "','" + amount + "')";
    }

    static String createTableTimeOfDay() {
        return "CREATE TABLE IF NOT EXISTS 'PoraDnia'" +
                "(idPoraDnia INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Pora TEXT)";
    }

    static String dropTable(String tableName) {
        return "DROP TABLE IF EXISTS "+ tableName;
    }

    static String createTableMeal() {
        return  "CREATE TABLE IF NOT EXISTS 'Posilek'" +
                "(idPosilek INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nazwa TEXT,Bialko REAL,Weglowodany REAL,Tluszcze REAL, Błonnik REAL, Kalorie REAL, Ilość INTEGER NOT NULL)";
    }

    static String createTableHash() {
        return  "CREATE TABLE IF NOT EXISTS 'Hash'" +
                "(idHash INTEGER PRIMARY KEY AUTOINCREMENT, Data NUMERIC NOT NULL," +
                " idPosilek INTEGER NOT NULL,idPoraDnia INTEGER NOT NULL," +
                "CONSTRAINT fk_idPosilek FOREIGN KEY(idPosilek) REFERENCES Posilek(idPosilek)," +
                "CONSTRAINT fk_idPoraDnia FOREIGN KEY(idPoraDnia) REFERENCES PoraDnia(idPoraDnia))";
    }
    static String findAll() {
        return  "SELECT Hash.Data,PoraDnia.Pora ,Posilek.Nazwa, Posilek.Kalorie, Posilek.Ilość, Posilek.Bialko, Posilek.Weglowodany, Posilek.Tluszcze, Posilek.Błonnik " +
                "FROM Hash, Posilek,PoraDnia " +
                "WHERE Hash.idPosilek=Posilek.idPosilek " +
                "AND Hash.idPoraDnia=PoraDnia.idPoraDnia;";
    }
    static String getElementByIdTimeOfDayAndDataFromAllTables(Integer id, String data) {
        return  "SELECT Posilek.Nazwa, Posilek.Kalorie, Posilek.Bialko, Posilek.Weglowodany, Posilek.Tluszcze, Posilek.Błonnik, Posilek.Ilość " +
                "FROM  Hash, PoraDnia,Posilek " +
                "WHERE Hash.idPosilek=Posilek.idPosilek " +
                "AND Hash.idPoraDnia = PoraDnia.idPoraDnia " +
                "AND Hash.idPoraDnia= '" + id + "'" +
                "AND Hash.Data = '" +
                data + "'";
    }
    static String deleteByIdHashFromHashTable(String idHash) {
        return  "DELETE  " +
                "FROM Hash " +
                "WHERE Hash.idHash='" + idHash + "' ";
    }

    static String getElementByIdTimeOfDayAndDataAndIdMealFromHash(Integer idTimeOfDay,String data, String idMeal) {
        return  "SELECT Hash.idHash " +
                "FROM  Hash " +
                "WHERE Hash.idPoraDnia = '" + idTimeOfDay + "' " +
                "AND Hash.Data = '" + data + "' " +
                "AND Hash.idPosilek ='" + idMeal + "'";
    }
    static String getMealIdByNameAndAmountFromTableMeal(String name , Integer amount) {
        return  " SELECT " + MEAL_ID_COLUMN +
                " FROM " + TABLE_MEAL +
                " WHERE " + MEAL_NAME_COLUMN +
                " LIKE '" + name + "%' " +
                " AND " + MEAL_AMOUNT_COLUMN + " = " + amount +
                " LIMIT 1 ";
    }
    static String getMealIdByDataAndIdMealFromTableMeal(String data , String idMeal) {
        return  "SELECT Posilek.Bialko, Posilek.Błonnik, Posilek.Kalorie,Posilek.Tluszcze,Posilek.Weglowodany, Posilek.Ilość, Posilek.Nazwa " +
                "FROM Posilek , Hash, PoraDnia " +
                "WHERE Hash.Data = '" + data + "' " +
                "AND Hash.idPoraDnia = PoraDnia.idPoraDnia " +
                "AND Hash.idPosilek = Posilek.idPosilek " + " " +
                "AND Posilek.idPosilek='" + idMeal+ "'";
    }


}
