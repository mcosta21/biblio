package com.mcosta.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateConverterTest {

    @Test
    void TestarMetodoQueVerificaSeLocalDateEhValido(){
        // Given
        String dateValid = "2021-02-10";
        String dateNotValid1 = "10-05-2020";
        String dateNotValid2 = "2021-13-20";

        // When Then
        assertTrue(DateConverter.isLocalDateValid(dateValid));
        assertFalse(DateConverter.isLocalDateValid(dateNotValid1));
        assertFalse(DateConverter.isLocalDateValid(dateNotValid2));
    }

    @Test
    void TestarMetodoQueConverteDataEmStringParaLocalDate(){
        // Given
        String dateString = "2021-02-21";
        String badDate = "2021-10-32";
        LocalDate dateExpected = LocalDate.of(2021, 2, 21);

        // When
        LocalDate dateActual = DateConverter.StringToLocalDate(dateString);
        LocalDate badDateActual = DateConverter.StringToLocalDate(badDate);

        // Then
        assertEquals(dateActual, dateExpected);
        assertNull(badDateActual);
    }
}
