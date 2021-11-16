CREATE TABLE GITHUB_PROJECT (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  github_user VARCHAR(50),
  project_name VARCHAR(50) -- no comma here
);

CREATE INDEX idx_github_user
ON GITHUB_PROJECT (github_user);
