package com.example.demo.quotes;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.client.RestTemplate;

/**
@RestController  informs Spring that each @RequestMapping method returns a @ResponseBody which, by default,
contains a ResponseEntity converted into JSON with an associated HTTP status code.  */
@RestController
// the bean gets registered by application context
public class QuoteController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuoteService quoteService; // will return a quote from API

    @RequestMapping(value = "/api/quotes/daily", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quote> getQuoteOfTheDay() {
        logger.info(">>>CONTROLLER: quoteService.getDaily(). " +
                "Received call on api/quotes/daily. And will call http://quotes.rest/qod.json for a quote json");
        Quote quote = quoteService.getDaily(QuoteService.CATEGORY_INSPIRATIONAL);

        logger.info(">>>CONTROLLER: analyze the API response & decide what the response from the HTTP client will be " +
                "by creating a ResponseEntity<Quote> with a quote in body & OK status, or 404 status");
        if (quote == null) {
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND); // returns an empty response body with HTTP status 404
        }
        logger.info("<<< getQuoteOfTheDay"); // Quote object is marshalled into JSON
        return new ResponseEntity<Quote>(quote, HttpStatus.OK); // returned as JSON with HTTP status 200
    }

    // simplified version:
    // 1. RestTemplate is created here (should be as a field,not as local variable. ALSO:
    // Always use a RestTemplateBuilder to create an instance of your RestTemplate!
    /*
       @Bean
       public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
        or
        public QuoteController(RestTemplateBuilder restTemplateBuilder) { Spring will inject the builder
        this.restTemplate = restTemplateBuilder.build();
    }
    }*/
    // 2. Resource object returned rather than ResponseEntity
    @RequestMapping(value = "/api/quotes/daily/simple", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Quote getQuoteOfTheDaySimple() {
        logger.info(">>>CONTROLLER: quoteService.getDaily(). " +
                "Received call on api/quotes/daily/simple. And will call http://quotes.rest/qod.json for a quote");
        RestTemplate restTemplate = new RestTemplate();
        QuoteResponse quoteResponse = restTemplate.getForObject("http://quotes.rest/qod.json", QuoteResponse.class, "inspire");
        Quote quote = quoteResponse.getQuote(); // extract the quote from the response's json
        return quote; // returned as JSON in  the body by @RequestMapping
        // actually you can return anything, eg only a single field from the Quote
    }

}
