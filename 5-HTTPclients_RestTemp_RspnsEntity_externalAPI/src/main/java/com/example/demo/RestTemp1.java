package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/* 1. Calls quote API at http://quotes.rest/qod.json which in turn calls https://theysaidso.com
* and displays them at http://localhost:8080/api/quotes/daily
* */

// similer example with http client calling sme other service
// https://medium.com/@TimvanBaarsen/spring-boot-why-you-should-always-use-the-resttemplatebuilder-to-create-a-resttemplate-instance-d5a44ebad9e9

@SpringBootApplication
public class RestTemp1 {

	/* Prior to Spring 5, there was RestTemplate for client-side HTTP access. RestTemplate, which is part
	of the Spring MVC project, enables communication with HTTP servers and enforces RESTful principles.

   Other options to perform HTTP operations from Spring Boot applications include the Apache HttpClient
   library. These options are based upon the Java Servlet API, which is blocking(1 request per 1 thread)
    (aka not reactive - a server can    handle more requests than blocking style application with less threads.).
   https://dev.to/bufferings/springboot2-blocking-web-vs-reactive-web-46jn

   With Spring Framework 5, you now have a new reactive WebClient that provides a higher level,
   common API over HTTP client libraries*/

    /* RestTemplate uses JSON message converter to automatically unmarshall JSON into a simple Java object.
     * We need to model a Java class which models the response*/

    public static void main(String[] args) {
        SpringApplication.run(RestTemp1.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

/*
{
success: {
    total: 1
},
contents: {
    quotes: [
    {
    quote: "Climb the mountains and get their good tidings. Nature's peace will flow into you as sunshine flows into trees. The winds will blow their own freshness into you, and the storms their energy, while cares will drop away from you like the leaves of Autumn.",
    length: "253",
    author: "John Muir",
    tags: [
    "autumn",
    "inspire",
    "life",
    "mountaineering",
    "peace"
    ],
    category: "inspire",
    language: "en",
    date: "2021-10-24",
    permalink: "https://theysaidso.com/quote/john-muir-climb-the-mountains-and-get-their-good-tidings-natures-peace-will-flow",
    id: "AplDeYJXEVuZ8_Qsl4d7QweF",
    background: "https://theysaidso.com/img/qod/qod-inspire.jpg",
    title: "Inspiring Quote of the day"
    }
    ]
},
baseurl: "https://theysaidso.com",
copyright: {
    year: 2023,
    url: "https://theysaidso.com"
}
}*/
