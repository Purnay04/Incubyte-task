package org.example;

import org.apache.commons.lang3.StringUtils;

public class Calculator {
    static int add(String numbers) {
        if(StringUtils.isEmpty(numbers)) {
            return 0;
        } else {
            return Integer.parseInt(numbers);
        }
    }
}
