package com.phoenixwings7;

import com.phoenixwings7.birthdayparty.BirthdayPartyCalendar;
import com.phoenixwings7.birthdayparty.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/* These tests follow this naming convention: should_expectedBehavior_when_stateUnderTest */

public class BdayPartyCalendarTest {
    @Before
    public void setUp() {
        // ensure the current year is 2021 for testing
        LocalDate.now(Clock.fixed(Instant.parse("2021-05-23T18:35:24.00Z"), ZoneId.systemDefault()));
    }

    @Test
    public void should_giveOneDate_when_givenTwoPeople() {
        ArrayList<Person> people = generatePeople(2, 2);

        BirthdayPartyCalendar calendar = new BirthdayPartyCalendar(people);
        assertEquals(2, calendar.getBirthdayPeople().size());
        assertEquals(1, calendar.getJointPartiesData().keySet().size());
    }

    @Test
    public void should_yieldSunday_when_oneBdayOnSunday() {
        ArrayList<Person> people = generatePeople(2, 3);

        BirthdayPartyCalendar calendar = new BirthdayPartyCalendar(people);
        assertEquals(LocalDate.of(2021, 5, 16), calendar.getJointPartiesData()
                .keySet().toArray()[0]);

    }

    private static ArrayList<Person> generatePeople(int amount, int dayDifference) {
        ArrayList<Person> people = new ArrayList<>();
        LocalDate initialDate = LocalDate.of(1999, 5, 13);
        for (int i = 0; i < amount*dayDifference; i+=dayDifference) {
            Person testPerson = new Person("Alice"+i, initialDate.plusDays(i));
            people.add(testPerson);
        }
        return people;
    }
}
