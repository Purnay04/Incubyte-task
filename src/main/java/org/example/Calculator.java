package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public class Calculator {

    public final static String DEFAULT_DELIMITER = ",";
    public final static String NEW_LINE = "\n";
    static int add(String numbers) throws NegativeValueException {
        if(StringUtils.isEmpty(numbers)) {
            return 0;
        } else if(numbers.startsWith("//")) {
            Optional<Character> delimiterChar = getDelimiter(numbers);
            if(delimiterChar.isPresent()) {
                String delimiter =  String.valueOf(delimiterChar.get());
                numbers = preprocess(numbers.substring(4), delimiter);  // offset 4 is to skip indexes of // & delimiterChar \n characters
                return sumOfStringArr(numbers.split(delimiter));
            }
            numbers = preprocess(numbers.substring(3), DEFAULT_DELIMITER);  // offset 3 is to skip indexes of // & \n characters
            return sumOfStringArr(numbers.split(DEFAULT_DELIMITER));
        } else if((numbers = preprocess(numbers, DEFAULT_DELIMITER)).contains(DEFAULT_DELIMITER)) {
            return sumOfStringArr(numbers.split(DEFAULT_DELIMITER));
        } else {
            return Integer.parseInt(numbers);
        }
    }

    static String preprocess(String numbers, String delimiter) {
        return numbers.replaceAll(NEW_LINE, delimiter);
    }

    static Optional<Character> getDelimiter(String numbers) {
        if(StringUtils.isNotEmpty(numbers) && numbers.length() > 3) {
            char delimiter = numbers.charAt(2);
            return delimiter == '\n' ? Optional.empty() : Optional.of(delimiter);
        }
        return Optional.empty();
    }

    static int sumOfStringArr(String[] numberArray) throws NegativeValueException {
        int[] negativeValues = Arrays.stream(numberArray).filter(StringUtils::isNotEmpty).mapToInt(Integer::parseInt).filter(num -> num < 0).toArray();
        if(negativeValues.length > 0) {
            throw new NegativeValueException(negativeValues);
        }
        return Arrays.stream(numberArray).filter(StringUtils::isNotEmpty).mapToInt(Integer::parseInt).sum();
    }
}
