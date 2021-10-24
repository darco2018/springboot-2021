package com.example.demo.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
  The QuoteResponse class models the attributes of a response from the Quote API. http://quotes.rest/qod.json

  This the response's JSON which we try to model here
    {
        +success: {},
        +contents: {},
         baseurl: "https://theysaidso.com",
        +copyright: {}
    }
*/
@JsonIgnoreProperties( ignoreUnknown = true) // will ignore baseurl and copyright keys
public class QuoteResponse {


    //The success or failure status of the API call.
    private QuoteResponseSuccess success;

    //The body of the API call response.
    private QuoteResponseContents contents;

    //---------------- constructor plus getters & setters
    // getters & setters are necessary for jackson to un/marshall
    public QuoteResponse() { }
    public QuoteResponseSuccess getSuccess() {  return success; }
    public void setSuccess(QuoteResponseSuccess success) { this.success = success; }
    public QuoteResponseContents getContents() { return contents;  }
    public void setContents(QuoteResponseContents contents) { this.contents = contents; }
    //-----------------------------------------------------------------------
    /**
     * A helper method which examines the internal value of the
     * QuoteResponseContents object and returns the first Quote object from the
     * Collection if the API call was successful and the Collection is not
     * empty.
     */
    public Quote getQuote() {
        if (isSuccess()) {
            if (contents != null && contents.getQuotes().size() > 0) { // the 1 and only item of the List will be the Quote object
                return contents.getQuotes().get(0); // the json shown below is unmarshalled into java object Quote
            }
        }
        return null;
    }


    /**
     * A helper method which examines the internal value of the
     * QuoteResponseSuccess object and returns a boolean indicating the success
     * or failure of the API call.
     */
    public boolean isSuccess() {
        if (success != null && success.getTotal() > 0) {
            return true;
        }
        return false;
    }

}