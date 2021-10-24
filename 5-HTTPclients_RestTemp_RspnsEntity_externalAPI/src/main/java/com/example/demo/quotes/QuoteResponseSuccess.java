package com.example.demo.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The QuoteResponseSuccess class models the invocation status attributes of a
 * response from the "They Said So" (TSS) remote Quote API. This class
 * represents the pass-fail indicator model of the API response.
 */
/* Models this part of API JSON reposne
  success: {
     total: 1
  }
*/
@JsonIgnoreProperties(  ignoreUnknown = true)
public class QuoteResponseSuccess {

    /**
     * The total number of objects contained in the response.
     */
    private int total;

    public QuoteResponseSuccess() { }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

}
