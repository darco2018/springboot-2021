package cld.jcoder.demo.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import cld.jcoder.demo.domain.DashboardEntry;
import cld.jcoder.demo.domain.GithubProject;
import cld.jcoder.demo.repo.GithubProjectRepository;
import cld.jcoder.demo.service.GithubClient;
import cld.jcoder.demo.domain.GithubRepoEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

//  MVC
@Controller
/*specialization of @Component. Used in combination with annotated handler methods based on the @RequestMapping annotation*/
public class EventsController {

    private final GithubClient githubClient; // service
    private final GithubProjectRepository repository;

    public EventsController(GithubClient githubClient, GithubProjectRepository repository) {
        this.githubClient = githubClient;
        this.repository = repository;
    }

    /* Method below dipslays response body as json :
{  array of events like this:
    event: "CLOSED",
    created_at: "2021-10-20T14:20:57Z",
    actor: {
        login: "rwinch",
        avatar_url: "https://avatars.githubusercontent.com/u/362503?v=4",
        html_url: "https://github.com/rwinch"
    },
    issue: {
        html_url: "https://github.com/spring-io/sagan/pull/1040",
        number: 1040,
        title: "Use tanzu security page"
    }
},*/

    @GetMapping("/events/{projectName}")
    @ResponseBody // ndicates a method return value should be bound to the web response body
    // the returned object GithubRepoEvent[] is automatically serialized into JSON and passed back into the HttpResponse object.
    // @RestController has this anno
    public GithubRepoEvent[] fetchEvents(@PathVariable String projectName) {

        GithubProject project = this.repository.findByRepoName(projectName);
        ResponseEntity<GithubRepoEvent[]> response = this.githubClient.fetchEvents(project.getOrgName(), project.getRepoName());
        GithubRepoEvent[] eventsInRepo = response.getBody(); // we can get dody, status, headers from ResponseEntity
        return eventsInRepo; // the other methods here use MVC. This one doesn't use view, just shows the body as json
    }


    @GetMapping("/") // displays orgName, repoName and all events in this repo
    public String dashboard(Model model) {
        List<DashboardEntry> entries = StreamSupport
                // gets GithubProjects from H2, splits them & turns into a stream
                .stream(this.repository.findAll().spliterator(), true)
                // join project(orgName + repoName) & list of events in this project
                .map(project -> new DashboardEntry(project, githubClient.fetchEventsList(project.getOrgName(), project.getRepoName())))
                .collect(Collectors.toList());
        // send the list of events as 'entries' to the template 'dashboard.html'
        model.addAttribute("entries", entries);
        return "dashboard";
    }

    // gets GitHubProjects from H2 & displays projects' owner & name on admin.html.
    // You can also add and delete new repos(not implemented)
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("projects", this.repository.findAll());
        return "admin";
    }

}


