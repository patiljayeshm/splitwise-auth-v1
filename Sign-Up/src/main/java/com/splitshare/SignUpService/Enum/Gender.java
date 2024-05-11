package com.splitshare.SignUpService.Enum;

import jakarta.persistence.Enumerated;


public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHERS("OTHERS");


    private final String value;

    private Gender(String value) {
        this.value = value;
    }


}
