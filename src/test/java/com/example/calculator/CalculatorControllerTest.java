package com.example.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculatorController.class)
@Import(AdditionParser.class)
class CalculatorControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void addsIntegers() throws Exception {
        mvc.perform(get("/calc/1+2+3"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result").value(6));
    }

    @Test
    void addsDecimalsAndNegatives() throws Exception {
        mvc.perform(get("/calc/1.5+-0.5+10"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result").value(11.0));
    }

    @Test
    void singleNumber() throws Exception {
        mvc.perform(get("/calc/42"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result").value(42));
    }

    @Test
    void rejectsOtherOperators() throws Exception {
        mvc.perform(get("/calc/1*2"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsDanglingPlus() throws Exception {
        mvc.perform(get("/calc/1+2+"))
           .andExpect(status().isBadRequest());
    }
}
