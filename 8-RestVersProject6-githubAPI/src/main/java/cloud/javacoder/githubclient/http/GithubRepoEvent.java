package cloud.javacoder.githubclient.http;

import com.fasterxml.jackson.annotation.JsonCreator;

public class GithubRepoEvent {

    private long id;
    private String url;

    @JsonCreator
    public GithubRepoEvent(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
