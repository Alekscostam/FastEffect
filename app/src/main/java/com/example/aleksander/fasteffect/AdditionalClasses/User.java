package com.example.aleksander.fasteffect.AdditionalClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Klasa wykorzystywana do interkacja ze zdalna baza danych
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    private Integer calories;
    private Integer proteins;
    private Integer carbohydrates;
    private Integer fats;

    private String weight;
    private String age;
    private String height;
    private String gender;
    private String email;

    private String kindOfSport;
    private String activity;
    private String goal;

    public static String childWeight;
    public static String childAge;
    public static String childHeight;
    public static String childGender;

    static {
        childWeight = "weight";
        childAge = "age";
        childHeight = "height";
        childGender = "gender";
    }

    public User(String sWeight, String sAge, String sHeight, String sGender, String sEmail) {
        weight = sWeight;
        age = sAge;
        height = sHeight;
        gender = sGender;
        email = sEmail;
    }

    public User(String kindOfSport, String activity, String goal) {
        this.kindOfSport = kindOfSport;
        this.activity = activity;
        this.goal = goal;
    }

    public User(Integer calories, Integer proteins, Integer carbohydrates, Integer fats) {
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }
}
