package cld.jcoder.demo.e1;

import cld.jcoder.demo.github.GithubRepoEvent;

import java.util.List;

// combines info about project and event
public class DashboardEntry {

    private final GithubProject project; // ownerName, repoName

    private final List<GithubRepoEvent> events;

    public DashboardEntry(GithubProject project, List<GithubRepoEvent> events) {
        this.project = project;
        this.events = events;
    }

    public GithubProject getProject() {
        return project;
    }

    public List<GithubRepoEvent> getEvents() {
        return events;
    }
}