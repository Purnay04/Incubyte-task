package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class CalculatorTest {
    public final static String DEFAULT_DELIMITER = ",";

    // Test case naming convention is like <TEST_METHOD>_<INPUT>_<EXPECTED_OUTPUT>
    @Test
    void add_emptyString_zero() throws NegativeValueException {
        Assertions.assertEquals(0, Calculator.add(""));
    }

    @Test
    void add_nullString_zero() throws NegativeValueException {
        Assertions.assertEquals(0, Calculator.add(null));
    }

    @Test
    void add_oneNumberString_sameNumber() throws NegativeValueException {
        Assertions.assertEquals(1, Calculator.add("1"));
        Assertions.assertEquals(22, Calculator.add("22"));
    }

    @Test
    void add_multipleNumbersCommaSeparatedString_addedResult() throws NegativeValueException {
        Assertions.assertEquals(2, Calculator.add("1,1"));
        Assertions.assertEquals(22, Calculator.add("10,12"));
        Assertions.assertEquals(10, Calculator.add("1,2,3,4"));
        Assertions.assertEquals(15, Calculator.add("1,2,3,8,1"));
    }

    @Test
    void add_numbersWithCommaSeparatedStringAndNewLineChar_addedResult() throws NegativeValueException {
        Assertions.assertEquals(1, Calculator.add("1\n"));
        Assertions.assertEquals(1, Calculator.add("1,\n"));
        Assertions.assertEquals(10, Calculator.add("1\n2,3,4"));
        Assertions.assertEquals(10, Calculator.add("1\n,2,3,4"));
        Assertions.assertEquals(50, Calculator.add("1\n2,3,4,25,15"));
    }

    @Test
    void add_numbersWithCustomDelimiter_addedResult() throws NegativeValueException {
        Assertions.assertEquals(10, Calculator.add("//;\n10"));
        Assertions.assertEquals(3, Calculator.add("//;\n1;2"));
        Assertions.assertEquals(7, Calculator.add("//,\n1,4,2"));
        Assertions.assertEquals(50, Calculator.add("//'\n1'2'3'4'25'15"));
    }

    @Test
    void add_numbersWithCustomDelimiterAndNewLineChar_addedResult() throws NegativeValueException {
        Assertions.assertEquals(10, Calculator.add("//;\n\n10"));
        Assertions.assertEquals(3, Calculator.add("//;\n1\n;2"));
        Assertions.assertEquals(7, Calculator.add("//,\n1,4\n,2"));
        Assertions.assertEquals(50, Calculator.add("//'\n1'2\n3'4\n25'15"));
        Assertions.assertEquals(50, Calculator.add("//\n1,2\n3,4\n25,15"));
    }

    @Test
    void add_singleNegativeNumberString_throwException() {
        try{
            Calculator.add("-1");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -1", ne.getMessage());
        }
        try{
            Calculator.add("-2345");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -2345", ne.getMessage());
        }
    }

    @Test
    void add_numbersWithSomeNegativeNumberString_throwException() {
        try {
            Calculator.add("-1");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -1", ne.getMessage());
        }
        try{
            Calculator.add("1,-2");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -2", ne.getMessage());
        }
        try{
            Calculator.add("1,-2,-3,4");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -2,-3", ne.getMessage());
        }
        try{
            Calculator.add("-1,-2,-3,-8,-1");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -1,-2,-3,-8,-1", ne.getMessage());
        }
    }

    @Test
    void add_numbersWithSomeNegativeNumbersAndCustomDelimiter_throwException() {
        try {
            Calculator.add("-1\n");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -1", ne.getMessage());
        }
        try{
            Calculator.add("//;\n1\n;-2");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -2", ne.getMessage());
        }
        try{
            Calculator.add("//,\n1,-4\n,-2");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -4,-2", ne.getMessage());
        }
        try{
            Calculator.add("//'\n1'2\n3'4\n-25'-15");
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -25,-15", ne.getMessage());
        }
    }

    @Test
    void add_numbersWithInvalidNumbers_addedResultWithSkippedInvalid() throws NegativeValueException {
        Assertions.assertEquals(0, Calculator.add("1001"));
        Assertions.assertEquals(2, Calculator.add("1,1,1001"));
        Assertions.assertEquals(22, Calculator.add("10,12,2354"));
        Assertions.assertEquals(3, Calculator.add("//,\n1,4999,2"));
        Assertions.assertEquals(25, Calculator.add("//'\n1'2\n3'4\n2555'15"));
        Assertions.assertEquals(5, Calculator.add("1\n,2800,3000,4"));
    }

    @Test
    void preprocess_numbersWithCommaSeparatedStringAndNewLineChar_replacedResult() {
        Assertions.assertEquals("1,2,3,4", Calculator.preprocess("1\n2,3,4", DEFAULT_DELIMITER));
        Assertions.assertEquals("1,,2,3,4", Calculator.preprocess("1\n,2,3,4", DEFAULT_DELIMITER));
    }

    @Test
    void getDelimiter_correctFormattedString_actualDelimiter() {
        Assertions.assertEquals(Optional.of(';'), Calculator.getDelimiter("//;\n\n10"));
        Assertions.assertEquals(Optional.of(','), Calculator.getDelimiter("//,\n1,4\n,2"));
    }

    @Test
    void getDelimiter_withoutDelimiter_empty() {
        Assertions.assertEquals(Optional.empty(), Calculator.getDelimiter("//\n\n10"));
    }

    @Test
    void sumOfStringArr_emptyStringArr_Zero() throws NegativeValueException {
        Assertions.assertEquals(0, Calculator.sumOfStringArr(new String[]{}));
    }

    @Test
    void sumOfStringArr_numberString_sumOfNumbers() throws NegativeValueException {
        Assertions.assertEquals(6, Calculator.sumOfStringArr(new String[]{"1", "2", "3"}));
        Assertions.assertEquals(7, Calculator.sumOfStringArr(new String[]{"1", "4", "2"}));
        Assertions.assertEquals(22, Calculator.sumOfStringArr(new String[]{"10", "12"}));
    }

    @Test
    void sumOfStringArr_someNegativeValues_throwException() {
        try{
            Calculator.sumOfStringArr(new String[]{"1", "-2", "3"});
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -2", ne.getMessage());
        }
        try{
            Calculator.sumOfStringArr(new String[]{"1", "-4", "-2"});
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -4,-2", ne.getMessage());
        }
        try{
            Calculator.sumOfStringArr(new String[]{"1","2","3","4","-25","-15"});
        } catch (NegativeValueException ne) {
            Assertions.assertEquals("negatives not allowed -25,-15", ne.getMessage());
        }
    }

    @Test
    void sumOfStringArr_someInvalidNumbers_addedResultWithSkippedInvalid() throws NegativeValueException {
        Assertions.assertEquals(4, Calculator.sumOfStringArr(new String[]{"1", "2000", "3"}));
        Assertions.assertEquals(6, Calculator.sumOfStringArr(new String[]{"1","2","3","4893","2123000","15555"}));
    }

}