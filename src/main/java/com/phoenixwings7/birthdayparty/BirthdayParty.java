package com.phoenixwings7.birthdayparty;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BirthdayParty {
    private final LocalDate birthdayDate;

    BirthdayParty(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public LocalDate getBirthdayPartyDate() {
        final int currentYear = LocalDate.now().getYear();
        return getBirthdayPartyDate(currentYear);
    }

    public LocalDate getBirthdayPartyDate(int year) {
        LocalDate birthdayThisYear = LocalDate.of(year,
                birthdayDate.getMonth(), birthdayDate.getDayOfMonth());

        LocalDate partyDate;
        DayOfWeek dayOfWeek = birthdayThisYear.getDayOfWeek();
        if ((dayOfWeek == DayOfWeek.SUNDAY) || (dayOfWeek == DayOfWeek.SATURDAY)) {
            // if birthday is on Saturday or Sunday, return the date
            partyDate = birthdayThisYear;
        }
        else {
            int daysToSaturday = daysToSaturday(dayOfWeek);
            partyDate = birthdayThisYear.plusDays(daysToSaturday);
        }

        return partyDate;
    }

    private int daysToSaturday(DayOfWeek dayOfWeek) {
        return DayOfWeek.SATURDAY.getValue() - dayOfWeek.getValue();
    }

}
