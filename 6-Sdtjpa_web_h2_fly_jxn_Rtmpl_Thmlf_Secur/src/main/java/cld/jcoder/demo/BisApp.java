package cld.jcoder.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/*Indicates a configuration class that declares one or more @Bean methods and also
triggers auto-configuration and component scanning.
This is a convenience annotation that is equivalent to declaring @Configuration, @EnableAutoConfiguration and @ComponentScan.
Since:*/
@SpringBootApplication




/* Basic Security:
* Using generated security password: 7736153f-b661-4b7c-afd3-8daf573ed6b8 - will be displayed in console on startup (login: user)
* overwritten in WebSecurityConfiguration
*
* PROBLEM: seems that you're automatically logged as User & you get 403 Forbidden for other endpoints. It works as expected
* if you open the browser in an incognito mode (logging screen is displayed)
* In Chrome enter console >> Application >> Clear Storage
* In Firefox, no problems
*/
/*As of Spring Boot 2.2, Spring finds and registers @ConfigurationProperties classes via classpath scanning. Therefore,
there is no need to annotate such classes with @Component (and other meta-annotations like @Configuration),
or  use the @EnableConfigurationProperties:*/

@EnableConfigurationProperties(GithubProperties.class)
public class BisApp {
	public static void main(String[] args) {
		SpringApplication.run(BisApp.class, args);
	}
}

// BASED on From Zero To Hero With Spring Boot - Brain Clozel

/* EXAMPLE of
  - H2 + SpringDataJPA + Web + Actuator + Devtools
  - Jackson & RestTemplate(builder()) + HTTPClient calling GithubAPI (see GithubClient)
* - INTERCEPTOR implements ClientHttpRequestInterceptor (2 interceptors in at GithubClient, GithubApiHealthIndicator
added to actuator/metrics)
* - custom property added in application.properties (github.token=username:somevalue),
@Validated by @Pattern(in GithubProperties.java)
       and sent with each call to Github API and visible at actuator/env/github.token
* - Spring (Basic) Security with some customisation in (WebSecurityConfiguration)
* */

// APP WORKSINGS & ENDPOINTS:
/*  0 .GithubProject (orgName, repoName,eg https://github.com/darco2018/springboot-2021 << org: darco2018) is
       loaded into H2 from .sql flyway scripts
	1. In EvensController  GithubProject is extracted from H2 by means of Spring Data JPA repository.
*  2. Then GithubProject's properties (organization name & github project name) are passed to GithubClient to generate a URL
*  3. With the URL, GithubClient fetches data about events for these organizations and projects.
*  4. The JSON data is converted into Action, Issue, RepositoryEvent entities. ReponseEntity has an array of RepositoryEvents,
*     which can be displayed at "/events/{projectName}"
*  5. At http://localhost:8080/admin you can view Thymeleaf view of DashboardEntrys generated with Controller's
*     dashboard() method using Model and View
*  6. At  http://localhost:8080/actuator/metrics/github.ratelimit.remaining  you can see a custom metric
* 		(remaining Github API calls/hour limit; default is 60 - you can increase it with a token)
*  7.  custom property (github.token=username:somevalue) visible at:  http://localhost:8080/actuator/env/github.token
*  8. secured endpoint:  /admin  shows the persisted GithubProjects
* */

// to see rate-limit remaining: http https://api.github.com/users/darco2018 -PhH
//
// http://localhost:8080/actuator/metrics/http.server.requests
//                      /actuator/metrics/github.ratelimit.remaining    << custom metric added by iterceptor to response in GithubClient
