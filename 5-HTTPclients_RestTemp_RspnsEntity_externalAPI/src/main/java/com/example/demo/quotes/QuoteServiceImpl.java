package com.example.demo.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteServiceImpl implements QuoteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private static final String QUOTE_API_BASE_URL = "http://quotes.rest/qod.json";

    // builder created and injected automatically by spring - where is builder bean created?!
    // Why not use @Autowired on the RestTemplate filed above and inject the @Bean from main()?!
    public QuoteServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Quote getDaily(String category) {
        logger.info(">>>>>>QUOTE SERVICE IMPL: getDaily() called");

        String quoteCategory = QuoteService.CATEGORY_INSPIRATIONAL; // package-access variable from QuoteService interface
        if (category != null && category.trim().length() > 0) {
            quoteCategory = category.trim();
        }

        logger.info("<<<<<<QUOTE SERVICE IMPL call API: restTemplate.getForObject(URL, ResponseObj.class, optionals) ");
                                                        // make GET request to quote API
                                                        // The response (if any) is converted and returned.
        QuoteResponse quoteResponse = this.restTemplate.getForObject(
                QUOTE_API_BASE_URL + "?category={categ}", // url template
                QuoteResponse.class, // class type of the response object
                quoteCategory); // optional varags for the template

        Quote quote = quoteResponse.getQuote(); // response type depends on response object declared as paramte
        // to restTemplate.getForObject(...,ResponseObj.class,...) as this is the responseType - the type of the return value
        logger.info("<<<<<<QUOTE SERVICE IMPL: accessing quote by quoteResponse.getQuote() ");

        return quote;
    }
}
