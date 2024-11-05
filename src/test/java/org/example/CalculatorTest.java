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

    @Test
    void add_oneNumberString_sameNumber() {
        Assertions.assertEquals(1, Calculator.add("1"));
        Assertions.assertEquals(22, Calculator.add("22"));
    }

    @Test
    void add_multipleNumbersCommaSeparatedString_addedResult() {
        Assertions.assertEquals(2, Calculator.add("1,1"));
        Assertions.assertEquals(22, Calculator.add("10,12"));
        Assertions.assertEquals(10, Calculator.add("1,2,3,4"));
        Assertions.assertEquals(15, Calculator.add("1,2,3,8,1"));
    }
}