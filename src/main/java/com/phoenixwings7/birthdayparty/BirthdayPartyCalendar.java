package com.phoenixwings7.birthdayparty;

import com.phoenixwings7.customexceptions.NullDateException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public class BirthdayPartyCalendar {
    private ArrayList<Person> birthdayPeople = new ArrayList<>();
    private final HashMap<LocalDate, ArrayList<Person>> parties = new HashMap<>();

    public BirthdayPartyCalendar(){}

    public BirthdayPartyCalendar(ArrayList<Person> birthdayPeople) {
        this.birthdayPeople = birthdayPeople;
        calculateJointParties(birthdayPeople);
    }

    public HashMap<LocalDate, ArrayList<Person>> getJointPartiesData() {
        return parties;
    }

    public void addPeopleToCalendar(ArrayList<Person> birthdayPeople) {
        this.birthdayPeople.addAll(birthdayPeople);
        calculateJointParties(birthdayPeople);
    }

    public ArrayList<Person> getBirthdayPeople() {
        return this.birthdayPeople;
    }

    private void calculateJointParties(ArrayList<Person> addedBdayPeople) {
        HashMap<Integer, ArrayList<Person>> birthdaysByWeekNum = getPeopleByWeekOfMonth(addedBdayPeople);

        // go through the HashMap to figure out joint birthday dates
        // and add them into the people HashMap by date
        for(int weekOfMonth: birthdaysByWeekNum.keySet()) {
            ArrayList<Person> birthdaysThisWeek = birthdaysByWeekNum.get(weekOfMonth);
            LocalDate birthdayDate = null;

            for (Person person: birthdaysThisWeek) {
                LocalDate personBirthdayParty;
                try {
                    personBirthdayParty = person.getBirthdayPartyDateThisYear();
                } catch (NullDateException e) {
                    e.printStackTrace();
                    continue;
                }
                // set birthday party date to this if it's on Sunday or if current date is null
                if (birthdayDate == null || personBirthdayParty.isAfter(birthdayDate)) {
                    birthdayDate = personBirthdayParty;
                }
            }
            // put result in the parties HashMap
            if (parties.get(birthdayDate) != null) {
                ArrayList<Person> peopleList = parties.get(birthdayDate);
                peopleList.addAll(birthdaysThisWeek);
            }
            else {
                parties.put(birthdayDate, birthdaysThisWeek);
            }
        }
    }

    private HashMap<Integer, ArrayList<Person>> getPeopleByWeekOfMonth(ArrayList<Person> addedBdayPeople) {
        HashMap<Integer, ArrayList<Person>> birthdaysByWeekNum = new HashMap<>();
        // get weekOfMonth TemporalField to use later
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        TemporalField weekOfMonthField = weekFields.weekOfMonth();

        // assign birthday people to weeks of the month
        for(Person person: addedBdayPeople) {
            LocalDate birthdayPartyDate;
            try {
                birthdayPartyDate = person.getBirthdayPartyDateThisYear();
            } catch (NullDateException e) {
                e.printStackTrace();
                continue;
            }
            // get week of month for birthday date
            int weekOfMonth = birthdayPartyDate.get(weekOfMonthField);
            if (birthdaysByWeekNum.containsKey(weekOfMonth)) {
                ArrayList<Person> birthdayPeopleThisWeek = birthdaysByWeekNum.get(weekOfMonth);
                birthdayPeopleThisWeek.add(person);
            }
            else {
                ArrayList<Person> people = new ArrayList<>();
                people.add(person);
                birthdaysByWeekNum.put(weekOfMonth, people);
            }
        }
        return birthdaysByWeekNum;
    }
}
