package cld.jcoder.demo.events;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import cld.jcoder.demo.e1.DashboardEntry;
import cld.jcoder.demo.e1.GithubProject;
import cld.jcoder.demo.e1.GithubProjectRepository;
import cld.jcoder.demo.github.GithubClient;
import cld.jcoder.demo.github.GithubRepoEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
/*specialization of @Component. Used in combination with annotated handler methods based on the @RequestMapping annotation*/
public class EventsController {

    private final GithubClient githubClient;
    private final GithubProjectRepository repository;

    public EventsController(GithubClient githubClient, GithubProjectRepository repository) {
        this.githubClient = githubClient;
        this.repository = repository;
    }

    @GetMapping("/events/{projectName}")
    @ResponseBody
    // the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    // @RestController has this anno
    public GithubRepoEvent[] fetchEvents(@PathVariable String projectName) {

        GithubProject project = this.repository.findByRepoName(projectName);
        ResponseEntity<GithubRepoEvent[]> response = this.githubClient.fetchEvents(project.getOrgName(), project.getRepoName());
        return response.getBody();
    }


    @GetMapping("/")
    public String dashboard(Model model) {
        List<DashboardEntry> entries = StreamSupport
                .stream(this.repository.findAll().spliterator(), true)
                .map(p -> new DashboardEntry(p, githubClient.fetchEventsList(p.getOrgName(), p.getRepoName())))
                .collect(Collectors.toList());
        model.addAttribute("entries", entries);
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("projects", this.repository.findAll());
        return "admin";
    }

}


