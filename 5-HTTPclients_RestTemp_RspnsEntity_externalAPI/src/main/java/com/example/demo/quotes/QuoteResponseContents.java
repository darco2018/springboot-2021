package com.example.demo.quotes;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The QuoteResponseContents class is a container for the body of a response
 * from the "They Said So" (TSS) remote Quote API. This class holds the
 * object(s) returned from the call.
 */
/*
contents: {
    quotes: [  // this array has only 1 item at index 0:
         { quote: "Climb the mountains and ...",   // all these items constitute fields in Quote
           length: "253",                         // all these items constitute fields in Quote
           author: "John Muir",                   // all these items constitute fields in Quote
           tags, etc ...                          // all these items constitute fields in Quote
         }
           ]
        },
**/

@JsonIgnoreProperties( ignoreUnknown = true)
public class QuoteResponseContents {

    // the json of the 1st and only element of the List is unmarshalled into java object Quote
    private ArrayList<Quote> quotes; // includes quote, length, author, tags..?

    public QuoteResponseContents() {
        this.quotes = new ArrayList<>(0);
    }

    // getters & setters are necessary for jackson to un/marshall
    public ArrayList<Quote> getQuotes() { return quotes; }

    public void setQuotes(ArrayList<Quote> quotes) {
        if (quotes == null) {
            this.quotes = new ArrayList<Quote>(0);
        }
        this.quotes = quotes;
    }

}