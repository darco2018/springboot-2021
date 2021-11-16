package cloud.javacoder.githubclient.githubproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface GithubProjectRepository extends JpaRepository<GithubProject, Long> {

    Optional<GithubProject> findByProjectName(String projectName);
    Optional<GithubProject> findByGithubUser(String githubUser);

}
