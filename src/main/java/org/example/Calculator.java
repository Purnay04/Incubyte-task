package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class Calculator {
    static int add(String numbers) {
        if(StringUtils.isEmpty(numbers)) {
            return 0;
        } else if(numbers.contains(",")) {
            String[] numbersArray = numbers.split(",");
            return Arrays.stream(numbersArray).mapToInt(Integer::parseInt).sum();
        } else {
            return Integer.parseInt(numbers);
        }
    }
}
