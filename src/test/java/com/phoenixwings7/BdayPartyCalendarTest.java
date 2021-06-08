package com.phoenixwings7;

import com.phoenixwings7.birthdayparty.BirthdayPartyCalendar;
import com.phoenixwings7.birthdayparty.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    // I would like to see a test related to the sample input proposed by the test
    @Test
    public void should_yieldSampleOutput_when_sampleInputIsGiven() {
        final Person[] people = new Person[] {
                new Person("Alice", LocalDate.of(1995, Month.MARCH, 29)),
                new Person("Bob", LocalDate.of(1988, Month.APRIL, 1)),
                new Person("Carol", LocalDate.of(2005, Month.APRIL, 6)),
                new Person("Dave", LocalDate.of(1979, Month.MAY, 10))
        };

        final BirthdayPartyCalendar calendar =
                new BirthdayPartyCalendar(new ArrayList<>(Arrays.asList(people)));

        final var results = calendar.getJointPartiesData(2018);
        final var april01 = LocalDate.of(2018, Month.APRIL, 1);
        final var april07 = LocalDate.of(2018, Month.APRIL, 7);
        assertTrue(results.keySet().contains(april01));
        assertTrue(results.keySet().contains(april07));
        assertEquals(2, results.get(april01).size());
        assertEquals(1, results.get(april07).size());
        assertArrayEquals(new String[] {"Alice", "Bob"}, extractNames(results.get(april01)));
        assertArrayEquals(new String[] {"Carol"}, extractNames(results.get(april07)));
    }

    // This is just one of the corner cases I would like to see: what if a party was scheduled
    // for Saturday, December 31st and another on Sunday, January 1st. The same applies between
    // months.
    @Test
    public void should_carryOverBirthdayToNextYear_whenAppropriate() {
        final var people = new Person[] {
                new Person("Alice", LocalDate.of(1990, Month.DECEMBER, 31)),
                new Person("Bob", LocalDate.of(1975, Month.JANUARY, 1))
        };
        final var calendar = new BirthdayPartyCalendar(new ArrayList<>(Arrays.asList(people)));
        final var results = calendar.getJointPartiesData(2017);
        final var jan01 = LocalDate.of(2017, Month.JANUARY, 1);
        assertTrue(results.keySet().contains(jan01));
        assertEquals(2, results.get(jan01).size());
        assertArrayEquals(new String[] {"Alice", "bob"}, extractNames(results.get(jan01)));
    }

    private Object[] extractNames(ArrayList<Person> people) {
        return people.stream().map(Person::getName).sorted().toArray();
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
