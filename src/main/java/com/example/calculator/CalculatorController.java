package com.example.calculator;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * GET /calc/{expression}, e.g. GET /calc/1+2+3  ->  {"expression":"1+2+3","result":6}
 *
 * The expression is taken from the path, not a query parameter, because in a query
 * string '+' is decoded as a space (?expr=1+2 arrives as "1 2").
 */
@RestController
public class CalculatorController {

    private final AdditionParser parser;

    public CalculatorController(AdditionParser parser) {
        this.parser = parser;
    }

    @GetMapping("/calc/{expression}")
    public Map<String, Object> calculate(@PathVariable String expression) {
        BigDecimal result = parser.evaluate(expression);
        return Map.of("expression", expression, "result", result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
