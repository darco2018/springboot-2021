package com.example.demo.quotes;

// best practice: create interfaces for business services:
public interface QuoteService {
    String CATEGORY_INSPIRATIONAL = "inspire";
    Quote getDaily(String category);
}
