package cld.jcoder.demo.repo;

import cld.jcoder.demo.domain.GithubProject;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GithubProjectRepository extends PagingAndSortingRepository<GithubProject, Long> {
    GithubProject findByRepoName(String repoName); // index is set on the repoName column for fast lookup
}
