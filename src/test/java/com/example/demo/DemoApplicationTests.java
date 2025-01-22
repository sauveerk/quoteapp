package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.service.QuoteService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    void contextLoads() {
    }

    @Test
    void homePageShouldReturnQuoteOfTheDay() throws Exception {
        String testQuote = "Test quote of the day";
        when(quoteService.getRandomQuote()).thenReturn(testQuote);

        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("text/html;charset=UTF-8"))
               .andExpect(view().name("index"))
               .andExpect(model().attribute("quote", testQuote));
    }
}