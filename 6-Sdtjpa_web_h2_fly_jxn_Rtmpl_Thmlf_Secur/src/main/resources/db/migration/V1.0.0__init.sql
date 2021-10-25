CREATE TABLE GITHUB_PROJECT (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  org_name VARCHAR(50),
  repo_name VARCHAR(50) -- no comma here
);

CREATE INDEX idx_repo_name
ON GITHUB_PROJECT (repo_name);
