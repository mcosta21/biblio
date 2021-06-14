package com.mcosta.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static boolean isLocalDateValid(String object) {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate dt = LocalDate.parse(object, formatter);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static LocalDate StringToLocalDate(String stringDate) {
        try {
            return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return null;
        }
    }

}
