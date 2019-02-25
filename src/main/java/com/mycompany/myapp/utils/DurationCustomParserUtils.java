package com.mycompany.myapp.utils;

import java.time.LocalDate;

public class DurationCustomParserUtils {
    public static LocalDate retrieveDate(String duration) {
        return retrieveDate(duration, false);
    }

    public static LocalDate retrieveDate(String duration, boolean reverse) {
        String length = duration.replaceAll("[^\\d]", "");
        String unit = duration.replaceAll("[^A-Za-z]", "");
        switch (unit) {
            case "month":
            case "months":
                return reverse ? LocalDate.now().minusMonths(Long.parseLong(length)) : LocalDate.now().plusMonths(Long.parseLong(length));
            case "week":
            case "weeks":
                return reverse ? LocalDate.now().minusWeeks(Long.parseLong(length)) : LocalDate.now().plusWeeks(Long.parseLong(length));
            case "year":
            case "years":
                return reverse ? LocalDate.now().minusYears(Long.parseLong(length)) : LocalDate.now().plusYears(Long.parseLong(length));
            default:
                return LocalDate.now();
        }
    }
}
