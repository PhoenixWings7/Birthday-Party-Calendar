package com.phoenixwings7.birthdayparty;

import com.phoenixwings7.customexceptions.NullDateException;

import java.time.LocalDate;

public class Person {
    private String name;
    private LocalDate birthday;
    private BirthdayParty birthdayParty;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate getBirthdayPartyDateThisYear() throws NullDateException {
        if (birthday == null) {
            throw new NullDateException();
        }
        if(birthdayParty == null) {
            birthdayParty = new BirthdayParty(birthday);
        }

        return birthdayParty.getBirthdayPartyDate();
    }

    public LocalDate getBirthdayPartyDate(int year) throws NullDateException {
        if (birthday == null) {
            throw new NullDateException();
        }
        if(birthdayParty == null) {
            birthdayParty = new BirthdayParty(birthday);
        }

        return birthdayParty.getBirthdayPartyDate(year);
    }
}
