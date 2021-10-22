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

/**
@RestController  informs Spring that each @RequestMapping method returns a @ResponseBody which, by default,
contains a ResponseEntity converted into JSON with an associated HTTP status code.  */
@RestController
// the bean gets registered by application context
public class QuoteController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuoteService quoteService;

    @RequestMapping(
            value = "/api/quotes/daily",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quote> getQuoteOfTheDay() {
        logger.info(">>> getQuoteOfTheDay");

        Quote quote = quoteService.getDaily(QuoteService.CATEGORY_INSPIRATIONAL);

        if (quote == null) {
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND); // returns an empty response body with HTTP status 404
        }
        logger.info("<<< getQuoteOfTheDay"); // Quote object is marshalled into JSON
        return new ResponseEntity<Quote>(quote, HttpStatus.OK); // returned as JSON with HTTP status 200
    }

}
