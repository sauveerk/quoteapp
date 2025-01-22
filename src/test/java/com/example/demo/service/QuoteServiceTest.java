package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuoteServiceTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    private QuoteService quoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quoteService = new QuoteService(dynamoDbClient);
    }

    @Test
    void whenNoQuotesAvailable_thenReturnDefaultMessage() {
        when(dynamoDbClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder()
                        .items(new ArrayList<>())
                        .build());

        String result = quoteService.getRandomQuote();
        assertEquals("No quotes available", result);
    }

    @Test
    void whenQuotesAvailable_thenReturnRandomQuote() {
        List<Map<String, AttributeValue>> items = new ArrayList<>();
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Quote", AttributeValue.builder().s("Test quote").build());
        items.add(item);

        when(dynamoDbClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder()
                        .items(items)
                        .build());

        String result = quoteService.getRandomQuote();
        assertEquals("Test quote", result);
    }
}