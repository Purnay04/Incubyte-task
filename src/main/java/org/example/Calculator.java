package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Calculator {

    public final static String DEFAULT_DELIMITER = ",";
    public final static String NEW_LINE = "\n";
    public static Function<String[], IntStream> parseNumbers = arr -> Arrays.stream(arr).filter(StringUtils::isNotEmpty).mapToInt(Integer::parseInt);

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
            numbers = numbers.replaceAll("\n", "");
            int number = Integer.parseInt(numbers);
            if(number < 0) {
                throw new NegativeValueException(new int[]{number});
            } else {
                return number <= 1000 ? number : 0;
            }
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
        int[] negativeValues = parseNumbers.apply(numberArray).filter(num -> num < 0).toArray();
        if(negativeValues.length > 0) {
            throw new NegativeValueException(negativeValues);
        }
        return parseNumbers.apply(numberArray).filter(num -> num <= 1000).sum();
    }
}
