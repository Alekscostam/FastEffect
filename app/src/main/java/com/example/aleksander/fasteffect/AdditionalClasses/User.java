package com.example.aleksander.fasteffect.AdditionalClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Klasa wykorzystywana do rejestracji uzytkownika
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    private String weight;
    private String age;
    private String height;
    private String gender;
    private String email;

    public User(String sWeight, String sAge, String sHeight, String sGender, String sEmail) {
        weight = sWeight;
        age = sAge;
        height = sHeight;
        gender = sGender;
        email = sEmail;
    }
}