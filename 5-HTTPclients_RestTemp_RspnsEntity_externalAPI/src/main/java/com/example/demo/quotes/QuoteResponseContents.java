package com.example.demo.quotes;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The QuoteResponseContents class is a container for the body of a response
 * from the "They Said So" (TSS) remote Quote API. This class holds the
 * object(s) returned from the call.
 */
@JsonIgnoreProperties( ignoreUnknown = true)
public class QuoteResponseContents {

    private ArrayList<Quote> quotes = null;

    public QuoteResponseContents() {    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        if (quotes == null) {
            this.quotes = new ArrayList<Quote>(0);
        }
        this.quotes = quotes;
    }

}