package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.QuoteService;

@SpringBootApplication
@Controller
public class DemoApplication {

    @Autowired
    private QuoteService quoteService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("quote", quoteService.getRandomQuote());
        return "index";
    }
}