package cloud.javacoder.githubclient.githubproject;

import cloud.javacoder.githubclient.http.GithubClient;
import cloud.javacoder.githubclient.http.GithubRepoEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubProjectController {

    private final GithubProjectRepository repo;
    private final GithubClient client;

    public GithubProjectController(GithubProjectRepository repo, GithubClient client){
        this.repo = repo;
        this.client = client;
    }

    /* Loads github projects & users defined in H2 */
    @GetMapping("/projects")
    public List<GithubProject> getProjectFromDb(){
        return this.repo.findAll(); // List will be converted into JSON String
    }

    @GetMapping("/fetch")
    @ResponseBody
    public GithubRepoEvent[] fetch(){
        ResponseEntity<GithubRepoEvent[]> response = this.client.fetchProjectsFromGithub("darco2018", "springboot");
         //return "Fetch completed";
         return response.getBody();
        // returns JSON from "https://api.github.com/repos/darco2018/springboot/issues/events"
        // and the body is the array GithubRepoEvent[] (each has its own id, url)
    }
}
