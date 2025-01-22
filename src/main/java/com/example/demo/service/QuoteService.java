package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final DynamoDbClient dynamoDbClient;
    private final Random random = new Random();

    @Autowired
    public QuoteService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public String getRandomQuote() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("Quotes")
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> items = response.items();
        
        if (items.isEmpty()) {
            return "No quotes available";
        }

        List<String> quotes = items.stream()
                .map(item -> item.get("Quote").s())
                .collect(Collectors.toList());

        return quotes.get(random.nextInt(quotes.size()));
    }
}