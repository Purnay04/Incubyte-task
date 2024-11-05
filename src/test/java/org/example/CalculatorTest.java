package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    // Test case naming convention is like <TEST_METHOD>_<INPUT>_<EXPECTED_OUTPUT>
    @Test
    void add_emptyString_zero() {
        Assertions.assertEquals(0, Calculator.add(""));
    }

    @Test
    void add_nullString_zero() {
        Assertions.assertEquals(0, Calculator.add(null));
    }
}