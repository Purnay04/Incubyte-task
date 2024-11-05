package org.example;

import org.apache.commons.lang3.StringUtils;

public class NegativeValueException extends Exception{
    int[] negativeNumbers;
    NegativeValueException(int[] negativeNumbers){
        this.negativeNumbers = negativeNumbers;
    }

    @Override
    public String getMessage() {
        return "negatives not allowed " + StringUtils.join(negativeNumbers, ',');
    }
}
