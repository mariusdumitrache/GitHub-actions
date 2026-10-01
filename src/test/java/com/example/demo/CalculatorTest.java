package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {

    private final Calculator calc = new Calculator();

    @ParameterizedTest
    @CsvSource({"1, 2, 3", "0, 0, 0", "-5, 5, 0", "100, 250, 350"})
    void addsNumbers(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }

    @Test
    void subtractsNumbers() {
        assertEquals(4, calc.subtract(10, 6));
    }

    @Test
    void multipliesNumbers() {
        assertEquals(42, calc.multiply(6, 7));
    }

    @Test
    void dividesNumbers() {
        assertEquals(5, calc.divide(20, 4));
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
    }
}
