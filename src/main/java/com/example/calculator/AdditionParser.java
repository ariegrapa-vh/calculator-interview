package com.example.calculator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/** Parses and evaluates expressions made only of numbers and '+' signs, e.g. "1 + 2.5 + 40". */
@Component
public class AdditionParser {

    // One number: optional minus sign, digits, optional decimal part.
    private static final Pattern NUMBER = Pattern.compile("-?\\d+(\\.\\d+)?");

    public BigDecimal evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression is empty");
        }

        // split with limit -1 keeps trailing empty tokens, so "1+2+" is rejected.
        String[] terms = expression.split("\\+", -1);
        BigDecimal sum = BigDecimal.ZERO;

        for (String raw : terms) {
            String term = raw.strip();
            if (!NUMBER.matcher(term).matches()) {
                throw new IllegalArgumentException("Invalid term: '" + term + "'");
            }
            sum = sum.add(new BigDecimal(term));
        }
        return sum;
    }
}
