package cld.jcoder.demo.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cld.jcoder.demo.GithubProperties;
import cld.jcoder.demo.domain.GithubRepoEvent;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubClient {
                               // eg https://api.github.com/repos/spring-io/sagan/issues/events returns json
    private static final String EVENT_ISSUES_URL = "https://api.github.com/repos/{owner}/{repo}/issues/events";
    private final RestTemplate restTemplate;

    public GithubClient(RestTemplateBuilder builder, GithubProperties properties, MeterRegistry meterRegistry) {

        this.restTemplate = builder
                .additionalInterceptors(new GithubAppTokenInterceptor(properties.getToken()))
                .additionalInterceptors(new MetricsInterceptor(meterRegistry))
                .build();
    }

    // fetch events for this user and this repo
    public ResponseEntity<GithubRepoEvent[]> fetchEvents(String orgName, String repoName) {
        System.out.println(EVENT_ISSUES_URL + ", " + orgName + ", " + repoName);
        ResponseEntity<GithubRepoEvent[]> events = this.restTemplate.getForEntity(EVENT_ISSUES_URL,
                GithubRepoEvent[].class, orgName, repoName); // return array of events
        return events;
    }

    @Cacheable("events") // method same as above but will return List rather than ResponseEntity with array, so less
    // info from the response will be available, eg we won't be able to use now getStatus(), getHeaders() etc.
    public List<GithubRepoEvent> fetchEventsList(String orgName, String repoName) {
        return Arrays.asList(fetchEvents(orgName, repoName).getBody());// returns the body of ResponseEntity<GithubRepoEvent[]> as List
    }

/////////////////////// non-essential methods ////////////////////////////////////////
    // adds the value of token (set in app.prop) in a request
    private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {
        private final String token;
        GithubAppTokenInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                            ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            if (StringUtils.hasText(this.token)) {
                byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
                httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString(basicAuthValue));
            }
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
    }


    // setting a metric actuator/metrics/github.ratelimit.remaining  on the response
    // do http://localhost:8080/ to see it working
    private static class MetricsInterceptor implements ClientHttpRequestInterceptor {
        private final AtomicInteger gauge;

        public MetricsInterceptor(MeterRegistry meterRegistry) {
            this.gauge = meterRegistry.gauge("github.ratelimit.remaining", new AtomicInteger(60));
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                            ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            this.gauge.set(Integer.parseInt(response.getHeaders().getFirst("X-RateLimit-Remaining")));
            return response;
        }
    }
}