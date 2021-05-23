package com.phoenixwings7;

import static org.junit.Assert.assertTrue;

import com.phoenixwings7.birthdayparty.Person;
import com.phoenixwings7.customexceptions.NullDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/* These tests follow this naming convention: should_expectedBehavior_when_stateUnderTest */

public class PersonBirthdayTest
{
    private static Person person;
    @Before
    public void setUp() throws Exception {
        person = new Person("Alice");
    }

    @Test
    public void should_calculateCorrectly_when_providedCorrectData() throws NullDateException {
        LocalDate dayToExamine = LocalDate.of(2021, 5, 20);
        LocalDate saturdayThatWeek = dayToExamine.plusDays(2);
        person.setBirthday(dayToExamine);
        assertTrue(saturdayThatWeek.isEqual(person.getBirthdayPartyDateThisYear()));
    }

    @Test(expected = NullDateException.class)
    public void should_throwException_when_birthdayIsNull() throws NullDateException {
        person.getBirthdayPartyDateThisYear();
    }
}
