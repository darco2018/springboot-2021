package com.example.demo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SomeHttpClient {

    private final RestTemplate restTemplate;
    // dep injection: > Bean created in main

    public SomeHttpClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build(); // builder has methods to configure template, eg add pass & logi
    }

    public String someRestCall(String name) {
        return this.restTemplate.getForObject("/{name}/details", String.class, name);
    }
}
