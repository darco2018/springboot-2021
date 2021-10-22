package com.example.demo.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteServiceBean implements QuoteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // collect info about service utilisation:
    //private CounterService counterService;
    // replace with https://stackoverflow.com/questions/52875393/the-import-org-springframework-boot-actuate-metrics-counterservice-cannot-be-res

    private final RestTemplate restTemplate;

    private static final String QUOTE_API_BASE_URL = "http://quotes.rest/qod.json";

    // builder created and injected automatically by spring
    public QuoteServiceBean(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }



    @Override
    public Quote getDaily(String category) {
        logger.info(">>>>>> getDaily");

        //counterService.increment("method.invoked.quoteServiceBean.getDaily");

        String quoteCategory = QuoteService.CATEGORY_INSPIRATIONAL;
        if (category != null && category.trim().length() > 0) {
            quoteCategory = category.trim();
        }

        QuoteResponse quoteResponse = this.restTemplate.getForObject(
                QUOTE_API_BASE_URL + "?category={categ}", // url template
                QuoteResponse.class, // class type of the response object
                quoteCategory); // optional varags for the template

        Quote quote = quoteResponse.getQuote();

        logger.info("<<<<<< getDaily");
        return quote;
    }
}
