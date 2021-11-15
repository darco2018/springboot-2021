package cld.jcoder.demo;

import cld.jcoder.demo.service.GithubClient;
import cld.jcoder.demo.domain.GithubRepoEvent;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
// creating our own health endpoint: shows remaining Github API calls/hour limit;
// default is 60 - you can increase it with a token
public class GithubApiHealthIndicator implements HealthIndicator {

    private final GithubClient githubClient;


    public GithubApiHealthIndicator(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public Health health() {
        try {
            ResponseEntity<GithubRepoEvent[]> responseEntity = githubClient
                    .fetchEvents("spring-projects", "spring-boot");
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return Health.up()
                        .withDetail("X-RateLimit-Remaining", responseEntity.getHeaders().getFirst("X-RateLimit-Remaining"))
                        .build();
            }
            else {
                return Health.down().withDetail("status", responseEntity.getStatusCode()).build();
            }
        } // when you cant even connect (eg IOEXception)
        catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
