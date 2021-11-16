package cloud.javacoder.githubclient.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.rest.core.event.RepositoryEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

@Service
public class GithubClient {

    private static final String EVENT_ISSUES_URL = "https://api.github.com/repos/{0}/{1}/issues/events";
    private final RestTemplate template;

    public GithubClient(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public ResponseEntity<GithubRepoEvent[]> fetchProjectsFromGithub(String userName, String repoName){

        String url = MessageFormat.format(EVENT_ISSUES_URL, userName, repoName);
        ResponseEntity<GithubRepoEvent[]> response = this.template.getForEntity(url, GithubRepoEvent[].class);

                                         // what we expect: array of GithubRepoEvents - must create such a class
        System.out.println(response); // returns headers
        System.out.println("Body: " + Arrays.toString(response.getBody())); // displays only memory addresses of GithubRepoEvent[]
        System.out.println("Items in array provided by the body: " +  Objects.requireNonNull(response.getBody()).length);
        return response;
    }
    public ResponseEntity<RepositoryEvent[]> fetchEvents(String userName, String repoName) {
        String url = MessageFormat.format(EVENT_ISSUES_URL, userName, repoName);
        return this.template.getForEntity(url, RepositoryEvent[].class);
    }
}
