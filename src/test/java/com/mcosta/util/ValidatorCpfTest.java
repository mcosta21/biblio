package com.mcosta.util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorCpfTest {

    @Test
    void TestarMetodoQueVerificaSeCpfEhValido(){
        // Givem
        String cpfValid1 = "897.807.810-99";
        String cpfValid2 = "53580904019";
        String cpfNotValid1 = "451.140.520-68";
        String cpfNotValid2 = "32895341031";

        // When Then
        assertTrue(ValidatorCpf.isValid(cpfValid1));
        assertFalse(ValidatorCpf.isNotValid(cpfValid2));
        assertFalse(ValidatorCpf.isValid(cpfNotValid1));
        assertTrue(ValidatorCpf.isNotValid(cpfNotValid2));
    }
}
